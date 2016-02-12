package kz.theeurasia.policy.facade;

import java.util.Calendar;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import kz.theeurasia.esbdproxy.domain.dict.general.CountryRegionDict;
import kz.theeurasia.esbdproxy.domain.dict.osgpovts.VehicleAgeClassDict;
import kz.theeurasia.esbdproxy.domain.dict.osgpovts.VehicleClassDict;
import kz.theeurasia.esbdproxy.domain.entities.osgpovts.VehicleEntity;
import kz.theeurasia.esbdproxy.services.InvalidInputParameter;
import kz.theeurasia.esbdproxy.services.NotFound;
import kz.theeurasia.esbdproxy.services.general.CountryRegionServiceDAO;
import kz.theeurasia.esbdproxy.services.osgpovts.VehicleServiceDAO;
import kz.theeurasia.policy.domain.InsuredVehicleData;
import kz.theeurasia.policy.domain.PolicyRequestData;
import kz.theeurasia.policy.domain.VehicleData;
import kz.theeurasia.policy.view.MessageBundleCode;
import kz.theeurasia.policy.view.ValidationException;

@ManagedBean
@ViewScoped
public class VehicleFacade {

    @EJB
    private CountryRegionServiceDAO countryRegionService;

    @EJB
    private VehicleServiceDAO vehicleService;

    public InsuredVehicleData add(PolicyRequestData policy) throws ValidationException {
	if (policy.getInsuredVehicles().size() > 0 && policy.getInsuredDrivers().size() > 1)
	    throw new ValidationException(MessageBundleCode.ONLY_ONE_VEHICLE_ALLOWED);
	InsuredVehicleData e = new InsuredVehicleData();
	policy.getInsuredVehicles().add(e);
	_reset(policy, e);
	return e;
    }

    public void remove(PolicyRequestData policy, InsuredVehicleData vehicle) throws ValidationException {
	if (policy.getInsuredVehicles().size() <= 1)
	    throw new ValidationException(MessageBundleCode.VEHICLES_LIST_CANT_BE_EMPTY);
	policy.getInsuredVehicles().remove(vehicle);
    }

    public void fetchInfo(PolicyRequestData policy, InsuredVehicleData vehicle) throws ValidationException {
	try {
	    VehicleEntity fetched = vehicleService.getByVINCode(vehicle.getVehicleData().getVinCode());
	    vehicle.setFetchedEntity(fetched);
	    vehicle.setVehicleClass(fetched.getVehicleClass());
	    if (fetched.getRealeaseDate() != null) {
		int age = CalculatorUtil.calculateAgeByDOB(fetched.getRealeaseDate());
		vehicle.setVehicleAgeClass(age > 7 ? VehicleAgeClassDict.OVER7 : VehicleAgeClassDict.UNDER7);
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
	insuredVehicle.setForcedMajorCity(
		insuredVehicle.getCertificateData().getRegion().equals(CountryRegionDict.GALM)
			|| insuredVehicle.getCertificateData().getRegion().equals(CountryRegionDict.GAST));
	if (insuredVehicle.isForcedMajorCity())
	    insuredVehicle.getCertificateData().setMajorCity(true);
    }

    private void _reset(PolicyRequestData policy, InsuredVehicleData vehicle) {
	_resetFetchedInfo(policy, vehicle);
	vehicle.getVehicleData().setVinCode(null);
	vehicle.getCertificateData().setRegion(CountryRegionDict.UNSPECIFIED);
	evaluateMajorCity(vehicle);
    }

    private void _resetFetchedInfo(PolicyRequestData policy, InsuredVehicleData vehicle) {
	vehicle.setFetchedEntity(null);
	vehicle.setVehicleClass(VehicleClassDict.UNSPECIFIED);
	vehicle.setVehicleAgeClass(VehicleAgeClassDict.UNSPECIFIED);
	vehicle.setVehicleData(new VehicleData());
    }

}
