package kz.theeurasia.policy.calc.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.lapsa.insurance.domain.CalculationData;
import com.lapsa.insurance.domain.policy.PolicyDriver;
import com.lapsa.insurance.domain.policy.PolicyVehicle;

@Named("policy")
@ViewScoped
public class Calculation implements Serializable {

    private static final long serialVersionUID = -910218412636084500L;

    private List<PolicyDriver> insuredDrivers = new ArrayList<>();
    private List<PolicyVehicle> insuredVehicles = new ArrayList<>();
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

    public List<PolicyDriver> getInsuredDrivers() {
	return insuredDrivers;
    }

    public void setInsuredDrivers(List<PolicyDriver> insuredDrivers) {
	this.insuredDrivers = insuredDrivers;
    }

    public List<PolicyVehicle> getInsuredVehicles() {
	return insuredVehicles;
    }

    public void setInsuredVehicles(List<PolicyVehicle> insuredVehicles) {
	this.insuredVehicles = insuredVehicles;
    }

    public CalculationData getCalculation() {
	return calculation;
    }
}
