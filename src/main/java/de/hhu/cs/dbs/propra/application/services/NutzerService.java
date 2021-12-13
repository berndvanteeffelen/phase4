package de.hhu.cs.dbs.propra.application.services;

import javax.sql.DataSource;
import javax.ws.rs.core.Response;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Map;

public class NutzerService {

	private DataSource dataSource;

	public NutzerService(DataSource dataSource){
		this.dataSource = dataSource;
	}

	public Response addKommentar(int tid, String text, String mail){
		Response r;
		Connection connection=null;
		try{
			String query = "INSERT INTO KOMMENTIERT VALUES(?,?,?)";
			connection = dataSource.getConnection();
			connection.setAutoCommit(false);
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1,mail);
			preparedStatement.setInt(2,tid);
			preparedStatement.setString(3,text);
			preparedStatement.executeUpdate();
			Long id = preparedStatement.getGeneratedKeys().getLong(1);
			connection.commit();
			connection.setAutoCommit(true);
			connection.close();
			r = Response.status(Response.Status.CREATED).header("Location","kommentare/" + URLEncoder.encode(String.valueOf(id), StandardCharsets.UTF_8)).build();
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

	public Response editKommentar(int kid, String text){
		Response r;
		Connection connection=null;
		try{
			String query = "UPDATE KOMMENTIERT SET Text=? WHERE ROWID=?";
			connection = dataSource.getConnection();
			connection.setAutoCommit(false);
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1,text);
			preparedStatement.setInt(2,kid);
			preparedStatement.executeUpdate();
			Long id = preparedStatement.getGeneratedKeys().getLong(1);
			connection.commit();
			connection.setAutoCommit(true);
			connection.close();
			r = Response.status(Response.Status.NO_CONTENT).build();
		}
		catch (Exception e){
			e.printStackTrace();
			Map<String, Object> entity = new HashMap<>();
			entity.put("message", "Bearbeitung fehlgeschlagen" + e.getLocalizedMessage());
			r = Response.status(Response.Status.BAD_REQUEST).entity(entity).build();
		}
		if(connection!=null) {
			try{
				connection.close();
			}
			catch (Exception e){
				e.printStackTrace();
				Map<String, Object> entity = new HashMap<>();
				entity.put("message", "Bearbeitung fehlgeschlagen" + e.getLocalizedMessage());
				r = Response.status(Response.Status.BAD_REQUEST).entity(entity).build();
			}
		}
		return r;
	}
}
