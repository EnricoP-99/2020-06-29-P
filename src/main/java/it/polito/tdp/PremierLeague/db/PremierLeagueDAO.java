package it.polito.tdp.PremierLeague.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.PremierLeague.model.Action;
import it.polito.tdp.PremierLeague.model.Adiacenze;
import it.polito.tdp.PremierLeague.model.Match;
import it.polito.tdp.PremierLeague.model.Player;

public class PremierLeagueDAO {
	
	public List<Player> listAllPlayers(){
		String sql = "SELECT * FROM Players";
		List<Player> result = new ArrayList<Player>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Player player = new Player(res.getInt("PlayerID"), res.getString("Name"));
				
				result.add(player);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Action> listAllActions(){
		String sql = "SELECT * FROM Actions";
		List<Action> result = new ArrayList<Action>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Action action = new Action(res.getInt("PlayerID"),res.getInt("MatchID"),res.getInt("TeamID"),res.getInt("Starts"),res.getInt("Goals"),
						res.getInt("TimePlayed"),res.getInt("RedCards"),res.getInt("YellowCards"),res.getInt("TotalSuccessfulPassesAll"),res.getInt("totalUnsuccessfulPassesAll"),
						res.getInt("Assists"),res.getInt("TotalFoulsConceded"),res.getInt("Offsides"));
				
				result.add(action);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void listAllMatches(Map<Integer,Match> idMap){
		String sql = "SELECT m.MatchID, m.TeamHomeID, m.TeamAwayID, m.teamHomeFormation, m.teamAwayFormation, m.resultOfTeamHome, m.date, t1.Name, t2.Name   "
				+ "FROM Matches m, Teams t1, Teams t2 "
				+ "WHERE m.TeamHomeID = t1.TeamID AND m.TeamAwayID = t2.TeamID";
		Connection conn = DBConnect.getConnection();
		idMap = new HashMap<>();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				
				Match match = new Match(res.getInt("m.MatchID"), res.getInt("m.TeamHomeID"), res.getInt("m.TeamAwayID"), res.getInt("m.teamHomeFormation"), 
							res.getInt("m.teamAwayFormation"),res.getInt("m.resultOfTeamHome"), res.getTimestamp("m.date").toLocalDateTime(), res.getString("t1.Name"),res.getString("t2.Name"));
				
				idMap.put(match.getMatchID(), match);

			}
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<Match> getVertici(int m,Map<Integer,Match> idMap){
		String sql = "SELECT m.MatchID, m.TeamHomeID, m.TeamAwayID, m.teamHomeFormation, m.teamAwayFormation, m.resultOfTeamHome, m.date, t1.Name, t2.Name "
				+ "FROM Matches m, Teams t1, Teams t2 "
				+ "WHERE m.TeamHomeID = t1.TeamID "
				+ "AND m.TeamAwayID = t2.TeamID "
				+ "AND MONTH(Date)=?";
		List<Match> result = new ArrayList<Match>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, m);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				
				Match match = new Match(res.getInt("m.MatchID"), res.getInt("m.TeamHomeID"), res.getInt("m.TeamAwayID"), res.getInt("m.teamHomeFormation"), 
							res.getInt("m.teamAwayFormation"),res.getInt("m.resultOfTeamHome"), res.getTimestamp("m.date").toLocalDateTime(), res.getString("t1.Name"),res.getString("t2.Name"));
				
				idMap.put(match.getMatchID(), match);
				result.add(match);

			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Adiacenze> getArchi(int m , int t, Map<Integer,Match> idMap){
		String sql = "SELECT DISTINCT m1.MatchID AS m1, m2.MatchID AS m2, COUNT(a1.PlayerID) AS peso "
				+ "FROM matches m1, matches m2 , actions a1, actions a2  "
				+ "WHERE MONTH(m1.Date) = ? "
				+ "AND MONTH(m2.Date) = MONTH(m1.Date) "
				+ "AND m1.MatchID= a1.MatchID "
				+ "AND m2.MatchID= a2.MatchID "
				+ "AND a1.PlayerID=a2.PlayerID "
				+ "AND a1.MatchID>a2.MatchID "
				+ "AND a1.TimePlayed > ?  "
				+ "AND a1.TimePlayed=a2.TimePlayed "
				+ "GROUP BY m1, m2 ";
		List<Adiacenze> result = new ArrayList<Adiacenze>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, m);
			st.setInt(2, t);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				
				Adiacenze a = new Adiacenze(idMap.get(res.getInt("m1")),idMap.get(res.getInt("m2")), res.getInt("peso"));
				
				
				result.add(a);

			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public int getMaxPeso(int m , int t){
		String sql = "SELECT MAX(peso) as maxPeso "
				+ "FROM(SELECT DISTINCT m1.MatchID AS m1, m2.MatchID AS m2, COUNT(a1.PlayerID) AS peso "
				+ "FROM matches m1, matches m2 , actions a1, actions a2 "
				+ "WHERE MONTH(m1.Date) = ? "
				+ "AND MONTH(m2.Date) = MONTH(m1.Date) "
				+ "AND m1.MatchID= a1.MatchID "
				+ "AND m2.MatchID= a2.MatchID "
				+ "AND a1.PlayerID=a2.PlayerID "
				+ "AND a1.MatchID>a2.MatchID "
				+ "AND a1.TimePlayed > ?  "
				+ "AND a1.TimePlayed=a2.TimePlayed "
				+ "GROUP BY m1, m2 )  AS MaxPeso ";
		
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, m);
			st.setInt(2, t);
			ResultSet res = st.executeQuery();
			res.next();
			
			int maxPeso= res.getInt("maxPeso");
			
			conn.close();
			return maxPeso;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
}
