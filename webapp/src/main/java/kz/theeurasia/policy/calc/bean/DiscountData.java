package kz.theeurasia.policy.calc.bean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named
@ApplicationScoped
public class DiscountData {
    private final double DISCOUNT_VALUE = 0.25d;

    public double getDiscountValue() {
	return DISCOUNT_VALUE;
    }
}
