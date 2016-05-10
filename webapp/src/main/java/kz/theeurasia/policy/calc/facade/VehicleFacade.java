package kz.theeurasia.policy.calc.facade;

import java.io.Serializable;
import java.util.Calendar;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.lapsa.insurance.domain.InsuredVehicleData;
import com.lapsa.insurance.domain.VehicleData;
import com.lapsa.insurance.elements.VehicleAgeClass;
import com.lapsa.kz.country.KZArea;
import com.lapsa.kz.country.KZCity;

import kz.theeurasia.esbdproxy.domain.entities.osgpovts.VehicleEntity;
import kz.theeurasia.esbdproxy.services.InvalidInputParameter;
import kz.theeurasia.esbdproxy.services.NotFound;
import kz.theeurasia.esbdproxy.services.osgpovts.VehicleServiceDAO;
import kz.theeurasia.policy.calc.bean.Calculation;

@Named
@ApplicationScoped
public class VehicleFacade implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private VehicleServiceDAO vehicleService;

    public InsuredVehicleData add(Calculation policy) throws ValidationException {
	if (policy.getInsuredVehicles().size() > 0 && policy.getInsuredDrivers().size() > 1)
	    throw new ValidationException(MessageBundleCode.ONLY_ONE_VEHICLE_ALLOWED);
	InsuredVehicleData e = new InsuredVehicleData();
	policy.getInsuredVehicles().add(e);
	_reset(policy, e);
	return e;
    }

    public void remove(Calculation policy, InsuredVehicleData vehicle) throws ValidationException {
	if (policy.getInsuredVehicles().size() <= 1)
	    throw new ValidationException(MessageBundleCode.VEHICLES_LIST_CANT_BE_EMPTY);
	policy.getInsuredVehicles().remove(vehicle);
    }

    public void fetchInfo(Calculation policy, InsuredVehicleData vehicle) throws ValidationException {
	try {
	    VehicleEntity fetched = vehicleService.getByVINCode(vehicle.getVehicleData().getVinCode());
	    vehicle.setFetched(true);
	    vehicle.setVehicleClass(fetched.getVehicleClass());
	    if (fetched.getRealeaseDate() != null) {
		int age = CalculatorUtil.calculateAgeByDOB(fetched.getRealeaseDate());
		vehicle.setVehicleAgeClass(age > 7 ? VehicleAgeClass.OVER7 : VehicleAgeClass.UNDER7);
	    }
	    vehicle.getVehicleData().setColor(fetched.getColor());
	    vehicle.getVehicleData().setManufacturer(fetched.getVehicleModel().getManufacturer().getName());
	    vehicle.getVehicleData().setModel(fetched.getVehicleModel().getName());
	    vehicle.getVehicleData().setYearOfIssue(fetched.getRealeaseDate().get(Calendar.YEAR));
	} catch (NotFound | InvalidInputParameter e) {
	    _resetFetchedInfo(policy, vehicle);
	}
    }

    public void evaluateMajorCity(InsuredVehicleData insuredVehicle) {
	KZArea region = insuredVehicle.getRegion();
	if (region == null)
	    return;
	if (region.equals(KZArea.GALM))
	    insuredVehicle.setCity(KZCity.ALM);
	if (region.equals(KZArea.GAST))
	    insuredVehicle.setCity(KZCity.AST);
    }

    private void _reset(Calculation policy, InsuredVehicleData vehicle) {
	_resetFetchedInfo(policy, vehicle);
	vehicle.getVehicleData().setVinCode(null);
	vehicle.setRegion(null);
	evaluateMajorCity(vehicle);
    }

    private void _resetFetchedInfo(Calculation policy, InsuredVehicleData vehicle) {
	vehicle.setFetched(false);
	vehicle.setVehicleClass(null);
	vehicle.setVehicleAgeClass(null);
	vehicle.setVehicleData(new VehicleData());
    }

    public void handleAreaChanged(InsuredVehicleData insuredVehicle) {
	KZArea region = insuredVehicle.getRegion();
	KZCity city = insuredVehicle.getCity();
	if (region != null && city != null && city.getArea() != null && !city.getArea().equals(region))
	    insuredVehicle.setCity(null);
    }

    public void handleCityChanged(InsuredVehicleData vehicle) {
	KZCity city = vehicle.getCity();
	if (city != null && city.getArea() != null)
	    vehicle.setRegion(city.getArea());
    }
}
