package bcdk;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class SaveGame implements AutoCloseable {
	private Connection dbConnection = null;

	public SaveGame(String filename) throws SQLException {
		this.dbConnection = DriverManager.getConnection("jdbc:sqlite:" + filename + ".db");
	}

	@Override
	public void close() throws Exception {
		if(this.dbConnection != null) {
			this.dbConnection.close();
		}
	}
}
