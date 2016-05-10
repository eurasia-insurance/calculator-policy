package kz.theeurasia.policy.calc.facade;

import java.util.ResourceBundle;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.lapsa.insurance.domain.InsuredDriverData;
import com.lapsa.insurance.domain.InsuredVehicleData;

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

    public void removeInsuredDriver(InsuredDriverData driver) {
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

    public void removeInsuredVehicle(InsuredVehicleData insuredVehicle) {
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

    public void onDriverIdNumberChanged(InsuredDriverData insuredDriver) {
	try {
	    driverFacade.fetchInfo(data, insuredDriver);
	} catch (ValidationException e) {
	}
	calculationFacade.calculatePremiumCost(data);
    }

    public void onPolicyCostCalculationFormChanged() {
	calculationFacade.calculatePremiumCost(data);
    }

    public void onVehicleVinCodeChanged(InsuredVehicleData insuredVehicle) {
	try {
	    vehicleFacade.fetchInfo(data, insuredVehicle);
	} catch (ValidationException e) {
	}
	calculationFacade.calculatePremiumCost(data);
    }

    public void onVehicleRegionChanged(InsuredVehicleData insuredVehicle) {
	vehicleFacade.handleAreaChanged(insuredVehicle);
	vehicleFacade.evaluateMajorCity(insuredVehicle);
	calculationFacade.calculatePremiumCost(data);
    }

    public void onVehicleCityChanged(InsuredVehicleData vehicle) {
	vehicleFacade.handleCityChanged(vehicle);
	vehicleFacade.evaluateMajorCity(vehicle);
	calculationFacade.calculatePremiumCost(data);
    }
}
