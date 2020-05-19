package com.ant.examen.beans;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.UploadedFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ant.examen.entities.Administrateur;
import com.ant.examen.model.MessageResponse;
import com.ant.examen.model.Password;
import com.ant.examen.services.AdminService;
import com.ant.examen.services.FlickrService;

@ManagedBean
@ViewScoped
public class AdminProfilBean {

	private AdminService adminService = new AdminService();
	private Administrateur admin = new Administrateur();
	private UploadedFile file;
	private FlickrService flickrService = new FlickrService();

	@PostConstruct
	public void init() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		admin = adminService.findByEmail(authentication.getName());
	}

	public void updateProfil() {
		try {
			if (file.getSize() > 0) {
				String url = flickrService.uploadImage(file.getInputstream(), file.getFileName());
				admin.setImage(url);
			}

			MessageResponse result = adminService.update(admin);
			if (result.isSuccess()) {

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

	public Administrateur getAdmin() {
		return admin;
	}

	public void setAdmin(Administrateur admin) {
		this.admin = admin;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

}
