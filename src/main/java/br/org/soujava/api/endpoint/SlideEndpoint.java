package br.org.soujava.api.endpoint;

import static javax.ws.rs.core.Response.created;
import static javax.ws.rs.core.Response.ok;
import static javax.ws.rs.core.Response.status;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import br.org.soujava.api.json.JsonMessage;
import br.org.soujava.domain.Slide;
import br.org.soujava.integration.resteasy.MediaType;
import br.org.soujava.service.SlideService;


/**
 * Endpoint of the Slide
 * 
 * @author arruda
 */
@Produces(MediaType.APPLICATION_JSON_UTF8)
@Path("/slides")
@RequestScoped
public class SlideEndpoint {
	
	@Inject
	private SlideService slideService;
	
	@GET
	public Response findAll() {
		final List<Slide> slides = slideService.findAll();
		
		return ok(slides).build();
		
		//return ok(JsonMessage.builder().data(slides).build()).build();
	}

	@GET
	@Path("/{id}")
	public Response findById(@PathParam("id") @NotNull String id) {
		final Slide slide = slideService.findbyId(id);

		if (slide == null) {
			return status(404).build();
		}

		return ok(JsonMessage.builder().data(slide).build()).build();
	}

	@POST
	public Response create(@NotNull Slide slide) throws URISyntaxException {

		slideService.save(slide);

		return created(new URI("/api/slides/" + slide.getId())).entity(JsonMessage.builder().data(slide).build())
				.build();
	}

	@PUT
	@Path("/{id}")
	public Response update(@NotNull Slide slide, @PathParam("id") @NotNull String id) throws URISyntaxException {

		slide.setId(id);

		slideService.update(slide);

		return ok(JsonMessage.builder().data(slide).build()).build();
	}

	@DELETE
	@Path("/{id}")
	public Response delete(@PathParam("id") @NotNull String id) throws URISyntaxException {

		slideService.delete(id);

		return ok(JsonMessage.builder().build()).build();
	}

}
