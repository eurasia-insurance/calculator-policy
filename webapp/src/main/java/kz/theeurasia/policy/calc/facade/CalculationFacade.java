package kz.theeurasia.policy.calc.facade;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import kz.theeurasia.policy.calc.bean.CalculationData;
import kz.theeurasia.policy.services.CalculationService;

@Named
@ApplicationScoped
public class CalculationFacade implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private CalculationService calculationService;

    @Inject
    private DiscountFacade discountFacade;

    public void calculatePremiumCost(CalculationData data) {
	double cost = calculationService.calculatePremiumCost(data.getInsuredDrivers(), data.getInsuredVehicles(),
		data.getTermClass());
	data.setCalculatedPremiumCost(cost);
	if (data.isApplyDiscount())
	    discountFacade.applyDiscount(data);
    }

}
