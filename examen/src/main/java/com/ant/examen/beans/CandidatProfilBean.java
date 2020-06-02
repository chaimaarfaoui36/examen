package com.ant.examen.beans;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.UploadedFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ant.examen.entities.Candidat;
import com.ant.examen.model.MessageResponse;
import com.ant.examen.model.Password;
import com.ant.examen.services.CandidatService;
import com.ant.examen.services.FlickrService;

@ManagedBean
@ViewScoped
public class CandidatProfilBean {

	private CandidatService candidatService = new CandidatService();
	private Candidat candidat = new Candidat();
	private UploadedFile file;
	private FlickrService flickrService = new FlickrService();

	@PostConstruct
	public void init() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		candidat = candidatService.findByEmail(authentication.getName());
	}

	public void updateProfil() {
		try {
			if (file!= null && file.getSize() > 0) {
				String url = flickrService.uploadImage(file.getInputstream(), file.getFileName());
				candidat.setImage(url);
			}

			MessageResponse result = candidatService.update(candidat);
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

	public Candidat getCandidat() {
		return candidat;
	}

	public void setCandidat(Candidat candidat) {
		this.candidat = candidat;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

}
