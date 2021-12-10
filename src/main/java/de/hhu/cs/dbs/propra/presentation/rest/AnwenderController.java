package de.hhu.cs.dbs.propra.presentation.rest;

import de.hhu.cs.dbs.propra.application.services.AnwenderService;

import javax.inject.Inject;
import javax.sql.DataSource;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;

@Path("/")
@Consumes(MediaType.MULTIPART_FORM_DATA)
@Produces(MediaType.APPLICATION_JSON)
public class AnwenderController {
	@Inject
	private DataSource dataSource;

	@Context
	private SecurityContext securityContext;

	@Context
	private UriInfo uriInfo;

	@Path("/nutzer")
	@GET
	public Response getNutzer(){
		AnwenderService anwenderService = new AnwenderService(dataSource);
		return anwenderService.getNutzer();
	}

	@Path("/premiumnutzer")
	@GET
	public Response getPremiumnutzer(){
		AnwenderService anwenderService = new AnwenderService(dataSource);
		return anwenderService.getPremiumnutzer();
	}

	@Path("/kuenstler")
	@GET
	public Response getKuenstler(){
		AnwenderService anwenderService = new AnwenderService(dataSource);
		return anwenderService.getKuenstler();
	}

	@Path("/alben")
	@GET
	public Response getAlben(){
		AnwenderService anwenderService = new AnwenderService(dataSource);
		return anwenderService.getAlben();
	}

	@Path("/genres")
	@GET
	public Response getGenres(){
		AnwenderService anwenderService = new AnwenderService(dataSource);
		return anwenderService.getGenres();
	}

	@Path("/titel")
	@GET
	public Response getTitel(){
		AnwenderService anwenderService = new AnwenderService(dataSource);
		return anwenderService.getTitel();
	}

	@Path("/playlists")
	@GET
	public Response getPlaylists(){
		AnwenderService anwenderService = new AnwenderService(dataSource);
		return anwenderService.getPlaylists();
	}

	@Path("/bands")
	@GET
	public Response getBands(){
		AnwenderService anwenderService = new AnwenderService(dataSource);
		return anwenderService.getBands();
	}
}
