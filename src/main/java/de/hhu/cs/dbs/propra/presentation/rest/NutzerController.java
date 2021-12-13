package de.hhu.cs.dbs.propra.presentation.rest;

import de.hhu.cs.dbs.propra.application.services.NutzerService;
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
public class NutzerController {
	@Inject
	private DataSource dataSource;

	@Context
	private SecurityContext securityContext;

	@Context
	private UriInfo uriInfo;

	@Path("/titel/{titelid}/kommentare")
	@RolesAllowed({"KUENSTLER","PREMIUMNUTZER","NUTZER"})
	@POST
	public Response addKommentar(@PathParam("titelid")int titelid, @FormDataParam("text") String text){
		NutzerService nutzerService = new NutzerService(dataSource);
		return nutzerService.addKommentar(titelid,text,securityContext.getUserPrincipal().getName());
	}

	@Path("/kommentare/{kommentarid}")
	@RolesAllowed({"KUENSTLER","PREMIUMNUTZER","NUTZER"})
	@PATCH
	public Response editKommentar(@PathParam("kommentarid")int kommentarid, @FormDataParam("text") String text){
		NutzerService nutzerService = new NutzerService(dataSource);
		return nutzerService.editKommentar(kommentarid,text);
	}
}
