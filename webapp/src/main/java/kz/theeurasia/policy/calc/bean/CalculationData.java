package kz.theeurasia.policy.calc.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import kz.theeurasia.policy.domain.InsuredDriverData;
import kz.theeurasia.policy.domain.InsuredVehicleData;
import kz.theeurasia.policy.domain.PolicyTermClass;

@Named("calculationData")
@ViewScoped
public class CalculationData implements Serializable {

    private static final long serialVersionUID = 1L;

    private final UUID id = UUID.randomUUID();

    private List<InsuredDriverData> insuredDrivers = new ArrayList<>();
    private List<InsuredVehicleData> insuredVehicles = new ArrayList<>();
    private PolicyTermClass termClass = PolicyTermClass.YEAR;
    private double calculatedPremiumCost;
    private boolean applyDiscount;

    @Override
    public int hashCode() {
	return this.getClass().hashCode() * id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
	return obj != null && this.getClass().isInstance(obj) && getId().equals((this.getClass().cast(obj)).getId());
    }

    @PostConstruct
    public void init() {

    }

    public String getSafeId() {
	return id.toString().replaceAll("-", "_");
    }

    // GENERATED

    public UUID getId() {
	return id;
    }

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

    public boolean isApplyDiscount() {
	return applyDiscount;
    }

    public void setApplyDiscount(boolean applyDiscount) {
	this.applyDiscount = applyDiscount;
    }
}
