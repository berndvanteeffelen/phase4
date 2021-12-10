package de.hhu.cs.dbs.propra.presentation.rest;

import de.hhu.cs.dbs.propra.application.services.AnwenderService;

import javax.inject.Inject;
import javax.sql.DataSource;
import javax.ws.rs.*;
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
	public Response getNutzer(@QueryParam("mail") String mail){
		AnwenderService anwenderService = new AnwenderService(dataSource);
		return anwenderService.getNutzer(mail);
	}

	@Path("/premiumnutzer")
	@GET
	public Response getPremiumnutzer(@QueryParam("abgelaufen") Boolean abgelaufen){
		AnwenderService anwenderService = new AnwenderService(dataSource);
		return anwenderService.getPremiumnutzer(abgelaufen);
	}

	@Path("/kuenstler")
	@GET
	public Response getKuenstler(@QueryParam("kuenstlername") String kuenstlername){
		AnwenderService anwenderService = new AnwenderService(dataSource);
		return anwenderService.getKuenstler(kuenstlername);
	}

	@Path("/alben")
	@GET
	public Response getAlben(@QueryParam("trackanzahl")@DefaultValue("-1") int trackanzahl,@QueryParam("bezeichnung")String bezeichnung){
		AnwenderService anwenderService = new AnwenderService(dataSource);
		return anwenderService.getAlben(trackanzahl,bezeichnung);
	}

	@Path("/genres")
	@GET
	public Response getGenres(@QueryParam("bezeichnung")String bezeichnung){
		AnwenderService anwenderService = new AnwenderService(dataSource);
		return anwenderService.getGenres(bezeichnung);
	}

	@Path("/titel")
	@GET
	public Response getTitel(@QueryParam("dauer")@DefaultValue("-1") int dauer,@QueryParam("bezeichnung")String bezeichnung){
		AnwenderService anwenderService = new AnwenderService(dataSource);
		return anwenderService.getTitel(dauer,bezeichnung);
	}

	@Path("/playlists")
	@GET
	public Response getPlaylists(@QueryParam("ist_privat")Boolean ist_privat,@QueryParam("bezeichnung")String bezeichnung){
		AnwenderService anwenderService = new AnwenderService(dataSource);
		return anwenderService.getPlaylists(ist_privat,bezeichnung);
	}

	@Path("/bands")
	@GET
	public Response getBands(@QueryParam("name")String name,@QueryParam("geschichte")String geschichte){
		AnwenderService anwenderService = new AnwenderService(dataSource);
		return anwenderService.getBands(name,geschichte);
	}

	@Path("/titel/{titelid}/kommentare")
	@GET
	public Response getKommentare(@PathParam("titelid")int titelId){
		AnwenderService anwenderService = new AnwenderService(dataSource);
		return anwenderService.getKommentare(titelId);
	}
}
