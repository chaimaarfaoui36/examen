package com.ant.examen.beans;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.UploadedFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ant.examen.entities.Entreprise;
import com.ant.examen.model.MessageResponse;
import com.ant.examen.services.EntrepriseService;
import com.ant.examen.services.FlickrService;

@ManagedBean
@ViewScoped
public class EntrepriseProfilBean {

	private EntrepriseService entrepriseService = new EntrepriseService();
	private Entreprise entreprise = new Entreprise();
	private UploadedFile file;
	private FlickrService flickrService = new FlickrService();

	@PostConstruct
	public void init() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		entreprise = entrepriseService.findByEmail(authentication.getName());
	}

	public void updateProfil() {
		try {
			if (file != null && file.getSize() > 0) {
				String url = flickrService.uploadImage(file.getInputstream(), file.getFileName());
				entreprise.setImage(url);
			}

			MessageResponse result = entrepriseService.update(entreprise);
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

	public Entreprise getEntreprise() {
		return entreprise;
	}

	public void setEntreprise(Entreprise entreprise) {
		this.entreprise = entreprise;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

}
