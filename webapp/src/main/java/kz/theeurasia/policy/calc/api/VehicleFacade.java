package kz.theeurasia.policy.calc.api;

import java.io.Serializable;

import com.lapsa.insurance.domain.policy.Policy;
import com.lapsa.insurance.domain.policy.PolicyVehicle;

public interface VehicleFacade extends Serializable {

    PolicyVehicle add(Policy policy) throws ValidationException;

    void remove(Policy policy, PolicyVehicle vehicle) throws ValidationException;

    void handleAreaChanged(PolicyVehicle insuredVehicle);

    void evaluateMajorCity(PolicyVehicle insuredVehicle);

    void handleCityChanged(PolicyVehicle vehicle);

    void handleTemporaryEntryChanged(PolicyVehicle vehicle);

}
