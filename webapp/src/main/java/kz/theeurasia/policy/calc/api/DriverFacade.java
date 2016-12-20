package kz.theeurasia.policy.calc.api;

import java.io.Serializable;

import com.lapsa.insurance.domain.policy.Policy;
import com.lapsa.insurance.domain.policy.PolicyDriver;

public interface DriverFacade extends Serializable {
    PolicyDriver add(Policy policy) throws ValidationException;

    void remove(Policy policy, PolicyDriver driver) throws ValidationException;

    void fetchInfo(Policy policy, PolicyDriver driver) throws ValidationException;
}
