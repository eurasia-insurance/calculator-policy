package kz.theeurasia.policy.calc.bean.facade;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import com.lapsa.insurance.domain.CalculationData;
import com.lapsa.insurance.domain.policy.Policy;

import kz.theeurasia.policy.calc.api.CalculationFacade;
import tech.lapsa.insurance.calculation.CalculationFailed;
import tech.lapsa.insurance.calculation.PolicyCalculation.PolicyCalculationRemote;

@Named
@RequestScoped
public class DefaultCalculationFacade implements CalculationFacade {

    private static final long serialVersionUID = 1L;

    @EJB
    private PolicyCalculationRemote calculationService;

    @Override
    public void calculatePremiumCost(Policy policy) {
	try {
	    final CalculationData cd = calculationService.calculateAmount(policy);
	    policy.setCalculation(cd);
	} catch (CalculationFailed e) {
	    policy.getCalculation().setAmount(0d);
	}
    }
}
