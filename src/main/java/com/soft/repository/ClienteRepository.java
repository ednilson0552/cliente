package com.soft.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

import com.soft.model.Cliente;
import com.soft.model.Usuario;
import com.soft.util.FacesMessages;

public class ClienteRepository implements Serializable {

	private static final long serialVersionUID = 1L;

    @Inject
    private FacesMessages messages;

    @Inject
    private Usuario usuario; 
    
    private final String  Url = "http://localhost:8080/clientes";
	
	public ClienteRepository() {

	}

	public Cliente porId(Long id) {
		Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target(Url);
        WebTarget resourceWebTarget = webTarget.path("clientes/");
        WebTarget pathdWebTarget = resourceWebTarget.path(id.toString());
        WebTarget pathdWebTargetQuery = pathdWebTarget.queryParam("");

        usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(usuario.getLogin(),usuario.getSenha());
        pathdWebTargetQuery.register(feature);
        
        Invocation.Builder invocationBuilder = 
        		pathdWebTargetQuery.request(MediaType.APPLICATION_JSON_TYPE);

        Response response = invocationBuilder.get();

        Cliente cliente  = new Cliente();
        cliente = response.readEntity(Cliente.class); 
        return cliente;
	}

	public List<Cliente> pesquisar(String cpf) {
		Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target(Url);
        WebTarget resourceWebTarget = webTarget.path("clientes/cpf/");
        WebTarget pathdWebTarget = resourceWebTarget.path(cpf);//
        WebTarget pathdWebTargetQuery = pathdWebTarget.queryParam("");

        usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
    
        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(usuario.getLogin(),usuario.getSenha());
        pathdWebTargetQuery.register(feature);

        Invocation.Builder invocationBuilder = 
        		pathdWebTargetQuery.request(MediaType.APPLICATION_JSON_TYPE);
        
        Response response = invocationBuilder.get();
        Cliente cliente  = new Cliente();
        ArrayList<Cliente> listaCliente = new ArrayList<Cliente>();

        if (response.getStatus() != 200) {
        	messages.info("Cliente não encontrado ");
        }else {
            cliente = response.readEntity(Cliente.class); 
            listaCliente.add(cliente);
        }
        return listaCliente;
	}
	
	public List<Cliente> todos() {
		Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target(Url);
        WebTarget resourceWebTarget = webTarget.path("clientes");
        WebTarget pathdWebTarget = resourceWebTarget.path("");//
        WebTarget pathdWebTargetQuery = pathdWebTarget.queryParam("");

        usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(usuario.getLogin(),usuario.getSenha());
        pathdWebTargetQuery.register(feature);

        Invocation.Builder invocationBuilder = 
        		pathdWebTargetQuery.request(MediaType.APPLICATION_JSON_TYPE);
        Response response = invocationBuilder.get();
        List<Cliente> clientes = new ArrayList<Cliente>();

        if (response.getStatus() != 200) {
        	messages.info("Não exsite nenhum cliente cadastrado");
        }else {
        	
        	clientes = response.readEntity(new GenericType<List<Cliente>>() {});
        }	
        
        return clientes;
    }

	public Boolean pesquisaCpf(String cpf) {
		Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target(Url);
        WebTarget resourceWebTarget = webTarget.path("clientes/cpf/");
        WebTarget pathdWebTarget = resourceWebTarget.path(cpf);//
        WebTarget pathdWebTargetQuery = pathdWebTarget.queryParam("");

        usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
    
        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(usuario.getLogin(),usuario.getSenha());
        pathdWebTargetQuery.register(feature);

        Invocation.Builder invocationBuilder = 
        		pathdWebTargetQuery.request(MediaType.APPLICATION_JSON_TYPE);
        Boolean achou = false;
        Response response = invocationBuilder.get();
        if (response.getStatus() == 200) {
            achou = true; 
        	messages.info("CPF já cadastrado para outro cliente ");
        }
        return achou;
	}

	public Boolean pesquisaEmail(String email) {
		Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target(Url);
        WebTarget resourceWebTarget = webTarget.path("clientes/email/");
        WebTarget pathdWebTarget = resourceWebTarget.path(email);
        WebTarget pathdWebTargetQuery = pathdWebTarget.queryParam("");

        usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(usuario.getLogin(),usuario.getSenha());
        pathdWebTargetQuery.register(feature);
        Invocation.Builder invocationBuilder = 
        		pathdWebTargetQuery.request(MediaType.APPLICATION_JSON_TYPE);
        Boolean achou = false;
        Response response = invocationBuilder.get();
        if (response.getStatus() == 200) {
            achou = true; 
        	messages.info("EMAIL já cadastrado para outro cliente ");
        }
        return achou;
	}
	
}

