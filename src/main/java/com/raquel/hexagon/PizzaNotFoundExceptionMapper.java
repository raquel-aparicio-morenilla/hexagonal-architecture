package com.raquel.hexagon;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class PizzaNotFoundExceptionMapper
    implements ExceptionMapper<PizzaNotFoundException> {

  @Override
  public Response toResponse(PizzaNotFoundException e) {
    return Response.status(Response.Status.NOT_FOUND).build();
  }
}
