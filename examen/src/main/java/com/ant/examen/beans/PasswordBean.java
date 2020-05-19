package com.ant.examen.beans;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ant.examen.model.MessageResponse;
import com.ant.examen.model.Password;
import com.ant.examen.services.UsersService;

@ManagedBean
@ViewScoped
public class PasswordBean {

	
	private UsersService usersService = new UsersService();
	private Password password = new Password();
	
	
	
	public void changePassword() {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			password.setEmail(authentication.getName());
			MessageResponse result = usersService.changePasswrod(password);
			if (result.isSuccess()) {
				
				password = new Password();
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", result.getMessage()));
			
			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", result.getMessage()));
			}

		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "Opération non effectuée"));
			e.printStackTrace();
		}

	}
	
	
	public Password getPassword() {
		return password;
	}
	public void setPassword(Password password) {
		this.password = password;
	}
	
	
	
}
