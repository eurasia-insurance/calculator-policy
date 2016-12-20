package kz.theeurasia.policy.calc.bean.facade;

import java.util.ResourceBundle;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.lapsa.insurance.domain.policy.Policy;
import com.lapsa.insurance.domain.policy.PolicyDriver;
import com.lapsa.insurance.domain.policy.PolicyVehicle;

import kz.theeurasia.policy.calc.api.ActionFacade;
import kz.theeurasia.policy.calc.api.CalculationFacade;
import kz.theeurasia.policy.calc.api.DefaultCalculationDataBuilder;
import kz.theeurasia.policy.calc.api.DriverFacade;
import kz.theeurasia.policy.calc.api.PolicyHolder;
import kz.theeurasia.policy.calc.api.ValidationException;
import kz.theeurasia.policy.calc.api.VehicleFacade;

@Named("actionFacade")
@ApplicationScoped
public class DefaultActionFacade implements ActionFacade {

    private ResourceBundle gpovts;

    @Inject
    private DriverFacade driverFacade;

    @Inject
    private VehicleFacade vehicleFacade;

    @Inject
    private CalculationFacade calculationFacade;

    @Inject
    private PolicyHolder policyHolder;

    @Inject
    // @ProjectStageDepend(stage = ProjectStage.Development)
    // @ManyDrivers
    // @ManyVehicles
    private DefaultCalculationDataBuilder dataBuilder;

    @Override
    public String doInitialize() {
	policyHolder.setValue(new Policy());
	dataBuilder.buildDefaultData(policyHolder.getValue());
	return null;
    }

    public void addInsuredDriver() {
	Policy data = policyHolder.getValue();
	try {
	    driverFacade.add(data);
	} catch (ValidationException e) {
	    FacesContext.getCurrentInstance().addMessage(null,
		    new FacesMessage(FacesMessage.SEVERITY_WARN,
			    gpovts.getString(e.getMessageCode().getMessageBundleCode()),
			    gpovts.getString(e.getDescriptionCode().getMessageBundleCode())));
	}
	calculationFacade.calculatePremiumCost(data);
    }

    public void removeInsuredDriver(PolicyDriver driver) {
	Policy data = policyHolder.getValue();
	try {
	    driverFacade.remove(data, driver);
	} catch (ValidationException e) {
	    FacesContext.getCurrentInstance().addMessage(null,
		    new FacesMessage(FacesMessage.SEVERITY_WARN,
			    gpovts.getString(e.getMessageCode().getMessageBundleCode()),
			    gpovts.getString(e.getDescriptionCode().getMessageBundleCode())));
	}
	calculationFacade.calculatePremiumCost(data);
    }

    public void addInsuredVehicle() {
	Policy data = policyHolder.getValue();
	try {
	    vehicleFacade.add(data);
	} catch (ValidationException e) {
	    FacesContext.getCurrentInstance().addMessage(null,
		    new FacesMessage(FacesMessage.SEVERITY_WARN,
			    gpovts.getString(e.getMessageCode().getMessageBundleCode()),
			    gpovts.getString(e.getDescriptionCode().getMessageBundleCode())));
	}
	calculationFacade.calculatePremiumCost(data);
    }

    public void removeInsuredVehicle(PolicyVehicle insuredVehicle) {
	Policy data = policyHolder.getValue();
	try {
	    vehicleFacade.remove(data, insuredVehicle);
	} catch (ValidationException e) {
	    FacesContext.getCurrentInstance().addMessage(null,
		    new FacesMessage(FacesMessage.SEVERITY_WARN,
			    gpovts.getString(e.getMessageCode().getMessageBundleCode()),
			    gpovts.getString(e.getDescriptionCode().getMessageBundleCode())));
	}
	calculationFacade.calculatePremiumCost(data);
    }

    public void doCalculatePolicyCost() {
	Policy data = policyHolder.getValue();
	calculationFacade.calculatePremiumCost(data);
    }

    public void onDriverIdNumberChanged(PolicyDriver insuredDriver) {
	Policy data = policyHolder.getValue();
	try {
	    driverFacade.fetchInfo(data, insuredDriver);
	} catch (ValidationException e) {
	}
	calculationFacade.calculatePremiumCost(data);
    }

    public void onPolicyCostCalculationFormChanged() {
	Policy data = policyHolder.getValue();
	calculationFacade.calculatePremiumCost(data);
    }

    public void onVehicleVinCodeChanged(PolicyVehicle insuredVehicle) {
	Policy data = policyHolder.getValue();
	try {
	    vehicleFacade.fetchInfo(data, insuredVehicle);
	} catch (ValidationException e) {
	}
	calculationFacade.calculatePremiumCost(data);
    }

    public void onVehicleRegionChanged(PolicyVehicle insuredVehicle) {
	Policy data = policyHolder.getValue();
	vehicleFacade.handleAreaChanged(insuredVehicle);
	vehicleFacade.evaluateMajorCity(insuredVehicle);
	calculationFacade.calculatePremiumCost(data);
    }

    public void onVehicleCityChanged(PolicyVehicle vehicle) {
	Policy data = policyHolder.getValue();
	vehicleFacade.handleCityChanged(vehicle);
	vehicleFacade.evaluateMajorCity(vehicle);
	calculationFacade.calculatePremiumCost(data);
    }
}
