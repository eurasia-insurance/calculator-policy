package kz.theeurasia.policy.calc.bean.facade;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.lapsa.insurance.domain.policy.Policy;
import com.lapsa.insurance.services.other.CalculationFailed;
import com.lapsa.insurance.services.other.PolicyCalculationService;

import kz.theeurasia.policy.calc.api.CalculationFacade;

@Named
@ApplicationScoped
public class DefaultCalculationFacade implements CalculationFacade {

    private static final long serialVersionUID = 1L;

    @Inject
    private Logger logger;

    @Inject
    private PolicyCalculationService calculationService;

    @Override
    public void calculatePremiumCost(Policy policy) {
	try {
	    calculationService.calculatePolicyCost(policy);
	} catch (CalculationFailed e) {
	    logger.log(Level.FINER, "Calculation failed", e);
	    policy.getCalculation().setCalculatedPremiumCost(0);
	}
    }
}
