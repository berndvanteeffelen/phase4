package de.hhu.cs.dbs.propra.application.services;

import javax.sql.DataSource;
import javax.ws.rs.core.Response;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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

	public Response getNutzer(String mail){
		try{
			String query;
			if(mail!=null){
				query = "SELECT * FROM NUTZER WHERE Mail == ?";
			}
			else{
				query = "SELECT * FROM NUTZER";
			}
			Connection connection = dataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			if(mail!=null){
				preparedStatement.setString(1,mail);
			}
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

	public Response getPremiumnutzer(Boolean abgelaufen){
		try{
			String query;
			if(abgelaufen==null){
				query = "SELECT * FROM PREMIUMNUTZER JOIN NUTZER N on N.Mail = PREMIUMNUTZER.Mail";
			}
			else if(abgelaufen){
				query = "SELECT * FROM PREMIUMNUTZER JOIN NUTZER N on N.Mail = PREMIUMNUTZER.Mail WHERE Vertragsende < date('now')";
			}
			else {
				query = "SELECT * FROM PREMIUMNUTZER JOIN NUTZER N on N.Mail = PREMIUMNUTZER.Mail WHERE Vertragsende >= date('now')";
			}
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

	public Response getKuenstler(String name){
		try{
			String query;
			if(name!=null){
				query = "SELECT * FROM KUENSTLER JOIN PREMIUMNUTZER P on KUENSTLER.Mail = P.Mail JOIN NUTZER N on P.Mail = N.Mail WHERE Kuenstlername == ?";
			}
			else{
				query = "SELECT * FROM KUENSTLER JOIN PREMIUMNUTZER P on KUENSTLER.Mail = P.Mail JOIN NUTZER N on P.Mail = N.Mail";
			}
			Connection connection = dataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			if(name!=null){
				preparedStatement.setString(1,name);
			}
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

	public Response getAlben(int trackanzahl,String bezeichnung){
		try{
			String query;
			if(trackanzahl!=-1){
				if(bezeichnung!=null){
					query = "SELECT * FROM ALBUM WHERE AlID IN(SELECT AlID FROM(SELECT AlID,count(AlID) as c FROM UMFASST GROUP BY AlID)WHERE c >= ?) AND  Albumname = ?";
				}
				else{
					query = "SELECT * FROM ALBUM WHERE AlID IN(SELECT AlID FROM(SELECT AlID,count(AlID) as c FROM UMFASST GROUP BY AlID)WHERE c >= ?)";
				}
			}
			else if(bezeichnung!=null){
				query = "SELECT * FROM ALBUM WHERE Albumname ==  ?";
			}
			else{
				query = "SELECT * FROM ALBUM";
			}
			Connection connection = dataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			if(trackanzahl!=-1){
				if(bezeichnung!=null){
					preparedStatement.setInt(1,trackanzahl);
					preparedStatement.setString(2,bezeichnung);
				}
				else{
					preparedStatement.setInt(1,trackanzahl);
				}
			}
			else if(bezeichnung!=null){
				preparedStatement.setString(1,bezeichnung);
			}
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

	public Response getGenres(String bezeichnung){
		try{
			String query;
			if(bezeichnung!=null){
				query = "SELECT * FROM GENRE WHERE Genrename == ?";
			}
			else{
				query = "SELECT * FROM GENRE";
			}
			Connection connection = dataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			if(bezeichnung!=null){
				preparedStatement.setString(1,bezeichnung);
			}
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

	public Response getTitel(int dauer, String bezeichnung){
		try{
			String query;
			String duration = "";
			if(dauer!=-1){
				duration = String.format("%02d:%02d:%02d", dauer / 3600, (dauer % 3600) / 60, dauer % 60);//LocalTime.ofSecondOfDay(dauer);//new SimpleDateFormat("HH/mm/ss").format(new Date(dauer));
				if(bezeichnung!=null){
					query = "SELECT * FROM TITEL WHERE DAUER >= ? AND Benennung == ?";
				}
				else{
					query = "SELECT * FROM TITEL WHERE Dauer >= ?";
				}
			}
			else if(bezeichnung!=null){
				query = "SELECT * FROM TITEL WHERE Benennung == ?";
			}
			else{
				query = "SELECT * FROM TITEL";
			}
			Connection connection = dataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			if(dauer!=-1){
				if(bezeichnung!=null){
					preparedStatement.setString(1,duration);
					preparedStatement.setString(2,bezeichnung);
				}
				else{
					preparedStatement.setString(1,duration);
				}
			}
			else if(bezeichnung!=null){
				preparedStatement.setString(1,bezeichnung);
			}
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

	public Response getPlaylists(Boolean privat,String bezeichnung){
		try{
			String query;
			if(privat==null){
				if(bezeichnung!=null){
					query = "SELECT * FROM PLAYLIST WHERE Playlistname == ?";
				}
				else {
					query = "SELECT * FROM PLAYLIST";
				}
			}
			else{
				if(bezeichnung!=null) {
					query = "SELECT * FROM PLAYLIST WHERE Oeffentlich != ? AND Playlistname == ?";
				}
				else{
					query = "SELECT * FROM PLAYLIST WHERE Oeffentlich != ?";//
				}
			}
			Connection connection = dataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			if(privat==null) {
				if (bezeichnung != null) {
					preparedStatement.setString(1, bezeichnung);
				}
			}
			else{
				if(privat) {
					preparedStatement.setString(1, "TRUE");
				}
				else {
					preparedStatement.setString(1, "FALSE");
				}
				if(bezeichnung!=null){
					preparedStatement.setString(2,bezeichnung);
				}
			}
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

	public Response getBands(String name, String geschichte){
		try{
			String query;
			if(name!=null){
				if(geschichte!=null){
					query = "SELECT * FROM BAND WHERE Bandname == ? AND Geschichte == ?";
				}
				else{
					query = "SELECT * FROM BAND WHERE Bandname == ?";
				}
			}
			else if(geschichte!=null){
				query = "SELECT * FROM BAND WHERE Geschichte == ?";
			}
			else{
				query = "SELECT * FROM BAND";
			}
			Connection connection = dataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			if(name!=null){
				if(geschichte!=null){
					preparedStatement.setString(1,name);
					preparedStatement.setString(2,geschichte);
				}
				else{
					preparedStatement.setString(1,name);
				}
			}
			else if(geschichte!=null){
				preparedStatement.setString(1,geschichte);
			}
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

	public Response getKommentare(int titelId){
		try{
			String query = "SELECT * FROM KOMMENTIERT WHERE TID == ?";
			Connection connection = dataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1,titelId);
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

	public Response addNutzer(String mail, String passwort, String name){
		Connection connection = null;
		Response r;
		try{
			String query = "INSERT INTO NUTZER VALUES(?,?,?,NULL)";
			connection = dataSource.getConnection();
			connection.setAutoCommit(false);
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(2,mail);
			preparedStatement.setString(3,passwort);
			preparedStatement.setString(1,name);
			preparedStatement.executeUpdate();
			Long id = preparedStatement.getGeneratedKeys().getLong(1);
			connection.commit();
			connection.setAutoCommit(true);
			connection.close();
			r = Response.status(Response.Status.CREATED).header("Location","nutzer/" + URLEncoder.encode(String.valueOf(id), StandardCharsets.UTF_8)).build();
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

	public Response addPremiumnutzer(String datum,String mail, String passwort, String name){
		addNutzer(mail,passwort,name);
		Connection connection = null;
		Response r;
		try{
			String query = "INSERT INTO PREMIUMNUTZER VALUES(?,?)";
			connection = dataSource.getConnection();
			connection.setAutoCommit(false);
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(2,mail);
			preparedStatement.setString(1,datum);
			preparedStatement.executeUpdate();
			Long id = preparedStatement.getGeneratedKeys().getLong(1);
			connection.commit();
			connection.setAutoCommit(true);
			connection.close();
			r = Response.status(Response.Status.CREATED).header("Location","premiumnutzer/" + URLEncoder.encode(String.valueOf(id), StandardCharsets.UTF_8)).build();
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

	public Response addKuenstler(String kuenstlername,String datum,String mail, String passwort, String name){
		addPremiumnutzer(datum,mail,passwort,name);
		Response r;
		Connection connection=null;
		try{
			String query = "INSERT INTO KUENSTLER VALUES(?,?)";
			connection = dataSource.getConnection();
			connection.setAutoCommit(false);
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(2,mail);
			preparedStatement.setString(1,kuenstlername);
			preparedStatement.executeUpdate();
			Long id = preparedStatement.getGeneratedKeys().getLong(1);
			connection.commit();
			connection.setAutoCommit(true);
			connection.close();
			r = Response.status(Response.Status.CREATED).header("Location","kuenstler/" + URLEncoder.encode(String.valueOf(id), StandardCharsets.UTF_8)).build();
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
}
