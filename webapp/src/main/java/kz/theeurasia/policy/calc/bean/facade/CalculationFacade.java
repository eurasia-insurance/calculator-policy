package kz.theeurasia.policy.calc.facade;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.lapsa.insurance.domain.policy.Policy;
import com.lapsa.insurance.services.other.PolicyCalculationService;

@Named
@ApplicationScoped
public class CalculationFacade implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private PolicyCalculationService calculationService;

    public void calculatePremiumCost(Policy policy) {
	calculationService.calculatePolicyCost(policy);
    }
}
