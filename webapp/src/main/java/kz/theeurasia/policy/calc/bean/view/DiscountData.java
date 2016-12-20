package kz.theeurasia.policy.calc.bean.view;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Named
@ViewScoped
public class DiscountData implements Serializable {

    private static final long serialVersionUID = -3073071391117763140L;

    private double discountValue = 0.25d;

    public void setDiscountValue(double discountValue) {
	this.discountValue = discountValue;
    }

    public double getDiscountValue() {
	return discountValue;
    }

    @Min(value = 0, message = "{validation.LotherThanZero.message}")
    @Max(value = 100, message = "{validation.GreaterThanHundred.message}")
    public double getDiscountPercentValue() {
	return discountValue * 100;
    }

    public void setDiscountPercentValue(double discountPercentValue) {
	this.discountValue = discountPercentValue / 100;
    }

}
