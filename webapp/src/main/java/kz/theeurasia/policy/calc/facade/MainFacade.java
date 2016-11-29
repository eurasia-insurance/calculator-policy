package kz.theeurasia.policy.calc.facade;

import java.util.ResourceBundle;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.lapsa.insurance.domain.policy.PolicyDriver;
import com.lapsa.insurance.domain.policy.PolicyVehicle;

import kz.theeurasia.policy.calc.bean.Calculation;

@Named("frontController")
@ApplicationScoped
public class MainFacade {

    private ResourceBundle gpovts;

    @Inject
    private DriverFacade driverFacade;

    @Inject
    private VehicleFacade vehicleFacade;

    @Inject
    private CalculationFacade calculationFacade;

    @Inject
    private Calculation data;

    public void addInsuredDriver() {
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
	calculationFacade.calculatePremiumCost(data);
    }

    public void onDriverIdNumberChanged(PolicyDriver insuredDriver) {
	try {
	    driverFacade.fetchInfo(data, insuredDriver);
	} catch (ValidationException e) {
	}
	calculationFacade.calculatePremiumCost(data);
    }

    public void onPolicyCostCalculationFormChanged() {
	calculationFacade.calculatePremiumCost(data);
    }

    public void onVehicleVinCodeChanged(PolicyVehicle insuredVehicle) {
	try {
	    vehicleFacade.fetchInfo(data, insuredVehicle);
	} catch (ValidationException e) {
	}
	calculationFacade.calculatePremiumCost(data);
    }

    public void onVehicleRegionChanged(PolicyVehicle insuredVehicle) {
	vehicleFacade.handleAreaChanged(insuredVehicle);
	vehicleFacade.evaluateMajorCity(insuredVehicle);
	calculationFacade.calculatePremiumCost(data);
    }

    public void onVehicleCityChanged(PolicyVehicle vehicle) {
	vehicleFacade.handleCityChanged(vehicle);
	vehicleFacade.evaluateMajorCity(vehicle);
	calculationFacade.calculatePremiumCost(data);
    }
}
