package com.soft.service;

import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

import com.soft.model.Cliente;
import com.soft.model.Usuario;
import com.soft.util.FacesMessages;

public class CadastroClienteService implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private final String  Url = "http://localhost:8080/clientes";
	
	@Inject
	private Usuario usuario;
	
    @Inject
    private FacesMessages messages;

	public void salvar(Cliente cliente) {
		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target(Url);
        WebTarget resourceWebTarget = webTarget.path("clientes/");
		if (cliente.getId() == null) {
	        WebTarget pathdWebTarget = resourceWebTarget.path("");
	        WebTarget pathdWebTargetQuery = pathdWebTarget.queryParam("");

	        usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
	        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(usuario.getLogin(),usuario.getSenha());
	        pathdWebTargetQuery.register(feature);
	        
	        Invocation.Builder invocationBuilder =  
	        		pathdWebTargetQuery.request() ;
	        Response postResponse = invocationBuilder.
	        		post(Entity.entity(cliente, MediaType.APPLICATION_JSON_TYPE));
	        
	        if (postResponse.getStatus() != 201) {
	        	messages.info("Erro no cadastro do cliente");
	        }else {
	        	messages.info("Cliente cadastrado com sucesso!");
	        }
		}else {
	        WebTarget pathdWebTarget1 = resourceWebTarget.path(cliente.getId().toString());
	        WebTarget pathdWebTargetQuery = pathdWebTarget1.queryParam("");
	
	        usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
	        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(usuario.getLogin(),usuario.getSenha());
	        pathdWebTargetQuery.register(feature);

	        Invocation.Builder invocationBuilder =  
	        		pathdWebTargetQuery.request() ;//MediaType.APPLICATION_JSON_TYPE);
	        Response putResponse = invocationBuilder.put(Entity.entity(cliente, MediaType.APPLICATION_JSON_TYPE));
	        
	        if (putResponse.getStatus() != 200) {
	        	messages.info("Erro na alteração do cliente");
	        }else {
	        	messages.info("Cliente alterado com sucesso!");
	        }
			
		}
	}
	
	public void excluir(Cliente cliente) {
		Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target(Url);
        WebTarget resourceWebTarget = webTarget.path("clientes/");
        WebTarget pathdWebTarget = resourceWebTarget.path(cliente.getId().toString());
        WebTarget pathdWebTargetQuery = pathdWebTarget.queryParam("");

        usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(usuario.getLogin(),usuario.getSenha());
        pathdWebTargetQuery.register(feature);

        Invocation.Builder invocationBuilder =  
        		pathdWebTargetQuery.request() ;
        Response postResponse = invocationBuilder.delete();
   
        if (postResponse.getStatus() != 204) {
        	messages.info("Erro na exclusão do cliente");
        }else {
        	messages.info("Cliente excluido com sucesso!");
        }
	}

}