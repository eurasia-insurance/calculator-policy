package kz.theeurasia.policy.calc.bean.impl;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.ProjectStage;
import javax.inject.Inject;

import com.lapsa.insurance.elements.InsuredExpirienceClass;
import com.lapsa.kz.country.KZArea;
import com.lapsa.kz.country.KZCity;

import kz.theeurasia.policy.calc.bean.CalculationData;
import kz.theeurasia.policy.calc.bean.DefaultCalculationDataBuilder;
import kz.theeurasia.policy.calc.bean.ProjectStageDepend;
import kz.theeurasia.policy.calc.facade.CalculationFacade;
import kz.theeurasia.policy.calc.facade.DriverFacade;
import kz.theeurasia.policy.calc.facade.ValidationException;
import kz.theeurasia.policy.calc.facade.VehicleFacade;
import kz.theeurasia.policy.domain.InsuredDriverData;
import kz.theeurasia.policy.domain.InsuredVehicleData;

@RequestScoped
@ProjectStageDepend(stage = ProjectStage.Development)
@ManyVehicles
public class DevelopingManyVehiclesDataBuilder implements DefaultCalculationDataBuilder {

    @Inject
    private DriverFacade driverFacade;

    @Inject
    private VehicleFacade vehicleFacade;

    @Inject
    private CalculationFacade calculationFacade;

    @Inject
    private Logger logger;

    @Override
    public void buildDefaultData(CalculationData calculationData) {
	try {
	    InsuredDriverData drv2 = driverFacade.add(calculationData);
	    drv2.setIdNumber("870622300359");
	    driverFacade.fetchInfo(calculationData, drv2);
	    drv2.setExpirienceClass(InsuredExpirienceClass.MORE2);
	    drv2.getResidenceData().setCity(KZCity.ALM);
	    drv2.getDriverLicenseData().setNumber("123");
	    drv2.getDriverLicenseData().setDateOfIssue(new Date());
	    drv2.setHasAnyPrivilege(false);

	    InsuredVehicleData vhc1 = vehicleFacade.add(calculationData);
	    vhc1.getVehicleData().setVinCode("JN1TANS51U0303376");
	    vehicleFacade.fetchInfo(calculationData, vhc1);
	    vhc1.getVehicleCertificateData().setRegion(KZArea.GALM);
	    vehicleFacade.evaluateMajorCity(vhc1);

	    InsuredVehicleData vhc2 = vehicleFacade.add(calculationData);
	    vhc2.getVehicleData().setVinCode("WDB2030421F503751");
	    vehicleFacade.fetchInfo(calculationData, vhc2);
	    vhc2.getVehicleCertificateData().setRegion(KZArea.GALM);
	    vehicleFacade.evaluateMajorCity(vhc2);

	    calculationFacade.calculatePremiumCost(calculationData);
	} catch (ValidationException e) {
	    logger.log(Level.SEVERE, e.getMessage(), e);
	}
    }

}
