package kz.theeurasia.policy.calc.facade;

import java.util.Calendar;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.lapsa.insurance.domain.policy.Policy;
import com.lapsa.insurance.domain.policy.PolicyVehicle;
import com.lapsa.insurance.elements.VehicleAgeClass;
import com.lapsa.insurance.esbd.domain.entities.policy.VehicleEntity;
import com.lapsa.insurance.esbd.services.InvalidInputParameter;
import com.lapsa.insurance.esbd.services.NotFound;
import com.lapsa.insurance.esbd.services.policy.VehicleServiceDAO;
import com.lapsa.kz.country.KZArea;
import com.lapsa.kz.country.KZCity;

import kz.theeurasia.policy.calc.api.VehicleFacade;

@Named
@ApplicationScoped
public class DefaultVehicleFacade implements VehicleFacade {

    private static final long serialVersionUID = 1L;

    @Inject
    private VehicleServiceDAO vehicleService;

    @Override
    public PolicyVehicle add(Policy policy) throws ValidationException {
	if (policy.getInsuredVehicles().size() > 0 && policy.getInsuredDrivers().size() > 1)
	    throw new ValidationException(MessageBundleCode.ONLY_ONE_VEHICLE_ALLOWED);
	PolicyVehicle e = new PolicyVehicle();
	policy.getInsuredVehicles().add(e);
	_reset(policy, e);
	return e;
    }

    @Override
    public void remove(Policy policy, PolicyVehicle vehicle) throws ValidationException {
	if (policy.getInsuredVehicles().size() <= 1)
	    throw new ValidationException(MessageBundleCode.VEHICLES_LIST_CANT_BE_EMPTY);
	policy.getInsuredVehicles().remove(vehicle);
    }

    @Override
    public void fetchInfo(Policy policy, PolicyVehicle vehicle) throws ValidationException {
	try {
	    VehicleEntity fetched = vehicleService.getByVINCode(vehicle.getVinCode());
	    vehicle.setFetched(true);
	    vehicle.setVehicleClass(fetched.getVehicleClass());
	    if (fetched.getRealeaseDate() != null) {
		int age = CalculatorUtil.calculateAgeByDOB(fetched.getRealeaseDate());
		vehicle.setVehicleAgeClass(age > 7 ? VehicleAgeClass.OVER7 : VehicleAgeClass.UNDER7);
	    }
	    vehicle.setColor(fetched.getColor());
	    vehicle.setManufacturer(fetched.getVehicleModel().getManufacturer().getName());
	    vehicle.setModel(fetched.getVehicleModel().getName());
	    vehicle.setYearOfManufacture(fetched.getRealeaseDate().get(Calendar.YEAR));
	} catch (NotFound | InvalidInputParameter e) {
	    _resetFetchedInfo(policy, vehicle);
	}
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
}
