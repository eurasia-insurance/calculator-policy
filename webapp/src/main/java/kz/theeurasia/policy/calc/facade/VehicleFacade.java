package kz.theeurasia.policy.calc.facade;

import java.io.Serializable;
import java.util.Calendar;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.lapsa.kz.country.KZArea;
import com.lapsa.kz.country.KZCity;

import kz.theeurasia.esbdproxy.domain.dict.osgpovts.VehicleAgeClassDict;
import kz.theeurasia.esbdproxy.domain.dict.osgpovts.VehicleClassDict;
import kz.theeurasia.esbdproxy.domain.entities.osgpovts.VehicleEntity;
import kz.theeurasia.esbdproxy.services.InvalidInputParameter;
import kz.theeurasia.esbdproxy.services.NotFound;
import kz.theeurasia.esbdproxy.services.osgpovts.VehicleServiceDAO;
import kz.theeurasia.policy.calc.bean.CalculationData;
import kz.theeurasia.policy.domain.InsuredVehicleData;
import kz.theeurasia.policy.domain.VehicleData;

@Named
@ApplicationScoped
public class VehicleFacade implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private VehicleServiceDAO vehicleService;

    public InsuredVehicleData add(CalculationData policy) throws ValidationException {
	if (policy.getInsuredVehicles().size() > 0 && policy.getInsuredDrivers().size() > 1)
	    throw new ValidationException(MessageBundleCode.ONLY_ONE_VEHICLE_ALLOWED);
	InsuredVehicleData e = new InsuredVehicleData();
	policy.getInsuredVehicles().add(e);
	_reset(policy, e);
	return e;
    }

    public void remove(CalculationData policy, InsuredVehicleData vehicle) throws ValidationException {
	if (policy.getInsuredVehicles().size() <= 1)
	    throw new ValidationException(MessageBundleCode.VEHICLES_LIST_CANT_BE_EMPTY);
	policy.getInsuredVehicles().remove(vehicle);
    }

    public void fetchInfo(CalculationData policy, InsuredVehicleData vehicle) throws ValidationException {
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
	KZArea region = insuredVehicle.getVehicleCertificateData().getRegion();
	if (region == null) {
	    insuredVehicle.getVehicleCertificateData().setCity(null);
	    return;
	}
	if (region.equals(KZArea.GALM))
	    insuredVehicle.getVehicleCertificateData().setCity(KZCity.ALM);
	if (region.equals(KZArea.GAST))
	    insuredVehicle.getVehicleCertificateData().setCity(KZCity.AST);
    }

    private void _reset(CalculationData policy, InsuredVehicleData vehicle) {
	_resetFetchedInfo(policy, vehicle);
	vehicle.getVehicleData().setVinCode(null);
	vehicle.getVehicleCertificateData().setRegion(null);
	evaluateMajorCity(vehicle);
    }

    private void _resetFetchedInfo(CalculationData policy, InsuredVehicleData vehicle) {
	vehicle.setFetchedEntity(null);
	vehicle.setVehicleClass(VehicleClassDict.UNSPECIFIED);
	vehicle.setVehicleAgeClass(VehicleAgeClassDict.UNSPECIFIED);
	vehicle.setVehicleData(new VehicleData());
    }
}
