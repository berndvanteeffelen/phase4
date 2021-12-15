package de.hhu.cs.dbs.propra.presentation.rest;

import de.hhu.cs.dbs.propra.application.services.PremiumnutzerService;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.sql.DataSource;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Path("/")
@Consumes(MediaType.MULTIPART_FORM_DATA)
@Produces(MediaType.APPLICATION_JSON)
public class PremiumController {
	@Inject
	private DataSource dataSource;

	@Context
	private SecurityContext securityContext;

	@Context
	private UriInfo uriInfo;

	@Path("/playlists")
	@RolesAllowed({"KUENSTLER","PREMIUMNUTZER"})
	@POST
	public Response addPlaylist(@FormDataParam("bezeichnung")String bezeichnung, @FormDataParam("ist_privat") Boolean privat, @FormDataParam("coverbild")String coverbild){
		PremiumnutzerService premiumnutzerService = new PremiumnutzerService(dataSource);
		return premiumnutzerService.addPlaylist(bezeichnung,privat,coverbild,securityContext.getUserPrincipal().getName());
	}

	@Path("/playlists/{playlistid}/titel")
	@RolesAllowed({"KUENSTLER","PREMIUMNUTZER"})
	@POST
	public Response addToPlaylist(@FormDataParam("titel")int titel, @PathParam("playlistid")int playlistid){
		PremiumnutzerService premiumnutzerService = new PremiumnutzerService(dataSource);
		return premiumnutzerService.addToPlaylist(titel,playlistid);
	}
}
