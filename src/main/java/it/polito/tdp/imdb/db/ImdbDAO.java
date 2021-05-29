package it.polito.tdp.imdb.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.imdb.model.Actor;
import it.polito.tdp.imdb.model.Adiacenza;
import it.polito.tdp.imdb.model.Director;
import it.polito.tdp.imdb.model.Movie;

public class ImdbDAO {
	
	public void listAllActors(Map <Integer, Actor> idMap){
		String sql = "SELECT * FROM actors";
		List<Actor> result = new ArrayList<Actor>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				if(!idMap.containsKey(res.getInt("id"))) {

				Actor actor = new Actor(res.getInt("id"), res.getString("first_name"), res.getString("last_name"),
						res.getString("gender"));
				
				idMap.put(actor.getId(), actor);
				}
			}
			conn.close();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<Movie> listAllMovies(){
		String sql = "SELECT * FROM movies";
		List<Movie> result = new ArrayList<Movie>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Movie movie = new Movie(res.getInt("id"), res.getString("name"), 
						res.getInt("year"), res.getDouble("rank"));
				
				result.add(movie);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public List<Director> listAllDirectors(){
		String sql = "SELECT * FROM directors";
		List<Director> result = new ArrayList<Director>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Director director = new Director(res.getInt("id"), res.getString("first_name"), res.getString("last_name"));
				
				result.add(director);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public List<Actor> getVertex(String genere, Map<Integer, Actor> idMap){
		
		String sql ="SELECT DISTINCT a.id "
				+ "FROM roles r, actors a, movies_genres m "
				+ "WHERE r.actor_id=a.id AND r.movie_id=m.movie_id AND m.genre=? ";
		
		List<Actor> result = new ArrayList<Actor>();
		
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, genere);
			ResultSet res = st.executeQuery();
			
			while (res.next()) {
				if(idMap.containsKey(res.getInt("id"))) {
					result.add(idMap.get(res.getInt("id")));
				}
			
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	public List<Adiacenza> getAdiacenze(String genere,  Map<Integer, Actor> idMap) {
		String sql ="SELECT DISTINCT a1.id as id1, a2.id as id2, COUNT(*) AS peso "
				+ "FROM roles r1, roles r2,  actors a1, actors a2, movies_genres m "
				+ "WHERE r1.actor_id=a1.id AND r2.actor_id=a2.id AND r1.movie_id=m.movie_id AND r1.movie_id=r2.movie_id AND m.genre=? AND "
				+ "a1.id>a2.id "
				+ "GROUP BY a1.id, a2.id ";
		
		List<Adiacenza> result = new ArrayList<>();
		
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, genere);
			ResultSet res = st.executeQuery();
			
			while (res.next()) {
				if(idMap.containsKey(res.getInt("id1")) && idMap.containsKey(res.getInt("id2")) ) {
					Actor a1 = idMap.get(res.getInt("id1"));
					Actor a2 = idMap.get(res.getInt("id2"));
					int peso = res.getInt("peso");
					Adiacenza a = new Adiacenza(a1, a2, peso);
					result.add(a);
				}
			
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	public List<String> listOfGenres(){
		String sql = "SELECT DISTINCT mg.genre "
				+ "FROM movies_genres mg ";
		List<String> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				result.add(res.getString("mg.genre"));
				
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
