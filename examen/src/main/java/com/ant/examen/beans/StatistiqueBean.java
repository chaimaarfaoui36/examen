package com.ant.examen.beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearTicks;
import org.primefaces.model.charts.bar.BarChartDataSet;
import org.primefaces.model.charts.bar.BarChartModel;
import org.primefaces.model.charts.bar.BarChartOptions;
import org.primefaces.model.charts.donut.DonutChartDataSet;
import org.primefaces.model.charts.donut.DonutChartModel;
import org.primefaces.model.charts.pie.PieChartDataSet;
import org.primefaces.model.charts.pie.PieChartModel;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ant.examen.entities.Candidat;
import com.ant.examen.entities.Entreprise;
import com.ant.examen.model.StatModel;
import com.ant.examen.services.CandidatService;
import com.ant.examen.services.EntrepriseService;
import com.ant.examen.services.ExamenService;
import com.ant.examen.services.InvitationService;
import com.ant.examen.services.ParticipationService;

@ManagedBean
@ViewScoped
public class StatistiqueBean {

	private CandidatService candidatService = new CandidatService();
	private EntrepriseService entrepriseService = new EntrepriseService();
	private ExamenService examenService = new ExamenService();
	private ParticipationService participationService = new ParticipationService();
	private InvitationService invitationService = new InvitationService();
	private PieChartModel pieModel;
	private PieChartModel pieModel2;
	private BarChartModel barModel;
	private BarChartModel barModel2;
	private DonutChartModel donutModel;
	private DonutChartModel donutModel2;

	@PostConstruct
	public void init() {

		createPieModel();
		createPieModel2();
		createBarModel();
		createBarModel2();
		createDonutModel();
		createDonutModel2();
	}

	public void createDonutModel() {
		donutModel = new DonutChartModel();
		ChartData data = new ChartData();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Entreprise entreprise = entrepriseService.findByEmail(authentication.getName());

		DonutChartDataSet dataSet = new DonutChartDataSet();

		List<Number> values = participationService.findScore(entreprise);

		dataSet.setData(values);

		List<String> bgColors = new ArrayList<>();
		bgColors.add("rgb(255, 54, 51)");
		bgColors.add("rgb(255, 131, 51)");
		bgColors.add("rgb(255, 236, 51)");

		bgColors.add("rgb(178, 255, 51)");
		bgColors.add("rgb(110, 255, 51)");
		bgColors.add("rgb(3, 220, 111)");
		dataSet.setBackgroundColor(bgColors);

		data.addChartDataSet(dataSet);
		List<String> labels = new ArrayList<>();
		labels.add("Trés faible");
		labels.add("Faible");
		labels.add("Passable");
		labels.add("Bien");
		labels.add("Très Bien");
		labels.add("Excelent");
		data.setLabels(labels);

		donutModel.setData(data);
	}

	public void createDonutModel2() {
		donutModel2 = new DonutChartModel();
		ChartData data = new ChartData();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Candidat candidat = candidatService.findByEmail(authentication.getName());

		DonutChartDataSet dataSet = new DonutChartDataSet();

		List<Number> values = participationService.findScore(candidat);

		dataSet.setData(values);

		List<String> bgColors = new ArrayList<>();
		bgColors.add("rgb(255, 54, 51)");
		bgColors.add("rgb(255, 131, 51)");
		bgColors.add("rgb(255, 236, 51)");

		bgColors.add("rgb(178, 255, 51)");
		bgColors.add("rgb(110, 255, 51)");
		bgColors.add("rgb(3, 220, 111)");
		dataSet.setBackgroundColor(bgColors);

		data.addChartDataSet(dataSet);
		List<String> labels = new ArrayList<>();
		labels.add("Trés faible");
		labels.add("Faible");
		labels.add("Passable");
		labels.add("Bien");
		labels.add("Très Bien");
		labels.add("Excelent");
		data.setLabels(labels);

		donutModel2.setData(data);
	}

	public void createBarModel() {
		barModel = new BarChartModel();
		ChartData data = new ChartData();

		BarChartDataSet barDataSet = new BarChartDataSet();
		barDataSet.setLabel("Examen");
		barDataSet.setBackgroundColor("rgba(255, 99, 132, 0.2)");
		barDataSet.setBorderColor("rgb(255, 99, 132)");
		barDataSet.setBorderWidth(1);
		List<Number> values = new ArrayList<>();
		for (int i = 1; i <= 12; i++) {
			values.add(examenService.findByMonth(i).size());
		}
		barDataSet.setData(values);

		BarChartDataSet barDataSet2 = new BarChartDataSet();
		barDataSet2.setLabel("Partcipation");
		barDataSet2.setBackgroundColor("rgba(255, 159, 64, 0.2)");
		barDataSet2.setBorderColor("rgb(255, 159, 64)");
		barDataSet2.setBorderWidth(1);
		List<Number> values2 = new ArrayList<>();
		for (int i = 1; i <= 12; i++) {
			values2.add(participationService.findByMonth(i).size());
		}
		barDataSet2.setData(values2);

		data.addChartDataSet(barDataSet);
		data.addChartDataSet(barDataSet2);

		List<String> labels = new ArrayList<>();
		labels.add("Janvier");
		labels.add("Février");
		labels.add("Mars");
		labels.add("Avril");
		labels.add("Mai");
		labels.add("Juin");
		labels.add("Juillet");
		labels.add("Aout");
		labels.add("Septembre");
		labels.add("Octobre");
		labels.add("Nouvembre");
		labels.add("Décembre");
		data.setLabels(labels);
		barModel.setData(data);

		// Options
		BarChartOptions options = new BarChartOptions();
		CartesianScales cScales = new CartesianScales();
		CartesianLinearAxes linearAxes = new CartesianLinearAxes();
		CartesianLinearTicks ticks = new CartesianLinearTicks();
		ticks.setBeginAtZero(true);
		ticks.setStepSize(5);
		linearAxes.setTicks(ticks);
		cScales.addYAxesData(linearAxes);
		options.setScales(cScales);

		barModel.setOptions(options);
		//////////////////////////////////////

	}

	public void createBarModel2() {
		barModel2 = new BarChartModel();
		ChartData data = new ChartData();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Entreprise entreprise = entrepriseService.findByEmail(authentication.getName());

		BarChartDataSet barDataSet = new BarChartDataSet();
		barDataSet.setLabel("Examen");
		barDataSet.setBackgroundColor("rgba(255, 99, 132, 0.2)");
		barDataSet.setBorderColor("rgb(255, 99, 132)");
		barDataSet.setBorderWidth(1);
		List<Number> values = new ArrayList<>();
		for (int i = 1; i <= 12; i++) {
			values.add(examenService.findByMonth(i, entreprise).size());
		}
		barDataSet.setData(values);

		BarChartDataSet barDataSet2 = new BarChartDataSet();
		barDataSet2.setLabel("Partcipation");
		barDataSet2.setBackgroundColor("rgba(255, 159, 64, 0.2)");
		barDataSet2.setBorderColor("rgb(255, 159, 64)");
		barDataSet2.setBorderWidth(1);
		List<Number> values2 = new ArrayList<>();
		for (int i = 1; i <= 12; i++) {
			values2.add(participationService.findByMonth(i, entreprise).size());
		}
		barDataSet2.setData(values2);

		BarChartDataSet barDataSet3 = new BarChartDataSet();
		barDataSet3.setLabel("Invitation");
		barDataSet3.setBackgroundColor("rgba(201, 203, 207, 0.2)");
		barDataSet3.setBorderColor("rgb(201, 203, 207)");
		barDataSet3.setBorderWidth(1);
		List<Number> values3 = new ArrayList<>();
		for (int i = 1; i <= 12; i++) {
			values3.add(invitationService.findByMonth(i, entreprise).size());
		}
		barDataSet3.setData(values3);

		data.addChartDataSet(barDataSet);
		data.addChartDataSet(barDataSet2);
		data.addChartDataSet(barDataSet3);
		List<String> labels = new ArrayList<>();
		labels.add("Janvier");
		labels.add("Février");
		labels.add("Mars");
		labels.add("Avril");
		labels.add("Mai");
		labels.add("Juin");
		labels.add("Juillet");
		labels.add("Aout");
		labels.add("Septembre");
		labels.add("Octobre");
		labels.add("Nouvembre");
		labels.add("Décembre");
		data.setLabels(labels);
		barModel2.setData(data);

		// Options
		BarChartOptions options = new BarChartOptions();
		CartesianScales cScales = new CartesianScales();
		CartesianLinearAxes linearAxes = new CartesianLinearAxes();
		CartesianLinearTicks ticks = new CartesianLinearTicks();
		ticks.setBeginAtZero(true);
		ticks.setStepSize(5);
		linearAxes.setTicks(ticks);
		cScales.addYAxesData(linearAxes);
		options.setScales(cScales);

		barModel2.setOptions(options);
		//////////////////////////////////////

	}

	private void createPieModel() {
		pieModel = new PieChartModel();
		ChartData data = new ChartData();

		PieChartDataSet dataSet = new PieChartDataSet();
		List<Number> values = new ArrayList<>();
		values.add(entrepriseService.findAll().size());
		values.add(candidatService.findAll().size());

		dataSet.setData(values);

		List<String> bgColors = new ArrayList<>();
		bgColors.add("rgb(255, 99, 132)");
		bgColors.add("rgb(54, 162, 235)");
	
		dataSet.setBackgroundColor(bgColors);

		data.addChartDataSet(dataSet);
		List<String> labels = new ArrayList<>();
		labels.add("Entreprise");
		labels.add("Candidat");

		data.setLabels(labels);

		pieModel.setData(data);
	}

	private void createPieModel2() {
		pieModel2 = new PieChartModel();
		ChartData data = new ChartData();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Entreprise entreprise = entrepriseService.findByEmail(authentication.getName());

		PieChartDataSet dataSet = new PieChartDataSet();
		StatModel result = examenService.findThemeExamen(entreprise);
		List<Number> values = result.getValues();

		dataSet.setData(values);

	
		List<String> bgColors = new ArrayList<>();
		bgColors.add("#4bb2c5");
		bgColors.add("#EAA228");
		bgColors.add("#c5b47f");
		bgColors.add( "#579575");
//		 , "#839557",
//			 "#958c12", "#953579", "#4b5de4", "#d8b83f",
//			 "#ff5800", "#0085cc", "#c747a3", "#cddf54",
//			 "#FBD178", "#26B4E3", "#bd70c7"};

		dataSet.setBackgroundColor(bgColors);

		data.addChartDataSet(dataSet);
		List<String> labels = result.getLabels();

		data.setLabels(labels);

		pieModel2.setData(data);
	}

	public PieChartModel getPieModel() {
		return pieModel;
	}

	public void setPieModel(PieChartModel pieModel) {
		this.pieModel = pieModel;
	}

	public BarChartModel getBarModel() {
		return barModel;
	}

	public void setBarModel(BarChartModel barModel) {
		this.barModel = barModel;
	}

	public BarChartModel getBarModel2() {
		return barModel2;
	}

	public void setBarModel2(BarChartModel barModel2) {
		this.barModel2 = barModel2;
	}

	public DonutChartModel getDonutModel() {
		return donutModel;
	}

	public void setDonutModel(DonutChartModel donutModel) {
		this.donutModel = donutModel;
	}

	public DonutChartModel getDonutModel2() {
		return donutModel2;
	}

	public void setDonutModel2(DonutChartModel donutModel2) {
		this.donutModel2 = donutModel2;
	}

	public PieChartModel getPieModel2() {
		return pieModel2;
	}

	public void setPieModel2(PieChartModel pieModel2) {
		this.pieModel2 = pieModel2;
	}
	
	

}
