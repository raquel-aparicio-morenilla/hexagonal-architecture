package com.raquel.hexagon.infrastructure.inputAdapter;

import com.raquel.hexagon.domain.object.PizzaNotValidException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class PizzaNotValidExceptionMapper
        implements ExceptionMapper<PizzaNotValidException> {

    @Override
    public Response toResponse(PizzaNotValidException e) {
        return Response.status(Response.Status.FORBIDDEN).build();
    }
}
