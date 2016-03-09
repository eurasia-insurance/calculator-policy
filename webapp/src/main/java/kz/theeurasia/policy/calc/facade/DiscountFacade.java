package kz.theeurasia.policy.calc.facade;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import kz.theeurasia.policy.calc.bean.CalculationData;
import kz.theeurasia.policy.calc.bean.DiscountData;

@Named
@ApplicationScoped
public class DiscountFacade {

    @Inject
    private DiscountData discountData;

    public void applyDiscount(CalculationData data) {
	double premium = data.getCalculatedPremiumCost();
	premium *= (1 - discountData.getDiscountValue());
	data.setCalculatedPremiumCost(premium);
    }
}
