package com.ant.examen.beans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.UploadedFile;

import com.ant.examen.entities.Entreprise;
import com.ant.examen.model.MessageResponse;
import com.ant.examen.services.EntrepriseService;
import com.ant.examen.services.FlickrService;

@ManagedBean
@ViewScoped
public class EntrepriseBean {
	private List<Entreprise> list = new ArrayList<>();
	private Entreprise entreprise = new Entreprise();
	private EntrepriseService entrepriseService = new EntrepriseService();
	private String confirmPwd;
	private UploadedFile file;
	private FlickrService flickrService = new FlickrService();

	public void activate(Entreprise cand) {
		try {
			MessageResponse result = entrepriseService.activate(cand);
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

	public String register() {

		try {
			if (file.getSize() != 0) {
				if (confirmPwd.equals(entreprise.getPassword())) {
					String url = flickrService.uploadImage(file.getInputstream(), file.getFileName());
					entreprise.setImage(url);
					MessageResponse result = entrepriseService.register(entreprise);
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
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Attention", "Logo réquis"));
			}

		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "Opération non effectuée"));
			e.printStackTrace();
		}
		return null;
	}

	public List<Entreprise> getList() {
		try {
			list = entrepriseService.findAll();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public void setList(List<Entreprise> list) {
		this.list = list;
	}

	public Entreprise getEntreprise() {
		return entreprise;
	}

	public void setEntreprise(Entreprise entreprise) {
		this.entreprise = entreprise;
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
