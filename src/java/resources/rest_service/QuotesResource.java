/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package resources.rest_service;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import services.CServicesBridge;

/**
 * REST Web Service
 *
 * @author vladilie
 */
@Path("/qoute")
public class QuotesResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of QuotesResource
     */
    public QuotesResource() {
    }

    /**
     * Retrieves representation of an instance of resources.QuotesResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public String getJson() {
        StringBuffer strDocumentBuffer = new StringBuffer();
        CServicesBridge.getInstance().getAllChangeRates(strDocumentBuffer);
        String strDocument = new String(strDocumentBuffer.toString());
        return strDocument;
    }

    /**
     * POST method for creating an instance of QuoteResource
     * @param content representation for the new resource
     * @return an HTTP response with content of the created resource
     */
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response postJson(String content) {
        //TODO
        return Response.created(context.getAbsolutePath()).build();
    }

    /**
     * Sub-resource locator method for {c1c2}
     */
    @Path("{c1c2}")
    public QuoteResource getQuoteResource(@PathParam("c1c2") String c1c2) {
        return QuoteResource.getInstance(c1c2);
    }
}
