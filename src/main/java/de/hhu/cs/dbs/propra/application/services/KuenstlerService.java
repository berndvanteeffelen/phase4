package de.hhu.cs.dbs.propra.application.services;

import javax.sql.DataSource;
import javax.ws.rs.core.Response;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Map;

public class KuenstlerService {
	private DataSource dataSource;

	public KuenstlerService(DataSource dataSource){
		this.dataSource = dataSource;
	}

	public Response addAlbum(String bezeichnung, String erscheinungsjahr, String mail){
		Response r;
		Connection connection=null;
		try{
			String query = "INSERT INTO ALBUM(Albumname, Erscheinungsdatum) VALUES(?,?)";
			connection = dataSource.getConnection();
			connection.setAutoCommit(false);
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(2,erscheinungsjahr);
			preparedStatement.setString(1,bezeichnung);
			preparedStatement.executeUpdate();
			Long id = preparedStatement.getGeneratedKeys().getLong(1);
			connection.commit();
			connection.setAutoCommit(true);
			connection.close();
			r = Response.status(Response.Status.CREATED).header("Location","alben/" + URLEncoder.encode(String.valueOf(id), StandardCharsets.UTF_8)).build();
			addErstellt(bezeichnung,erscheinungsjahr,mail);
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

	public Response addErstellt(String bezeichnung, String erscheinungsjahr, String mail){
		Response r;
		Connection connection=null;
		try{
			String query = "INSERT INTO ERSTELLT VALUES(?,(SELECT AlID FROM ALBUM WHERE Albumname=? AND Erscheinungsdatum=?))";
			connection = dataSource.getConnection();
			connection.setAutoCommit(false);
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1,mail);
			preparedStatement.setString(2,bezeichnung);
			preparedStatement.setString(3,erscheinungsjahr);
			preparedStatement.executeUpdate();
			Long id = preparedStatement.getGeneratedKeys().getLong(1);
			connection.commit();
			connection.setAutoCommit(true);
			connection.close();
			r = Response.status(Response.Status.CREATED).header("Location","erstellt/" + URLEncoder.encode(String.valueOf(id), StandardCharsets.UTF_8)).build();
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

	public Response addTitel(String bezeichnung, String dauer, String lq, String hq, String mail){
		Response r;
		Connection connection=null;
		try{
			String query = "INSERT INTO TITEL(Benennung, Dauer, LQ, HQ) VALUES(?,?,?,?)";
			connection = dataSource.getConnection();
			connection.setAutoCommit(false);
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1,bezeichnung);
			preparedStatement.setString(2,dauer);
			preparedStatement.setString(3,lq);
			preparedStatement.setString(4,hq);
			preparedStatement.executeUpdate();
			Long id = preparedStatement.getGeneratedKeys().getLong(1);
			connection.commit();
			connection.setAutoCommit(true);
			connection.close();
			r = Response.status(Response.Status.CREATED).header("Location","titel/" + URLEncoder.encode(String.valueOf(id), StandardCharsets.UTF_8)).build();
			addVeroeffentlicht(lq,mail);
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

	public Response addVeroeffentlicht(String lq, String mail){
		Response r;
		Connection connection=null;
		try{
			String query = "INSERT INTO VEROEFFENTLICHT VALUES(?,(SELECT TID FROM TITEL WHERE LQ=?))";
			connection = dataSource.getConnection();
			connection.setAutoCommit(false);
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1,mail);
			preparedStatement.setString(2,lq);
			preparedStatement.executeUpdate();
			Long id = preparedStatement.getGeneratedKeys().getLong(1);
			connection.commit();
			connection.setAutoCommit(true);
			connection.close();
			r = Response.status(Response.Status.CREATED).header("Location","veroeffentlicht/" + URLEncoder.encode(String.valueOf(id), StandardCharsets.UTF_8)).build();
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

	public Response addBand(String name, String geschichte){
		Response r;
		Connection connection=null;
		try{
			String query = "INSERT INTO BAND(Bandname, Geschichte) VALUES(?,?)";
			connection = dataSource.getConnection();
			connection.setAutoCommit(false);
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1,name);
			preparedStatement.setString(2,geschichte);
			preparedStatement.executeUpdate();
			Long id = preparedStatement.getGeneratedKeys().getLong(1);
			connection.commit();
			connection.setAutoCommit(true);
			connection.close();
			r = Response.status(Response.Status.CREATED).header("Location","bands/" + URLEncoder.encode(String.valueOf(id), StandardCharsets.UTF_8)).build();
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

	public Response addToBand(String bandid, String kuenstlerID){
		Response r;
		Connection connection=null;
		try{
			String query = "INSERT INTO GEHOERT_ZU VALUES((SELECT BID FROM BAND WHERE ROWID=?),(SELECT Mail FROM KUENSTLER WHERE ROWID=?))";
			connection = dataSource.getConnection();
			connection.setAutoCommit(false);
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1,Integer.parseInt(bandid));
			preparedStatement.setInt(2,Integer.parseInt(kuenstlerID));
			preparedStatement.executeUpdate();
			Long id = preparedStatement.getGeneratedKeys().getLong(1);
			connection.commit();
			connection.setAutoCommit(true);
			connection.close();
			r = Response.status(Response.Status.CREATED).header("Location","bands/" + URLEncoder.encode(String.valueOf(id), StandardCharsets.UTF_8)).build();
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

	public Response deleteBand(String bandid){
		Response r;
		Connection connection=null;
		try{
			String query = "DELETE FROM BAND WHERE ROWID=?";
			connection = dataSource.getConnection();
			connection.setAutoCommit(false);
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1,Integer.parseInt(bandid));
			preparedStatement.executeUpdate();
			Long id = preparedStatement.getGeneratedKeys().getLong(1);
			connection.commit();
			connection.setAutoCommit(true);
			connection.close();
			r = Response.status(Response.Status.NO_CONTENT).header("Location","bands/").build();
		}
		catch (Exception e){
			e.printStackTrace();
			Map<String, Object> entity = new HashMap<>();
			entity.put("message", "Löschen fehlgeschlagen" + e.getLocalizedMessage());
			r = Response.status(Response.Status.BAD_REQUEST).entity(entity).build();
		}
		if(connection!=null) {
			try{
				connection.close();
			}
			catch (Exception e){
				e.printStackTrace();
				Map<String, Object> entity = new HashMap<>();
				entity.put("message", "Löschen fehlgeschlagen" + e.getLocalizedMessage());
				r = Response.status(Response.Status.BAD_REQUEST).entity(entity).build();
			}
		}
		return r;
	}
}
