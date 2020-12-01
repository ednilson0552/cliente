package com.soft.controller;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import com.soft.model.Cliente;
import com.soft.repository.ClienteRepository;
import com.soft.service.CadastroClienteService;
import com.soft.util.FacesMessages;

@Named
@ViewScoped
public class GestaoClienteBean implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Inject
    private ClienteRepository clienteRepository;
    
    @Inject
    private FacesMessages messages;
    
    @Inject
    private CadastroClienteService cadastroClienteService;
    
    private List<Cliente> listaClientes;
    
    private String termoPesquisa;
    
    private Cliente cliente;
    
    public void prepararNovoCliente() {
        cliente = new Cliente();
    }
    
    public void salvar() {
        cadastroClienteService.salvar(cliente);
        
        if (jaHouvePesquisa()) {
            pesquisar();
        }else {
        	cliente=null;
        	todosClientes();
        }
        RequestContext.getCurrentInstance().update(Arrays.asList(
		  "frm:clientesDataTable", "frm:messages" , "frm:toolbar" ));      
    }

    public void excluir() {
        cadastroClienteService.excluir(cliente);

        cliente=null;

        if (jaHouvePesquisa()) {
            pesquisar();
        }else {
        	todosClientes();
        }
    }
    
    
    public void pesquisar() {
    	if (termoPesquisa != "") {
	        listaClientes = clienteRepository.pesquisar(termoPesquisa);
	        
	        if (listaClientes.isEmpty()) {
	            messages.info("Sua consulta não retornou registros.");
	        	todosClientes();
	        }
	        termoPesquisa=null;
	        RequestContext.getCurrentInstance().update(Arrays.asList(
			  "frm:clientesDataTable", "frm:messages", "frm:termoPesquisa"));      
    
    	}else {
        	todosClientes();
    		
    	}
    	
    }
    
    public void todosClientes() {
        listaClientes = clienteRepository.todos();
    }
    
    
    private boolean jaHouvePesquisa() {
        return termoPesquisa != null && !"".equals(termoPesquisa);
    }
    
    public List<Cliente> getListaClientes() {
        return listaClientes;
    }
    
    public String getTermoPesquisa() {
        return termoPesquisa;
    }
    
    public void setTermoPesquisa(String termoPesquisa) {
        this.termoPesquisa = termoPesquisa;
    }
    
    
    public Cliente getCliente() {
        return cliente;
    }
    
	public void porId(Long id) {
		cliente = clienteRepository.porId(id);
	}	

	public void setCliente(Cliente cliente) {
		this.cliente=cliente;
		
	}
	
	public boolean isClienteSelecionado() {
		return cliente != null && cliente.getId() != null ;
	}	


	public String logoff() {
		
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "/Login.xhtml?faces-redirect=true";    
	}

    public void pesquisarCpf() {
        System.out.println("entrou pesquisacpf: "+cliente.getCpf());	
    	Boolean achou = clienteRepository.pesquisaCpf(cliente.getCpf());
    	if (achou) {
            System.out.println("Achou cliente cpf existente");
            cliente.setCpf(null);
    	}
    }	

    public void pesquisarEmail() {
    System.out.println("entrou pesquisaemail: "+cliente.getEmail());	
    	Boolean achou = clienteRepository.pesquisaEmail(cliente.getEmail());
    	if (achou) {
            System.out.println("Achou cliente email existente");
            cliente.setEmail(null);
    	}
    }	

}
