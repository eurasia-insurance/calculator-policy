package kz.theeurasia.policy.calc.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import kz.theeurasia.policy.domain.CalculationData;
import kz.theeurasia.policy.domain.InsuredDriverData;
import kz.theeurasia.policy.domain.InsuredVehicleData;

@Named("policy")
@ViewScoped
public class Calculation implements Serializable {

    private static final long serialVersionUID = -910218412636084500L;

    private List<InsuredDriverData> insuredDrivers = new ArrayList<>();
    private List<InsuredVehicleData> insuredVehicles = new ArrayList<>();
    private CalculationData calculation = new CalculationData();

    @Inject
    // @ProjectStageDepend(stage = ProjectStage.Development)
    // @ManyDrivers
    // @ManyVehicles
    private DefaultCalculationDataBuilder dataBuilder;

    @PostConstruct
    public void init() {
	dataBuilder.buildDefaultData(this);
    }

    // GENERATED

    public List<InsuredDriverData> getInsuredDrivers() {
	return insuredDrivers;
    }

    public void setInsuredDrivers(List<InsuredDriverData> insuredDrivers) {
	this.insuredDrivers = insuredDrivers;
    }

    public List<InsuredVehicleData> getInsuredVehicles() {
	return insuredVehicles;
    }

    public void setInsuredVehicles(List<InsuredVehicleData> insuredVehicles) {
	this.insuredVehicles = insuredVehicles;
    }

    public CalculationData getCalculation() {
	return calculation;
    }
}
