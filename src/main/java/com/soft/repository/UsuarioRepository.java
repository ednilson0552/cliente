package com.soft.repository;

import java.io.Serializable;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

import com.soft.model.Usuario;
import com.soft.util.FacesMessages;

public class UsuarioRepository implements Serializable {

	private static final long serialVersionUID = 1L;
	
    @Inject
    private FacesMessages messages;

	private final  String  Url = "http://localhost:8080/clientes";

	public UsuarioRepository() {

	}

	public Usuario buscaLogin(String login, String senha) {
		Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target(Url);
        WebTarget resourceWebTarget = webTarget.path("clientes/login/");
        WebTarget pathdWebTarget = resourceWebTarget.path(login);//
        WebTarget pathdWebTargetQuery = pathdWebTarget.queryParam("");

        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(login,senha);
        pathdWebTargetQuery.register(feature);
        Invocation.Builder invocationBuilder = 
        		pathdWebTargetQuery.request(MediaType.APPLICATION_JSON_TYPE);

        Usuario usuario =  new Usuario();
        try {    
        	Response response = invocationBuilder.get();
	        if (response.getStatus() == 202) { 
	        	messages.info("Usuario com permissão de acesso");
	            usuario.setLogin(login);  
	            usuario.setSenha(senha);
	        }else {
	        	messages.info("Usuario sem permissão de acesso");
	        }
	    } catch  (Exception erro1) {
	        messages.info("Erro de Conexão com Banco de Dados");
	    }
        
        return usuario;
	}
	
}
