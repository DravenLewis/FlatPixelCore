package io.infinitestrike.mechanics.leaderboard;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;

import io.infinitestrike.core.DataEntry;
import io.infinitestrike.core.LogBot;
import io.infinitestrike.core.LogBot.Status;
import io.infinitestrike.sql.DataBaseNotFoundException;
import io.infinitestrike.sql.SqlConnector;

public class LeaderBoard {

	private SqlConnector conn;
	private String name = "";

	public LeaderBoard(String tablename) {
		conn = new SqlConnector("lb-" + tablename + "-leaderboard.db");
		this.name = tablename;

		try {
			if (!conn.tableExists(tablename)) {
				conn.executeUpdate(String.format("CREATE TABLE %s (name vchar[255] , score INTEGER)", tablename));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LogBot.logDataVerbose(e, Status.ERROR, e.getLocalizedMessage());
		}
	}

	public void setPlayerScore(String name, int score) {
		try {
			if (conn.recordExists(this.name, "name", name)) {
				conn.createStatement().executeUpdate(
						String.format("UPDATE %s SET score = %s WHERE name = '%s'", this.name, score, name));
			} else {

				conn.createStatement().executeUpdate(
						String.format("INSERT INTO %s (name,score) VALUES ('%s' , '%s')", this.name, name, score));
			}
		} catch (SQLException | DataBaseNotFoundException e) {
			// TODO Auto-generated catch block
			LogBot.logDataVerbose(e, Status.ERROR, e.getLocalizedMessage());
		}
	}

	public void setPlayerHighScore(String name, int score) {
		Collection<DataEntry> scoreBoard = this.getData();
		if (DataEntry.containsValue((ArrayList<DataEntry>) scoreBoard, name)) {
			DataEntry player = DataEntry.getEntry((ArrayList<DataEntry>) scoreBoard, name);
			if (score > player.getDataEntryInt()) {
				this.setPlayerScore(name, score);
			}
		} else {
			this.setPlayerScore(name, score);
		}
	}

	public void removePlayerData(String name) {
		try {
			if (conn.recordExists(this.name, "name", name)) {
				conn.createStatement()
						.executeUpdate(String.format("DELETE FROM %s WHERE name = '%s'", this.name, name));
			}
		} catch (SQLException | DataBaseNotFoundException e) {
			// TODO Auto-generated catch block
			LogBot.logDataVerbose(e, Status.ERROR, e.getLocalizedMessage());
		}
	}

	public Collection<DataEntry> getData() {
		Collection<DataEntry> entries = new ArrayList<DataEntry>();
		try {
			ResultSet set = conn.createStatement().executeQuery(String.format("SELECT * FROM %s", this.name));
			while (set.next()) {
				entries.add(new DataEntry(set.getString("name"), set.getInt("score")));
			}
		} catch (SQLException | DataBaseNotFoundException e) {
			// TODO Auto-generated catch block
			LogBot.logDataVerbose(e, Status.ERROR, e.getLocalizedMessage());
		}
		return entries;
	}

	public void close() {
		try {
			this.conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			LogBot.logDataVerbose(e, Status.ERROR, e.getLocalizedMessage() + " : " + e.getSQLState());
		}
	}
}
