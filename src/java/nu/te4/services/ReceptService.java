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
import nu.te4.support.User;

@Path("/")
public class ReceptService {
    
    @EJB
    ReceptBean receptBean;
    
    
    @GET
    @Path("Rec_Ing/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRec_Ing(@Context HttpHeaders httpHeaders, @PathParam("id")int id) {

        JsonArray data = receptBean.getRec_Ing(id);
        
        if(data == null){
            return Response.serverError().build();
        }
        
        return Response.ok(data).build();
    }
    //getLastRec
    @GET
    @Path("LastRec")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRec_IngKatASC(@Context HttpHeaders httpHeaders) {

        JsonArray data = receptBean.getLastRec();
        
        if(data == null){
            return Response.serverError().build();
        }
        
        return Response.ok(data).build();
    }
    
    @GET
    @Path("Rec_IngKatDESC")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRec_IngKatDESC(@Context HttpHeaders httpHeaders) {
        if (!User.authoricate(httpHeaders)) {
           return Response.status(401).build();
        }
        JsonArray data = receptBean.getRec_IngKatDESC();
        
        if(data == null){
            return Response.serverError().build();
        }
        
        return Response.ok(data).build();
    }
    
    
    
    @POST
    @Path("Ing")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addIng(String body,@Context HttpHeaders httpHeaders){
     if (!User.authoricate(httpHeaders)) {
           return Response.status(401).build();
        }
     if(!receptBean.addIng(body)){
         return Response.status(Response.Status.BAD_REQUEST).build();
     }
     
     return Response.status(Response.Status.CREATED).build();
    }
    
    @POST
    @Path("Rec")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addRec(String body,@Context HttpHeaders httpHeaders){
     if (!User.authoricate(httpHeaders)) {
           return Response.status(401).build();
        }
     if(!receptBean.addRec(body)){
         return Response.status(Response.Status.BAD_REQUEST).build();
     }
     
     return Response.status(Response.Status.CREATED).build();
    }
    
    @POST
    @Path("Rec_Ing")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addRec_Ing(String body,@Context HttpHeaders httpHeaders){
     if (!User.authoricate(httpHeaders)) {
           return Response.status(401).build();
        }
     if(!receptBean.addRec_Ing(body)){
         return Response.status(Response.Status.BAD_REQUEST).build();
     }
     
     return Response.status(Response.Status.CREATED).build();
    }
    
    @DELETE
    @Path("ing/{id}")
    public Response deleteIng(@PathParam("id") int id,@Context HttpHeaders httpHeaders){
     if (!User.authoricate(httpHeaders)) {
           return Response.status(401).build();
        }
     if(!receptBean.deleteIng(id)){
         return Response.status(Response.Status.BAD_REQUEST).build();
     }
     
     return Response.ok().build();        
    }
    
    @DELETE
    @Path("Rec_Ing/{recept_id}")
    public Response deleteRec_Ing(@PathParam("recept_id") int recept_id,@Context HttpHeaders httpHeaders){
     if (!User.authoricate(httpHeaders)) {
           return Response.status(401).build();
        }
     if(!receptBean.deleteRec_Ing(recept_id)){
         return Response.status(Response.Status.BAD_REQUEST).build();
     }
     
     return Response.ok().build();        
    }
    
    @DELETE
    @Path("Ing_Rec/{r_id}/{i_id}")
    public Response deleteIng_Rec(@PathParam("r_id") int r_id,@PathParam("i_id") int i_id,@Context HttpHeaders httpHeaders){
     if (!User.authoricate(httpHeaders)) {
           return Response.status(401).build();
        }
     if(!receptBean.deleteIng_Rec(r_id, i_id)){
         return Response.status(Response.Status.BAD_REQUEST).build();
     }
     
     return Response.ok().build();        
    }
    
    @PUT
    @Path("Ing")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateIng(String body,@Context HttpHeaders httpHeaders){
     if (!User.authoricate(httpHeaders)) {
           return Response.status(401).build();
        }
     if(!receptBean.updateIng(body)){
         return Response.status(Response.Status.BAD_REQUEST).build();
     }
     
     return Response.status(Response.Status.CREATED).build();
    }
    
    @PUT
    @Path("Rec")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateRec(String body,@Context HttpHeaders httpHeaders){
     if (!User.authoricate(httpHeaders)) {
           return Response.status(401).build();
        }
     if(!receptBean.updateRec(body)){
         return Response.status(Response.Status.BAD_REQUEST).build();
     }
     
     return Response.status(Response.Status.CREATED).build();
    }
    
    @GET
    @Path("table")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTable(){
     JsonArray data = receptBean.getTable();
        if(data == null){
            return Response.serverError().build();
        }
        
        return Response.ok(data).build();
        
    }
    
    @GET
    @Path("ing")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getIng(){
     JsonArray data = receptBean.getIng();
        
        if(data == null){
            return Response.serverError().build();
        }
        
        return Response.ok(data).build();
        
    }
    
  
}
