package io.github.aparnachaudhary.exceptions;

import io.github.aparnachaudhary.entities.RestErrorEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {

    private static final Logger LOGGER = LoggerFactory.getLogger(GenericExceptionMapper.class);

    @Override
    public Response toResponse(Throwable throwable) {
        LOGGER.error("Unhandled exception resulting in internal server error response", throwable);
        RestErrorEntity restErrorEntity = new RestErrorEntity(50001, throwable.getMessage(), throwable.getMessage());
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).type(MediaType.APPLICATION_JSON_TYPE).entity(restErrorEntity).build();
    }
}
