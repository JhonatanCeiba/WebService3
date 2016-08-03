package com.sample;


import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.FormParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.io.KieResources;
import org.kie.api.io.Resource;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;


import java.io.IOException;
import java.util.ArrayList;

@Path("/service2")
public class SimpleRestService2 {

	   private static final String SUCCESS_RESULT="<result>success</result>";
	   private static final String FAILURE_RESULT="<result>failure</result>";

	   @GET
	   @Path("/angularr")
	   @Produces(MediaType.TEXT_PLAIN)
	   public String angularr()
	   {
		   return "Hoola";
	   }
	   
	  
	  @GET
	   @Path("/angular")
	   @Produces(MediaType.APPLICATION_JSON)
	  public Usuario angular(@QueryParam("nombre") String nombre,
			  @QueryParam("sueldo") int sueldo) {
		  try {
	    	   KieServices kieServices = KieServices.Factory.get();
	    	    KieFileSystem kfs = kieServices.newKieFileSystem();
	    	    KieResources kieResources = kieServices.getResources();
	    	    Resource resource = kieResources.newClassPathResource("rulesUsuario.drl");
	    	    kfs.write("src/main/resources/com/sample/rulesUsuario.drl", resource);
	    	    
	    	    KieBuilder kieBuilder = kieServices.newKieBuilder( kfs ).buildAll();
	    	    Results results = kieBuilder.getResults();
	    	    
	    	    if( results.hasMessages( Message.Level.ERROR ) ){
	    	        System.out.println( results.getMessages() );
	    	        throw new IllegalStateException( "### errors ###" );
	    	    }
	    	    KieContainer kieContainer = kieServices.newKieContainer( kieServices.getRepository().getDefaultReleaseId() );
	    	    KieSession ksession = kieContainer.newKieSession();

	            // go !
	            Usuario usuario = new Usuario(nombre,sueldo);
	            ksession.insert(usuario);
	            ksession.fireAllRules();
	            return usuario;
	            
	        } catch (Throwable t) {
	            t.printStackTrace();
	            return null;
	        }
	  }
	  
	 

	
}
