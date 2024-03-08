package bcdk;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SaveGame implements AutoCloseable {
	private static int VERSION = 1;
	private Connection dbConnection = null;

	public SaveGame(String filename) throws SQLException, VersionMismatchException {
		this.dbConnection = DriverManager.getConnection("jdbc:sqlite:" + filename + ".db");
		this.init();
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
		
		Statement stmt = this.dbConnection.createStatement();

		// create tables
		stmt.executeUpdate("CREATE TABLE DBINFO (VERSION INTEGER PRIMARY KEY)");

		// fill in version number
		stmt.executeUpdate("INSERT INTO DBINFO VALUES(" + VERSION + ")");
		
		stmt.close();
	}

	@Override
	public void close() throws Exception {
		if (this.dbConnection != null) {
			this.dbConnection.close();
		}
	}
	
	public void deleteSaveData() throws SQLException {
		Statement stmt = this.dbConnection.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM sqlite_master WHERE type='table';");
		while (rs.next()) {
			stmt.execute("DROP TABLE " + rs.getString("tbl_name"));
		}
		
		stmt.close();
	}

	// 1.7 - use of nested class
	// 6.3 - creation of custom exception
	class VersionMismatchException extends RuntimeException {
		private static final long serialVersionUID = -5244452174323513L;

		VersionMismatchException(String message) {
			super(message);
		}
	}
}
