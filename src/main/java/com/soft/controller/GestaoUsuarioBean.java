package com.soft.controller;

import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.soft.model.Usuario;
import com.soft.repository.UsuarioRepository;
import com.soft.util.FacesMessages;

@Named
@ViewScoped
public class GestaoUsuarioBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private Usuario usuario ;
        
	@Inject
    private UsuarioRepository usuarioRepository;
    
    @Inject
    private FacesMessages messages;
    
    private String login;
    
    private String senha;


    public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;

	}

    public String doLogin() {
 
    	if ((login == "") || (senha == "")) {
        	messages.info("Dados de Login incompletos, favor inserir novamente");
        } else {	
        	usuario = usuarioRepository.buscaLogin(login, senha);
        	if (usuario.getSenha() != null) {
        		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", usuario);
        		return "/GestaoCliente.xhtml?faces-redirect=true";
        	}
        }	
        return "";
    }

}

