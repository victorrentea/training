package com.crossover.trial.weather.webservice;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GenericExceptionMapper implements ExceptionMapper<Throwable> {
	
	private final static Logger log = LoggerFactory.getLogger(GenericExceptionMapper.class);
	
	@Override 
	public Response toResponse(Throwable ex) {
		log.error("REST endpoint threw exception: " + ex, ex);
 
		return Response.status(500)
				.entity(ex.getMessage())
				.type(MediaType.APPLICATION_JSON)
				.build();	
	}
 
}