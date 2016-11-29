package kz.theeurasia.policy.calc.facade;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.lapsa.insurance.domain.CalculationData;
import com.lapsa.insurance.domain.policy.Policy;
import com.lapsa.insurance.services.other.PolicyCalculationService;

import kz.theeurasia.policy.calc.bean.Calculation;

@Named
@ApplicationScoped
public class CalculationFacade implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private PolicyCalculationService calculationService;

    public void calculatePremiumCost(Calculation data) {
	Policy policy = new Policy();
	policy.setCalculation(new CalculationData());
	policy.setInsuredDrivers(data.getInsuredDrivers());
	policy.setInsuredVehicles(data.getInsuredVehicles());

	calculationService.calculatePolicyCost(policy);
	data.getCalculation().setPremiumCost(policy.getCalculation().getPremiumCost());
    }
}
