package kz.theeurasia.policy.calc.facade;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.lapsa.insurance.domain.services.CalculationService;

import kz.theeurasia.policy.calc.bean.Calculation;

@Named
@ApplicationScoped
public class CalculationFacade implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private CalculationService calculationService;

    public void calculatePremiumCost(Calculation data) {
	double cost = calculationService.calculatePremiumCost(data.getInsuredDrivers(), data.getInsuredVehicles(),
		data.getCalculation().getTermClass());
	data.getCalculation().setPremiumCost(cost);
    }
}
