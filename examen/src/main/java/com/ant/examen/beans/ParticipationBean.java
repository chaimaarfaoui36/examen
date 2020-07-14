package com.ant.examen.beans;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ant.examen.entities.Candidat;
import com.ant.examen.entities.Examen;
import com.ant.examen.entities.Participation;
import com.ant.examen.entities.Question;
import com.ant.examen.entities.ReponseCandidat;
import com.ant.examen.entities.Theme;
import com.ant.examen.services.CandidatService;
import com.ant.examen.services.ExamenService;
import com.ant.examen.services.ParticipationService;
import com.ant.examen.services.QuestionService;
import com.ant.examen.services.ReponseCandidatService;
import com.ant.examen.services.ThemeService;

@ManagedBean
@ViewScoped
public class ParticipationBean {
	private String id;
	private Examen examen = new Examen();
	private ExamenService examenService = new ExamenService();
	private ParticipationService participationService = new ParticipationService();
	private Participation participation = new Participation();
	private CandidatService candidatService = new CandidatService();
	private LocalDateTime counter;
	private long diffSeconde;
	private QuestionService questionService = new QuestionService();
	private ThemeService themeService = new ThemeService();
	private List<Theme> themes = new ArrayList<>();
	// private ReponseService reponseService = new ReponseService();
	private ReponseCandidat singleReponse = new ReponseCandidat();
	private List<ReponseCandidat> multiReponses;
	private ReponseCandidatService reponseCandidatService = new ReponseCandidatService();

	@PostConstruct
	public void init() {

		try {
			Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext()
					.getRequestParameterMap();
			id = params.get("id");
			if (id != null) {
				Integer idQ = Integer.valueOf(id);
				examen = examenService.findById(idQ);
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
				Candidat candidat = candidatService.findByEmail(authentication.getName());
				participation.setCandidat(candidat);
				participation.setExamen(examen);
				participation = participationService.save(participation);
				themes = themeService.findByExamen(examen);

				Calendar cal = Calendar.getInstance();
				cal.setTime(participation.getDateParticipation());
				cal.add(Calendar.MINUTE, examen.getDuree());
				Date dateFin = cal.getTime();
				LocalDateTime debut = convertToLocalDateViaInstant(participation.getDateParticipation());
				LocalDateTime fin = convertToLocalDateViaInstant(dateFin);

				diffSeconde = ChronoUnit.SECONDS.between(LocalDateTime.now(), fin);
				
				if (diffSeconde <= 0 || participation.isFinished()) {

					try {
						String uri = "Resultat.xhtml?id=" + participation.getId();
						// FacesContext.getCurrentInstance().getExternalContext().dispatch(uri);
						FacesContext.getCurrentInstance().getExternalContext().redirect(uri);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					diffSeconde = 10;
					// return "Resultat.xhtml?id="+participation.getId();
				}

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// return null;
	}

	public void timerCompleted() {

		try {
			
			String uri = "Resultat.xhtml?id=" + participation.getId();
			// FacesContext.getCurrentInstance().getExternalContext().dispatch(uri);
			FacesContext.getCurrentInstance().getExternalContext().redirect(uri);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public List<Question> findQuestionByTheme(Theme theme) {
		return questionService.findByExamenAndTheme(examen, theme);
	}

	public List<ReponseCandidat> findByQuestion(Question question) {
		List<ReponseCandidat> result = reponseCandidatService.findByQuestion(question, participation);
		result.forEach(res -> {
			if (res.isEtat()) {
				singleReponse = res;
			}

		});
		return result;
	}

	public void repondreSingleChoice() {
		try {
			reponseCandidatService.updateSingleResponse(singleReponse);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void checkReponse(ReponseCandidat reponseCandidat) {
		reponseCandidat.setEtat(!reponseCandidat.isEtat());
		reponseCandidatService.updateMultiResponse(reponseCandidat);

	}

	public LocalDateTime convertToLocalDateViaInstant(Date dateToConvert) {
		return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
	}

	public Examen getExamen() {
		return examen;
	}

	public void setExamen(Examen examen) {
		this.examen = examen;
	}

	public LocalDateTime getCounter() {
		return counter;
	}

	public void setCounter(LocalDateTime counter) {
		this.counter = counter;
	}

	public long getDiffSeconde() {
		return diffSeconde;
	}

	public void setDiffSeconde(long diffSeconde) {
		this.diffSeconde = diffSeconde;
	}

	public List<Theme> getThemes() {
		return themes;
	}

	public void setThemes(List<Theme> themes) {
		this.themes = themes;
	}

	public ReponseCandidat getSingleReponse() {
		return singleReponse;
	}

	public void setSingleReponse(ReponseCandidat singleReponse) {
		this.singleReponse = singleReponse;
	}

	public List<ReponseCandidat> getMultiReponses() {
		return multiReponses;
	}

	public void setMultiReponses(List<ReponseCandidat> multiReponses) {
		this.multiReponses = multiReponses;
	}

	public Participation getParticipation() {
		return participation;
	}

	public void setParticipation(Participation participation) {
		this.participation = participation;
	}

}
