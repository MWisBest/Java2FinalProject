package bcdk.savegame;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bcdk.BCDK;

// 10.? - JDBC, save game example using SQLite
public class SaveGame implements AutoCloseable {
	private static String FILENAME = "bcdk";
	private static int VERSION = 3;
	private Connection dbConnection = null;
	private List<ISave> saveableObjects = null;
	private List<ILoad> loadableObjects = null;

	private static SaveGame INSTANCE = new SaveGame();
	private static boolean HAVE_INIT = false;

	private SaveGame() {
		try {
			this.dbConnection = DriverManager.getConnection("jdbc:sqlite:" + FILENAME + ".db");
		} catch (SQLException e) {
			this.dbConnection = null;
		}
		this.saveableObjects = new ArrayList<>();
		this.loadableObjects = new ArrayList<>();
	}

	public static SaveGame getInstance() throws SQLException, VersionMismatchException {
		if (!HAVE_INIT) {
			INSTANCE.init();
		}

		return INSTANCE;
	}

	public void registerSaveable(ISave saveable) {
		this.saveableObjects.add(saveable);
	}

	public void registerLoadable(ILoad loadable) {
		this.loadableObjects.add(loadable);
		loadable.loadFrom(this);
	}

	public void save() {
		for (ISave saveable : this.saveableObjects) {
			saveable.saveTo(this);
		}
	}

	private void init() throws SQLException, VersionMismatchException {
		Statement stmt = this.dbConnection.createStatement();
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery("SELECT VERSION FROM DBINFO");
		} catch (SQLException e) {
			// assume the table didn't exist
		}
		if (rs == null || !rs.next()) { // empty result, database needs to be created
			BCDK.logger.trace("SaveGame: Database was empty, creating");
			this.createDB();
		} else {
			// rs.next ran, so it moved to the first (and only) result
			int curVersion = rs.getInt("VERSION");
			if (curVersion != VERSION) {
				rs.close();
				// version mismatch, also create the database
				BCDK.logger.trace("SaveGame: Database was old version, deleting and re-making");
				this.createDB();

				// throw exception so main class knows to inform the user of the situation
				throw new VersionMismatchException("Old version was " + curVersion + ", new version is " + VERSION);
			}
		}
	}

	private void createDB() throws SQLException {
		deleteSaveData();

		// create tables
		this.createTableIfNotExists(Table.DBINFO, "VERSION INTEGER PRIMARY KEY");
		this.createTableIfNotExists(Table.PLAYER, "PLAYER_NAME TEXT PRIMARY KEY");
		this.createTableIfNotExists(Table.CHECKPOINTS, "CHECKPOINT_NAME TEXT PRIMARY KEY");
		this.createTableIfNotExists(Table.PLAYER_CHECKPOINTS,
				"PLAYER_NAME TEXT, CHECKPOINT_NAME TEXT, PRIMARY KEY (PLAYER_NAME, CHECKPOINT_NAME)");

		// fill in version number
		Statement stmt = this.dbConnection.createStatement();
		stmt.executeUpdate("INSERT INTO DBINFO VALUES(" + VERSION + ")");
		stmt.close();
	}

	@Override
	public void close() throws Exception {
		this.save();
		if (this.dbConnection != null) {
			this.dbConnection.close();
		}
	}

	/**
	 * Deletes all tables from the savegame database.
	 */
	public void deleteSaveData() throws SQLException {
		Statement stmt = this.dbConnection.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM sqlite_master WHERE type='table';");
		List<String> tables = new ArrayList<>();
		while (rs.next()) {
			tables.add(rs.getString("tbl_name"));
		}
		stmt.close();

		stmt = this.dbConnection.createStatement();
		String query = "";
		for (String s : tables) {
			query += "DROP TABLE " + s + ";";
		}
		if (!query.isBlank())
			stmt.execute(query);
		stmt.close();
	}

	/**
	 * Creates a new table in the savegame database if it does not already exist.
	 * 
	 * @param tableName Name for the table, e.g. PLAYER
	 * @param tableDesc Descriptor for the table, e.g. HEALTH INTEGER PRIMARY KEY
	 */
	private void createTableIfNotExists(Table table, String tableDesc) throws SQLException {
		boolean foundTable = false;
		String tableName = table.name();
		Statement stmt = this.dbConnection.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM sqlite_master WHERE type='table';");
		while (rs.next()) {
			if (rs.getString("tbl_name").equals(tableName)) {
				foundTable = true;
			}
		}

		if (!foundTable) {
			stmt.execute("CREATE TABLE " + tableName + " (" + tableDesc + ")");
		}

		stmt.close();
	}

	public void insertOrUpdate(Table table, String values) {
		String tableName = table.name();
		try {
			Statement stmt = this.dbConnection.createStatement();
			stmt.executeUpdate("INSERT OR REPLACE INTO " + tableName + " VALUES(" + values + ")");
			stmt.close();
		} catch (SQLException e) {
			BCDK.logger.error(e);
		}
	}

	// TODO: Is there a better way to allow other classes to query their tables?
	// Probably.
	public Connection getDBConnection() {
		return this.dbConnection;
	}

	// 1.7 - use of nested class
	// 6.3 - creation of custom exception
	/**
	 * Thrown when the savegame that was attempted to be loaded is a different
	 * version than this program is designed to understand and load successfully.
	 */
	public class VersionMismatchException extends RuntimeException {
		private static final long serialVersionUID = -5244452174323513L;

		VersionMismatchException(String message) {
			super(message);
		}
	}

	public enum Table {
		DBINFO, PLAYER, CHECKPOINTS, PLAYER_CHECKPOINTS
	}
}
