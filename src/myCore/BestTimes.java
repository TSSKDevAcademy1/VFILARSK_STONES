package myCore;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Iterator;
import java.util.List;

public class BestTimes {

	public static final String URL = "jdbc:mysql://localhost/tiledgame";
	public static final String USER = "root";
	public static final String PASSWORD = "root";

	public static final String QUERYINSERT = "INSERT INTO besttimes (name, time) VALUES (?, ?)";
	public static final String QUERYSELECT = "SELECT name, time FROM besttimes";
	public static final String QUERYDELETE = "DELETE FROM besttimes";
	private List<PlayerTime> playerTimes = new ArrayList<PlayerTime>();

	public BestTimes() {
		load();
	}

	public Iterator<PlayerTime> iterator() {
		return playerTimes.iterator();
	}

	public void addPlayerTime(String name, int time) {
		playerTimes.add(new PlayerTime(name, time));
		// Collections.sort(playerTimes);
		try {
			try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {
				try (Statement stmt = con.createStatement()) {
					stmt.executeUpdate(QUERYDELETE);
				}

				try (PreparedStatement stmt = con.prepareStatement(QUERYINSERT)) {
					Iterator<PlayerTime> fIterator = playerTimes.iterator();
					while (fIterator.hasNext()) {
						PlayerTime bestTime = fIterator.next();
						stmt.setString(1, bestTime.getName());
						stmt.setInt(2, bestTime.getTime());
						stmt.executeUpdate();
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void load() {
		try {
			try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
					Statement stmt = con.createStatement();
					ResultSet rs = stmt.executeQuery(QUERYSELECT);) {

				while (rs.next()) {
					playerTimes.add(new PlayerTime(rs.getString(1), rs.getInt(2)));
				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public String toString() {

		Formatter f = new Formatter();
		Iterator<PlayerTime> fIterator = playerTimes.iterator();
		while (fIterator.hasNext()) {
			f.format(fIterator.next().toString());
		}
		return f.toString();
	}

	public static class PlayerTime implements Comparable<PlayerTime> {

		private final String name;

		private final int time;

		public PlayerTime(String name, int time) {
			this.name = name;
			this.time = time;
		}

		public String getName() {
			return this.name;
		}

		public int getTime() {
			return this.time;
		}

		@Override
		public int compareTo(PlayerTime o) {
			if (this.time == o.time) {
				return 0;
			} else if (this.time > o.getTime()) {
				return -1;
			} else {
				return 1;
			}

		}

		public String toString() {
			String string = this.name + " " + this.time + "\n";
			return string;
		}
	}

}
