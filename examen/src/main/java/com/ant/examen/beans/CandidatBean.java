package com.ant.examen.beans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.UploadedFile;

import com.ant.examen.entities.Candidat;
import com.ant.examen.model.MessageResponse;
import com.ant.examen.services.CandidatService;
import com.ant.examen.services.FlickrService;

@ManagedBean
@ViewScoped
public class CandidatBean {

	private List<Candidat> list = new ArrayList<>();
	private Candidat candidat = new Candidat();
	private CandidatService candidatService = new CandidatService();
	private String confirmPwd;
	private UploadedFile file;
	private FlickrService flickrService = new FlickrService();

	public void activate(Candidat cand) {
		try {
			MessageResponse result = candidatService.activate(cand);
			if (result.isSuccess()) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", result.getMessage()));

			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", result.getMessage()));
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "Op�ration non effectu�e"));
			e.printStackTrace();
		}
	}

	public String register() {
		try {
			if (file.getSize() != 0) {
				if (confirmPwd.equals(candidat.getPassword())) {
					String url = flickrService.uploadImage(file.getInputstream(), file.getFileName());
					candidat.setImage(url);
					MessageResponse result = candidatService.register(candidat);
					if (result.isSuccess()) {
						FacesContext.getCurrentInstance().addMessage(null,
								new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", result.getMessage()));
						return "Login";
					} else {
						FacesContext.getCurrentInstance().addMessage(null,
								new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", result.getMessage()));
					}
				} else {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Attention", "Les mots de passe ne sont pas identiques"));
				}
			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Attention", "Logo r�quis"));
			}

		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "Op�ration non effectu�e"));
			e.printStackTrace();
		}

		return null;
	}

	public List<Candidat> getList() {
		try {
			list = candidatService.findAll();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public void setList(List<Candidat> list) {
		this.list = list;
	}

	public Candidat getCandidat() {
		return candidat;
	}

	public void setCandidat(Candidat candidat) {
		this.candidat = candidat;
	}

	public String getConfirmPwd() {
		return confirmPwd;
	}

	public void setConfirmPwd(String confirmPwd) {
		this.confirmPwd = confirmPwd;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

}
