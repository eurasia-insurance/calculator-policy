package kz.theeurasia.policy.calc.api;

import java.io.Serializable;

import com.lapsa.insurance.domain.policy.Policy;

public interface CalculationFacade extends Serializable {

    void calculatePremiumCost(Policy policy);

}
