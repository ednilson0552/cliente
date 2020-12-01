package com.soft.util;

import javax.faces.application.Application;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import com.soft.model.Usuario;

public class Listener implements PhaseListener{

	private static final long serialVersionUID = 1L;

    @Override
	public void beforePhase(PhaseEvent arg0) {
	}

	@Override
	public void afterPhase(PhaseEvent arg0) {
	     FacesContext ctx = FacesContext.getCurrentInstance();
	     if(!ctx.getViewRoot().getViewId().equals("/Login.xhtml")){
	         Application app = ctx.getApplication();
	         Usuario u = app.evaluateExpressionGet(ctx, "#{usuario}", Usuario.class);
	         if(u == null) {  
	             NavigationHandler handler = app.getNavigationHandler();
	             handler.handleNavigation(ctx, "", "/Login.xhtml?faces-redirect=true");
	             ctx.renderResponse();
	         }
	     }
	}

	@Override
	public PhaseId getPhaseId() {
	    return PhaseId.RESTORE_VIEW;
	}
}
