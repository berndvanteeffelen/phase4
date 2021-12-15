package de.hhu.cs.dbs.propra.presentation.rest;

import de.hhu.cs.dbs.propra.application.services.KuenstlerService;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.sql.DataSource;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Path("/")
@Consumes(MediaType.MULTIPART_FORM_DATA)
@Produces(MediaType.APPLICATION_JSON)
public class KuenstlerController {
	@Inject
	private DataSource dataSource;

	@Context
	private SecurityContext securityContext;

	@Context
	private UriInfo uriInfo;

	@Path("/alben")
	@RolesAllowed({"KUENSTLER"})
	@POST
	public Response addAlbum(@FormDataParam("bezeichnung")String bezeichnung, @FormDataParam("erscheinungsjahr")String erscheinungsjahr){
		KuenstlerService kuenstlerService = new KuenstlerService(dataSource);
		return kuenstlerService.addAlbum(bezeichnung,erscheinungsjahr,securityContext.getUserPrincipal().getName());
	}

	@Path("/titel")
	@RolesAllowed({"KUENSTLER"})
	@POST
	public Response addTitel(@FormDataParam("bezeichnung")String bezeichnung, @FormDataParam("dauer")int dauer, @FormDataParam("speicherort_lq")String lq, @FormDataParam("speicherort_hq")String hq){
		KuenstlerService kuenstlerService = new KuenstlerService(dataSource);
		return kuenstlerService.addTitel(bezeichnung,dauer,lq,hq,securityContext.getUserPrincipal().getName());
	}

	@Path("/bands")
	@RolesAllowed({"KUENSTLER"})
	@POST
	public Response addBand(@FormDataParam("name")String name, @FormDataParam("geschichte")String geschichte){
		KuenstlerService kuenstlerService = new KuenstlerService(dataSource);
		return kuenstlerService.addBand(name,geschichte);
	}

	@Path("/bands/{bandid}/kuenstler")
	@RolesAllowed({"KUENSTLER"})
	@POST
	public Response addToBand(@PathParam("bandid")String name, @FormDataParam("kuenstlerid")String geschichte){
		KuenstlerService kuenstlerService = new KuenstlerService(dataSource);
		return kuenstlerService.addToBand(name,geschichte);
	}

	@Path("/bands/{bandid}")
	@RolesAllowed({"KUENSTLER"})
	@DELETE
	public Response deleteBand(@PathParam("bandid")String bandid){
		KuenstlerService kuenstlerService = new KuenstlerService(dataSource);
		return kuenstlerService.deleteBand(bandid);
	}
}
