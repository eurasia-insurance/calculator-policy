package kz.theeurasia.policy.calc.facade;

import java.util.ResourceBundle;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import kz.theeurasia.policy.domain.InsuredDriverData;
import kz.theeurasia.policy.domain.InsuredVehicleData;

@Named("calculationController")
@ApplicationScoped
public class MainFacade {

    private ResourceBundle gpovts;

    @Inject
    private DriverFacade driverFacade;

    @Inject
    private VehicleFacade vehicleFacade;

    @Inject
    private CalculationFacade calculationFacade;

    public void addInsuredDriver() {
	try {
	    driverFacade.add();
	} catch (ValidationException e) {
	    FacesContext.getCurrentInstance().addMessage(null,
		    new FacesMessage(FacesMessage.SEVERITY_WARN,
			    gpovts.getString(e.getMessageCode().getMessageBundleCode()),
			    gpovts.getString(e.getDescriptionCode().getMessageBundleCode())));
	}
	calculationFacade.calculatePremiumCost();
    }

    public void removeInsuredDriver(InsuredDriverData driver) {
	try {
	    driverFacade.remove(driver);
	} catch (ValidationException e) {
	    FacesContext.getCurrentInstance().addMessage(null,
		    new FacesMessage(FacesMessage.SEVERITY_WARN,
			    gpovts.getString(e.getMessageCode().getMessageBundleCode()),
			    gpovts.getString(e.getDescriptionCode().getMessageBundleCode())));
	}
	calculationFacade.calculatePremiumCost();
    }

    public void addInsuredVehicle() {
	try {
	    vehicleFacade.add();
	} catch (ValidationException e) {
	    FacesContext.getCurrentInstance().addMessage(null,
		    new FacesMessage(FacesMessage.SEVERITY_WARN,
			    gpovts.getString(e.getMessageCode().getMessageBundleCode()),
			    gpovts.getString(e.getDescriptionCode().getMessageBundleCode())));
	}
	calculationFacade.calculatePremiumCost();
    }

    public void removeInsuredVehicle(InsuredVehicleData insuredVehicle) {
	try {
	    vehicleFacade.remove(insuredVehicle);
	} catch (ValidationException e) {
	    FacesContext.getCurrentInstance().addMessage(null,
		    new FacesMessage(FacesMessage.SEVERITY_WARN,
			    gpovts.getString(e.getMessageCode().getMessageBundleCode()),
			    gpovts.getString(e.getDescriptionCode().getMessageBundleCode())));
	}
	calculationFacade.calculatePremiumCost();
    }

    public void doCalculatePolicyCost() {
	calculationFacade.calculatePremiumCost();
    }

    public void onDriverIdNumberChanged(InsuredDriverData insuredDriver) {
	try {
	    driverFacade.fetchInfo(insuredDriver);
	} catch (ValidationException e) {
	}
	calculationFacade.calculatePremiumCost();
    }

    public void onPolicyCostCalculationFormChanged() {
	calculationFacade.calculatePremiumCost();
    }

    public void onVehicleVinCodeChanged(InsuredVehicleData insuredVehicle) {
	try {
	    vehicleFacade.fetchInfo(insuredVehicle);
	} catch (ValidationException e) {
	}
	calculationFacade.calculatePremiumCost();
    }

    public void onVehicleRegionChanged(InsuredVehicleData insuredVehicle) {
	vehicleFacade.handleAreaChanged(insuredVehicle);
	vehicleFacade.evaluateMajorCity(insuredVehicle);
	calculationFacade.calculatePremiumCost();
    }

    public void onVehicleCityChanged(InsuredVehicleData vehicle) {
	vehicleFacade.handleCityChanged(vehicle);
	vehicleFacade.evaluateMajorCity(vehicle);
	calculationFacade.calculatePremiumCost();
    }
}
