
package nu.te4.services;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import nu.te4.support.User;

@Path("/")
public class LoginService {

     @POST
     @Path("login")
     public Response checkLogin(@Context HttpHeaders httpHeaders){
      if (!User.authoricate(httpHeaders)) {
           return Response.status(401).build();
        }
      return Response.ok().build();
     }
     
     @POST
     @Path("register")
     public Response createUser(String body){
         if(!User.createUser(body)){
             return Response.status(Response.Status.BAD_REQUEST).build();
         }
      return Response.status(Response.Status.CREATED).build();
     }
     
     
    
}
