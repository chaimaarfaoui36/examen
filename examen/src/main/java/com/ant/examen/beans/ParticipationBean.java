package com.ant.examen.beans;

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
import com.ant.examen.entities.Reponse;
import com.ant.examen.entities.Theme;
import com.ant.examen.services.CandidatService;
import com.ant.examen.services.ExamenService;
import com.ant.examen.services.ParticipationService;
import com.ant.examen.services.QuestionService;
import com.ant.examen.services.ReponseService;
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
	private ReponseService reponseService = new ReponseService();
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
				Date dateFin = cal.getTime()  ;
				LocalDateTime debut = convertToLocalDateViaInstant(participation.getDateParticipation());
				LocalDateTime fin = convertToLocalDateViaInstant(dateFin);
			
				
				diffSeconde = ChronoUnit.SECONDS.between(LocalDateTime.now(), fin);
				
				if(diffSeconde<=0) {
					diffSeconde = 10;
				}
				
				
			
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void timerCompleted() {
		System.out.println("************ finished");
	}
	public List<Question> findQuestionByTheme(Theme theme) {
		return questionService.findByExamenAndTheme(examen ,theme);
	}
	public List<Reponse> findByQuestion(Question question) {
		return reponseService.findByQuestion(question);
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
	

}
