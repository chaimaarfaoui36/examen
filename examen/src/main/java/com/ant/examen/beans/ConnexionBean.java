package com.ant.examen.beans;

import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ant.examen.entities.Users;
import com.ant.examen.services.UsersService;

@ManagedBean
@SessionScoped
public class ConnexionBean {

	private String userConnected;
	private UsersService userService = new UsersService();
	private boolean admin, candidat, entreprise;
	private String role;
	private String photo;
	private String error;
	private boolean showError;

	public ConnexionBean() {

	}

	public boolean isAdmin() {
		return admin;
	}

	public String getUserConnected() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		Users user = (Users) userService.loadUserByUsername(username);
		if (user != null) {

			userConnected = user.getNom();

		}

		for (GrantedAuthority authority : authentication.getAuthorities()) {
			if (authority.getAuthority().equalsIgnoreCase("ROLE_ADMIN")) {
				admin = true;
				candidat = false;
				entreprise = false;
				role = "Adminsitratuer";
			} else if (authority.getAuthority().equalsIgnoreCase("ROLE_CANDIDAT")) {
				admin = false;
				candidat = true;
				entreprise = false;
				role = "Candidat";
			} else if (authority.getAuthority().equalsIgnoreCase("ROLE_ENTREPRISE")) {
				admin = false;
				candidat = false;
				entreprise = true;
				role = "Entreprise";
			}
		}

		return userConnected;
	}

	public void setUserConnected(String userConnected) {
		this.userConnected = userConnected;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getError() {

		FacesContext context = FacesContext.getCurrentInstance();
		Map<String, String> paramMap = context.getExternalContext().getRequestParameterMap();
		String result = paramMap.get("error");

		if (result != null && result.equals("1")) {
			showError = true;
		}

		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public boolean isShowError() {
		return showError;
	}

	public void setShowError(boolean showError) {
		this.showError = showError;
	}

	public boolean isCandidat() {
		return candidat;
	}

	public void setCandidat(boolean candidat) {
		this.candidat = candidat;
	}

	public boolean isEntreprise() {
		return entreprise;
	}

	public void setEntreprise(boolean entreprise) {
		this.entreprise = entreprise;
	}

	public String getPhoto() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		Users user = (Users) userService.loadUserByUsername(username);
		if (user != null) {
			photo = user.getImage();
		}
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

}
