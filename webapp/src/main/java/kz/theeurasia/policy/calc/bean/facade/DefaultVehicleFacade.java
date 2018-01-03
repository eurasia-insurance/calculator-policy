package kz.theeurasia.policy.calc.bean.facade;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import com.lapsa.insurance.domain.policy.Policy;
import com.lapsa.insurance.domain.policy.PolicyVehicle;
import com.lapsa.kz.country.KZArea;
import com.lapsa.kz.country.KZCity;

import kz.theeurasia.policy.calc.api.MessagesBundleCode;
import kz.theeurasia.policy.calc.api.ValidationException;
import kz.theeurasia.policy.calc.api.VehicleFacade;

@Named
@RequestScoped
public class DefaultVehicleFacade implements VehicleFacade {

    private static final long serialVersionUID = 1L;

    @Override
    public PolicyVehicle add(Policy policy) throws ValidationException {
	if (policy.getInsuredVehicles().size() > 0 && policy.getInsuredDrivers().size() > 1)
	    throw new ValidationException(MessagesBundleCode.ONLY_ONE_VEHICLE_ALLOWED);
	PolicyVehicle e = new PolicyVehicle();
	policy.getInsuredVehicles().add(e);
	_reset(policy, e);
	return e;
    }

    @Override
    public void remove(Policy policy, PolicyVehicle vehicle) throws ValidationException {
	if (policy.getInsuredVehicles().size() <= 1)
	    throw new ValidationException(MessagesBundleCode.VEHICLES_LIST_CANT_BE_EMPTY);
	policy.getInsuredVehicles().remove(vehicle);
    }

    public void evaluateMajorCity(PolicyVehicle insuredVehicle) {
	KZArea region = insuredVehicle.getArea();
	if (region == null)
	    return;
	if (region.equals(KZArea.GALM))
	    insuredVehicle.setCity(KZCity.ALM);
	if (region.equals(KZArea.GAST))
	    insuredVehicle.setCity(KZCity.AST);
    }

    private void _reset(Policy policy, PolicyVehicle vehicle) {
	_resetFetchedInfo(policy, vehicle);
	vehicle.setVinCode(null);
	vehicle.setArea(null);
	evaluateMajorCity(vehicle);
    }

    private void _resetFetchedInfo(Policy policy, PolicyVehicle vehicle) {
	vehicle.setFetched(false);
	vehicle.setVehicleClass(null);
	vehicle.setVehicleAgeClass(null);
	vehicle.setVehicleClass(null);
	vehicle.setVehicleAgeClass(null);
	vehicle.setColor(null);
	vehicle.setManufacturer(null);
	vehicle.setModel(null);
	vehicle.setYearOfManufacture(null);
    }

    public void handleAreaChanged(PolicyVehicle insuredVehicle) {
	KZArea region = insuredVehicle.getArea();
	KZCity city = insuredVehicle.getCity();
	if (region != null && city != null && city.getArea() != null && !city.getArea().equals(region))
	    insuredVehicle.setCity(null);
    }

    public void handleCityChanged(PolicyVehicle vehicle) {
	KZCity city = vehicle.getCity();
	if (city != null && city.getArea() != null)
	    vehicle.setArea(city.getArea());
    }

    @Override
    public void handleTemporaryEntryChanged(PolicyVehicle vehicle) {
	// if (vehicle.isTemporaryEntry()) {
	// vehicle.setArea(null);
	// vehicle.setCity(null);
	// }
    }
}
