package de.hhu.cs.dbs.propra.application.services;

import javax.sql.DataSource;
import javax.ws.rs.core.Response;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Map;

public class PremiumnutzerService {
	private DataSource dataSource;

	public PremiumnutzerService(DataSource dataSource){
		this.dataSource = dataSource;
	}

	public Response addPlaylist(String bezeichnung, Boolean privat, String cover, String mail){
		Response r;
		Connection connection=null;
		try{
			String query = "INSERT INTO PLAYLIST(playlistname, oeffentlich, coverbild, mail) VALUES(?,?,?,?)";
			connection = dataSource.getConnection();
			connection.setAutoCommit(false);
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1,bezeichnung);
			if(privat){
				preparedStatement.setString(2,"FALSE");
			}
			else{
				preparedStatement.setString(2,"TRUE");
			}
			preparedStatement.setString(3,cover);
			preparedStatement.setString(4,mail);
			preparedStatement.executeUpdate();
			Long id = preparedStatement.getGeneratedKeys().getLong(1);
			connection.commit();
			connection.setAutoCommit(true);
			connection.close();
			r = Response.status(Response.Status.CREATED).header("Location","playlists/" + URLEncoder.encode(String.valueOf(id), StandardCharsets.UTF_8)).build();
		}
		catch (Exception e){
			e.printStackTrace();
			Map<String, Object> entity = new HashMap<>();
			entity.put("message", "Erstellung fehlgeschlagen" + e.getLocalizedMessage());
			r = Response.status(Response.Status.BAD_REQUEST).entity(entity).build();
		}
		if(connection!=null) {
			try{
				connection.close();
			}
			catch (Exception e){
				e.printStackTrace();
				Map<String, Object> entity = new HashMap<>();
				entity.put("message", "Erstellung fehlgeschlagen" + e.getLocalizedMessage());
				r = Response.status(Response.Status.BAD_REQUEST).entity(entity).build();
			}
		}
		return r;
	}

	public Response addToPlaylist(int TID, int PID){
		Response r;
		Connection connection=null;
		try{
			String query = "INSERT INTO SAMMELT VALUES(?,?)";
			connection = dataSource.getConnection();
			connection.setAutoCommit(false);
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1,PID);
			preparedStatement.setInt(2,TID);
			preparedStatement.executeUpdate();
			Long id = preparedStatement.getGeneratedKeys().getLong(1);
			connection.commit();
			connection.setAutoCommit(true);
			connection.close();
			r = Response.status(Response.Status.CREATED).header("Location","playlists/" + URLEncoder.encode(String.valueOf(id), StandardCharsets.UTF_8)).build();
		}
		catch (Exception e){
			e.printStackTrace();
			Map<String, Object> entity = new HashMap<>();
			entity.put("message", "Hinzufuegen fehlgeschlagen" + e.getLocalizedMessage());
			r = Response.status(Response.Status.BAD_REQUEST).entity(entity).build();
		}
		if(connection!=null) {
			try{
				connection.close();
			}
			catch (Exception e){
				e.printStackTrace();
				Map<String, Object> entity = new HashMap<>();
				entity.put("message", "Hinzufuegen fehlgeschlagen" + e.getLocalizedMessage());
				r = Response.status(Response.Status.BAD_REQUEST).entity(entity).build();
			}
		}
		return r;
	}
}
