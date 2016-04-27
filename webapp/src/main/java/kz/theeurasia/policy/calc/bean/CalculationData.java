package kz.theeurasia.policy.calc.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import kz.theeurasia.policy.domain.InsuredDriverData;
import kz.theeurasia.policy.domain.InsuredVehicleData;
import kz.theeurasia.policy.domain.PolicyTermClass;

@Named("policy")
@ViewScoped
public class CalculationData implements Serializable {

    private static final long serialVersionUID = -910218412636084500L;

    private List<InsuredDriverData> insuredDrivers = new ArrayList<>();
    private List<InsuredVehicleData> insuredVehicles = new ArrayList<>();
    private PolicyTermClass termClass = PolicyTermClass.YEAR;
    private double calculatedPremiumCost;

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

    public PolicyTermClass getTermClass() {
	return termClass;
    }

    public void setTermClass(PolicyTermClass termClass) {
	this.termClass = termClass;
    }

    public double getCalculatedPremiumCost() {
	return calculatedPremiumCost;
    }

    public void setCalculatedPremiumCost(double calculatedPremiumCost) {
	this.calculatedPremiumCost = calculatedPremiumCost;
    }
}
