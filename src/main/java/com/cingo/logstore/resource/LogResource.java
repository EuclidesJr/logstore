package com.cingo.logstore.resource;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.cingo.logstore.entity.Log;
import com.cingo.logstore.repostory.LogRepository;

@Path("log")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LogResource {
	
	@Context
	private HttpServletRequest httpRequest;
    private LogRepository repository = new LogRepository();
	
    @GET
    public List<Log> getLogs() {
    	return this.repository.findAllOrdered();
    }

	@POST
	public Response add(Log log) {
		repository.add(log);
    	return Response
                .status(200)
                .build();
    }

    @DELETE
	@Path("{id}")
	public Response delete(@PathParam("id") Integer id) {
    	try {
			Log log = repository.findById(id);
			repository.delete(log);
			return Response
					.status(204)
					.build();
    	} catch (NoResultException | NonUniqueResultException e) {
    		return Response.status(404).build();
    	}
	}
}
