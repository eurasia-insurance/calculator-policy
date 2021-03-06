package kz.theeurasia.policy.calc.bean.facade;

import java.time.LocalDate;
import java.util.ResourceBundle;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.lapsa.insurance.domain.InsurancePeriodData;
import com.lapsa.insurance.domain.policy.Policy;
import com.lapsa.insurance.domain.policy.PolicyDriver;
import com.lapsa.insurance.domain.policy.PolicyVehicle;

import kz.theeurasia.policy.calc.api.ActionFacade;
import kz.theeurasia.policy.calc.api.CalculationFacade;
import kz.theeurasia.policy.calc.api.DefaultCalculationDataBuilder;
import kz.theeurasia.policy.calc.api.DriverFacade;
import kz.theeurasia.policy.calc.api.MessagesBundleCode;
import kz.theeurasia.policy.calc.api.PolicyHolder;
import kz.theeurasia.policy.calc.api.ValidationException;
import kz.theeurasia.policy.calc.api.VehicleFacade;

@Named("actionFacade")
@RequestScoped
public class DefaultActionFacade implements ActionFacade {

    private ResourceBundle messages = ResourceBundle.getBundle(MessagesBundleCode.BUNDLE_BASE_NAME);

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
	doPeriodYear();
	return null;
    }

    @Override
    public String doPeriodYear() {
	InsurancePeriodData period = new InsurancePeriodData();
	period.setFrom(LocalDate.now());
	period.setTo(LocalDate.now().plusYears(1).minusDays(1));

	Policy policy = policyHolder.getValue();
	policy.setPeriod(period);
	calculationFacade.calculatePremiumCost(policy);
	return null;
    }

    @Override
    public String doPeriodMonth(int monthsCount) {
	InsurancePeriodData period = new InsurancePeriodData();
	period.setFrom(LocalDate.now());
	period.setTo(LocalDate.now().plusMonths(monthsCount));

	Policy policy = policyHolder.getValue();
	policy.setPeriod(period);
	calculationFacade.calculatePremiumCost(policy);
	return null;
    }

    @Override
    public void addInsuredDriver() {
	Policy data = policyHolder.getValue();
	try {
	    driverFacade.add(data);
	} catch (ValidationException e) {
	    FacesContext.getCurrentInstance().addMessage(null,
		    new FacesMessage(FacesMessage.SEVERITY_WARN,
			    messages.getString(e.getMessageCode().getMessageBundleCode()),
			    messages.getString(e.getDescriptionCode().getMessageBundleCode())));
	}
	calculationFacade.calculatePremiumCost(data);
    }

    @Override
    public void removeInsuredDriver(PolicyDriver driver) {
	Policy data = policyHolder.getValue();
	try {
	    driverFacade.remove(data, driver);
	} catch (ValidationException e) {
	    FacesContext.getCurrentInstance().addMessage(null,
		    new FacesMessage(FacesMessage.SEVERITY_WARN,
			    messages.getString(e.getMessageCode().getMessageBundleCode()),
			    messages.getString(e.getDescriptionCode().getMessageBundleCode())));
	}
	calculationFacade.calculatePremiumCost(data);
    }

    @Override
    public void addInsuredVehicle() {
	Policy data = policyHolder.getValue();
	try {
	    vehicleFacade.add(data);
	} catch (ValidationException e) {
	    FacesContext.getCurrentInstance().addMessage(null,
		    new FacesMessage(FacesMessage.SEVERITY_WARN,
			    messages.getString(e.getMessageCode().getMessageBundleCode()),
			    messages.getString(e.getDescriptionCode().getMessageBundleCode())));
	}
	calculationFacade.calculatePremiumCost(data);
    }

    @Override
    public void removeInsuredVehicle(PolicyVehicle insuredVehicle) {
	Policy data = policyHolder.getValue();
	try {
	    vehicleFacade.remove(data, insuredVehicle);
	} catch (ValidationException e) {
	    FacesContext.getCurrentInstance().addMessage(null,
		    new FacesMessage(FacesMessage.SEVERITY_WARN,
			    messages.getString(e.getMessageCode().getMessageBundleCode()),
			    messages.getString(e.getDescriptionCode().getMessageBundleCode())));
	}
	calculationFacade.calculatePremiumCost(data);
    }

    @Override
    public void doCalculatePolicyCost() {
	Policy data = policyHolder.getValue();
	calculationFacade.calculatePremiumCost(data);
    }

    @Override
    public void onDriverIdNumberChanged(PolicyDriver insuredDriver) {
	Policy data = policyHolder.getValue();
	try {
	    driverFacade.fetchInfo(data, insuredDriver);
	} catch (ValidationException e) {
	}
	calculationFacade.calculatePremiumCost(data);
    }

    @Override
    public void onPolicyCostCalculationFormChanged() {
	Policy data = policyHolder.getValue();
	calculationFacade.calculatePremiumCost(data);
    }

    @Override
    public void onVehicleTemporaryEntryChanged(PolicyVehicle vehicle) {
	Policy policy = policyHolder.getValue();
	vehicleFacade.handleTemporaryEntryChanged(vehicle);
	calculationFacade.calculatePremiumCost(policy);
    }

    @Override
    public void onVehicleRegionChanged(PolicyVehicle insuredVehicle) {
	Policy data = policyHolder.getValue();
	vehicleFacade.handleAreaChanged(insuredVehicle);
	vehicleFacade.evaluateMajorCity(insuredVehicle);
	calculationFacade.calculatePremiumCost(data);
    }

    @Override
    public void onVehicleCityChanged(PolicyVehicle vehicle) {
	Policy data = policyHolder.getValue();
	vehicleFacade.handleCityChanged(vehicle);
	vehicleFacade.evaluateMajorCity(vehicle);
	calculationFacade.calculatePremiumCost(data);
    }
}
