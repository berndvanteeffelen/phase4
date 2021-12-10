package de.hhu.cs.dbs.propra.application.services;

import javax.sql.DataSource;
import javax.ws.rs.core.Response;
import java.sql.*;
import java.util.*;

public class AnwenderService {
	private DataSource dataSource;

	public AnwenderService(DataSource dataSource){
		this.dataSource = dataSource;
	}

	private Response resultAsResponse(ResultSet resultSet){
		List<Map> entity = new ArrayList<>();
		try {
			ResultSetMetaData rsm = resultSet.getMetaData();
			int cC = rsm.getColumnCount();
			List<String> cN = new ArrayList<>();
			for (int i = 1; i<=cC; i++){
				cN.add(rsm.getColumnName(i).toUpperCase());
			}
			while (resultSet.next()){
				Map<String, Object> map = new HashMap<>();
				for (int i=1;i<=cC;i++) {
					map.put(cN.get(i-1),resultSet.getString(i));
				}
				entity.add(map);
			}
			resultSet.close();
			return Response.status(Response.Status.OK).entity(entity).build();
		}
		catch (Exception e){
			e.printStackTrace();
			Map<String, Object> err = new HashMap<>();
			err.put("message","Keine Ergebnisse gefunden");
			entity.add(err);
			return Response.status(Response.Status.BAD_REQUEST).entity(entity).build();
		}
	}

	public Response getNutzer(){
		try{
			String query = "SELECT * FROM NUTZER";
			Connection connection = dataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			return resultAsResponse(resultSet);
		}
		catch (Exception e){
			e.printStackTrace();
			Map<String, Object> entity = new HashMap<>();
			entity.put("message", "Keine Ergebnisse gefunden" + e.getLocalizedMessage());
			return Response.status(Response.Status.BAD_REQUEST).entity(entity).build();
		}
	}

	public Response getPremiumnutzer(){
		try{
			String query = "SELECT * FROM PREMIUMNUTZER";
			Connection connection = dataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			return resultAsResponse(resultSet);
		}
		catch (Exception e){
			e.printStackTrace();
			Map<String, Object> entity = new HashMap<>();
			entity.put("message", "Keine Ergebnisse gefunden" + e.getLocalizedMessage());
			return Response.status(Response.Status.BAD_REQUEST).entity(entity).build();
		}
	}

	public Response getKuenstler(){
		try{
			String query = "SELECT * FROM KUENSTLER";
			Connection connection = dataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			return resultAsResponse(resultSet);
		}
		catch (Exception e){
			e.printStackTrace();
			Map<String, Object> entity = new HashMap<>();
			entity.put("message", "Keine Ergebnisse gefunden" + e.getLocalizedMessage());
			return Response.status(Response.Status.BAD_REQUEST).entity(entity).build();
		}
	}

	public Response getAlben(){
		try{
			String query = "SELECT * FROM ALBUM";
			Connection connection = dataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			return resultAsResponse(resultSet);
		}
		catch (Exception e){
			e.printStackTrace();
			Map<String, Object> entity = new HashMap<>();
			entity.put("message", "Keine Ergebnisse gefunden" + e.getLocalizedMessage());
			return Response.status(Response.Status.BAD_REQUEST).entity(entity).build();
		}
	}

	public Response getGenres(){
		try{
			String query = "SELECT * FROM GENRE";
			Connection connection = dataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			return resultAsResponse(resultSet);
		}
		catch (Exception e){
			e.printStackTrace();
			Map<String, Object> entity = new HashMap<>();
			entity.put("message", "Keine Ergebnisse gefunden" + e.getLocalizedMessage());
			return Response.status(Response.Status.BAD_REQUEST).entity(entity).build();
		}
	}

	public Response getTitel(){
		try{
			String query = "SELECT * FROM TITEL";
			Connection connection = dataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			return resultAsResponse(resultSet);
		}
		catch (Exception e){
			e.printStackTrace();
			Map<String, Object> entity = new HashMap<>();
			entity.put("message", "Keine Ergebnisse gefunden" + e.getLocalizedMessage());
			return Response.status(Response.Status.BAD_REQUEST).entity(entity).build();
		}
	}

	public Response getPlaylists(){
		try{
			String query = "SELECT * FROM PLAYLIST";
			Connection connection = dataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			return resultAsResponse(resultSet);
		}
		catch (Exception e){
			e.printStackTrace();
			Map<String, Object> entity = new HashMap<>();
			entity.put("message", "Keine Ergebnisse gefunden" + e.getLocalizedMessage());
			return Response.status(Response.Status.BAD_REQUEST).entity(entity).build();
		}
	}

	public Response getBands(){
		try{
			String query = "SELECT * FROM BAND";
			Connection connection = dataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			return resultAsResponse(resultSet);
		}
		catch (Exception e){
			e.printStackTrace();
			Map<String, Object> entity = new HashMap<>();
			entity.put("message", "Keine Ergebnisse gefunden" + e.getLocalizedMessage());
			return Response.status(Response.Status.BAD_REQUEST).entity(entity).build();
		}
	}
}
