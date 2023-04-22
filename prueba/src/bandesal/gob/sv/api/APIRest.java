package bandesal.gob.sv.api;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import bandesa.gob.sv.responses.ConsultaLectores;
import bandesa.gob.sv.responses.GenericResponse;
import bandesal.gob.sv.sessions.BandesalSBSLLocal;

@Stateless
@Path("/servicios")
public class APIRest {

	@EJB
	protected BandesalSBSLLocal bandesalSBSL;

	@GET
	@Path("/lectoresPorBlog")
	@Produces({ MediaType.APPLICATION_JSON })
	@Transactional
	public Response lectoresPorBlog() {
		try {
			@SuppressWarnings("unchecked")
			List<ConsultaLectores> lectores = (List<ConsultaLectores>) bandesalSBSL
					.findByNamedQuery(ConsultaLectores.class, "readers.by.blog", null);

			if (lectores != null && !lectores.isEmpty()) {
				return Response.status(Response.Status.OK).entity(lectores).build();
			} else {
				return Response.status(Response.Status.NOT_FOUND).entity(
						new GenericResponse(Response.Status.NOT_FOUND.getStatusCode(), "No se encontraron resultados"))
						.build();
			}

		} catch (Exception e) {
			// TODO: handle exception
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new GenericResponse(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), "Error interno"))
					.build();
		}
	}

}
