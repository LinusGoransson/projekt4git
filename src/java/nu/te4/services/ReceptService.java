/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nu.te4.services;

import javax.ejb.EJB;
import javax.json.JsonArray;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import nu.te4.beans.ReceptBean;

@Path("/")
public class ReceptService {
    
    @EJB
    ReceptBean receptBean;
    
    
    @GET
    @Path("Rec_Ing")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRec_Ing() {

        JsonArray data = receptBean.getRec_Ing();
        
        if(data == null){
            return Response.serverError().build();
        }
        
        return Response.ok(data).build();
    }
    
    @POST
    @Path("Ing")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addIng(String body){
         
    if(!receptBean.addIng(body)){
         return Response.status(Response.Status.BAD_REQUEST).build();
     }
     
     return Response.ok().build(); 
    }
    
    @POST
    @Path("Rec")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addRec(String body){
         
    if(!receptBean.addRec(body)){
         return Response.status(Response.Status.BAD_REQUEST).build();
     }
     
     return Response.ok().build(); 
    }
    
    @DELETE
    @Path("ing/{id}")
    public Response deleteIng(@PathParam("id") int id){
      
     if(!receptBean.deleteIng(id)){
         return Response.status(Response.Status.BAD_REQUEST).build();
     }
     
     return Response.ok().build();        
    }
    
    @PUT
    @Path("Ing")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateIng(String body){

     if(!receptBean.updateIng(body)){
         return Response.status(Response.Status.BAD_REQUEST).build();
     }
     return Response.ok().build(); 
    }
    
    @PUT
    @Path("Rec")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateRec(String body){

     if(!receptBean.updateRec(body)){
         return Response.status(Response.Status.BAD_REQUEST).build();
     }
     return Response.ok().build(); 
    }
  
}
