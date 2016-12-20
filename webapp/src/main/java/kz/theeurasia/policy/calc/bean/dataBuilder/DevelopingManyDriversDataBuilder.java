package kz.theeurasia.policy.calc.bean.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.ProjectStage;
import javax.inject.Inject;

import com.lapsa.country.Country;
import com.lapsa.insurance.domain.policy.Policy;
import com.lapsa.insurance.domain.policy.PolicyDriver;
import com.lapsa.insurance.domain.policy.PolicyVehicle;
import com.lapsa.insurance.elements.IdentityCardType;
import com.lapsa.insurance.elements.InsuredAgeClass;
import com.lapsa.insurance.elements.InsuredExpirienceClass;
import com.lapsa.insurance.elements.Sex;
import com.lapsa.kz.country.KZArea;
import com.lapsa.kz.country.KZCity;

import kz.theeurasia.policy.calc.api.DefaultCalculationDataBuilder;
import kz.theeurasia.policy.calc.bean.ProjectStageDepend;
import kz.theeurasia.policy.calc.facade.CalculationFacade;
import kz.theeurasia.policy.calc.facade.DriverFacade;
import kz.theeurasia.policy.calc.facade.ValidationException;
import kz.theeurasia.policy.calc.facade.VehicleFacade;

@RequestScoped
@ProjectStageDepend(stage = ProjectStage.Development)
@ManyDrivers
public class DevelopingManyDriversDataBuilder implements DefaultCalculationDataBuilder {

    @Inject
    private DriverFacade driverFacade;

    @Inject
    private VehicleFacade vehicleFacade;

    @Inject
    private CalculationFacade calculationFacade;

    @Inject
    private Logger logger;

    @Override
    public void buildDefaultData(Policy calculation) {
	try {
	    PolicyDriver drv1 = driverFacade.add(calculation);
	    drv1.setIdNumber("570325300699");
	    driverFacade.fetchInfo(calculation, drv1);
	    drv1.setExpirienceClass(InsuredExpirienceClass.MORE2);
	    drv1.getResidenceData().setCity(KZCity.ALM);
	    drv1.getResidenceData().setAddress("Джамбула, 231");
	    drv1.getResidenceData().setResident(true);
	    drv1.getOriginData().setCountry(Country.KAZ);
	    drv1.getDriverLicenseData().setNumber("123");
	    drv1.getDriverLicenseData().setDateOfIssue(new Date());
	    drv1.setHasAnyPrivilege(true);
	    drv1.setGpwParticipant(true);
	    drv1.getGpwParticipantCertificateData().setNumber("123");
	    drv1.getGpwParticipantCertificateData().setDateOfIssue(new Date());
	    drv1.setHandicaped(true);
	    drv1.getHandicapedCertificateData().setNumber("123");
	    drv1.getHandicapedCertificateData().setValidFrom(new Date());
	    drv1.getHandicapedCertificateData().setValidTill(new Date());
	    drv1.setPensioner(true);
	    drv1.getPensionerCertificateData().setNumber("123");
	    drv1.getPensionerCertificateData().setDateOfIssue(new Date());
	    drv1.setPriveleger(true);
	    drv1.getPrivilegerCertificateData().setType("123");
	    drv1.getPrivilegerCertificateData().setNumber("123");
	    drv1.getPrivilegerCertificateData().setDateOfIssue(new Date());

	    PolicyDriver drv2 = driverFacade.add(calculation);
	    drv2.setIdNumber("870622300359");
	    driverFacade.fetchInfo(calculation, drv2);
	    drv2.setExpirienceClass(InsuredExpirienceClass.MORE2);
	    drv2.getResidenceData().setCity(KZCity.ALM);
	    // drv2.getResidenceData().setAddress("Джамбула, 231");
	    // drv2.getResidenceData().setResident(true);
	    // drv2.getOriginData().setCountry(CountryDict.KAZ);
	    drv2.getDriverLicenseData().setNumber("123");
	    drv2.getDriverLicenseData().setDateOfIssue(new Date());
	    drv2.setHasAnyPrivilege(false);

	    PolicyDriver drv3 = driverFacade.add(calculation);
	    drv3.setIdNumber("800225000319");
	    driverFacade.fetchInfo(calculation, drv3);
	    drv3.setAgeClass(InsuredAgeClass.OVER25);
	    drv3.setExpirienceClass(InsuredExpirienceClass.MORE2);
	    drv3.getPersonalData().setName("Вадим");
	    drv3.getPersonalData().setSurename("Исаев");
	    drv3.getPersonalData().setPatronymic("Олегович");
	    Calendar dob = Calendar.getInstance();
	    dob.set(1980, Calendar.FEBRUARY, 25);
	    drv3.getPersonalData().setDayOfBirth(dob.getTime());
	    drv3.getPersonalData().setSex(Sex.MALE);
	    drv3.getIdentityCardData().setType(IdentityCardType.PASSPORT);
	    drv3.getIdentityCardData().setDateOfIssue(new Date());
	    drv3.getIdentityCardData().setIssuingAuthority("МВД РФ");
	    drv3.getIdentityCardData().setNumber("123123123");
	    drv3.getResidenceData().setCity(KZCity.ALM);
	    drv3.getResidenceData().setAddress("Джамбула, 231");
	    drv3.getResidenceData().setResident(true);
	    drv3.getOriginData().setCountry(Country.KAZ);
	    drv3.getDriverLicenseData().setNumber("123");
	    drv3.getDriverLicenseData().setDateOfIssue(new Date());
	    drv3.setHasAnyPrivilege(false);

	    PolicyDriver drv4 = driverFacade.add(calculation);
	    drv4.setIdNumber("860401402685");
	    driverFacade.fetchInfo(calculation, drv4);
	    drv4.setExpirienceClass(InsuredExpirienceClass.MORE2);
	    drv4.getPersonalData().setSex(Sex.FEMALE);
	    drv4.getIdentityCardData().setIssuingAuthority("МВД РК");

	    drv4.getResidenceData().setCity(KZCity.ALM);
	    drv4.getResidenceData().setAddress("Джамбула, 231");
	    drv4.getResidenceData().setResident(true);
	    drv4.getOriginData().setCountry(Country.KAZ);
	    drv4.getDriverLicenseData().setNumber("123");
	    drv4.getDriverLicenseData().setDateOfIssue(new Date());
	    drv4.setHasAnyPrivilege(false);

	    PolicyVehicle vhc1 = vehicleFacade.add(calculation);
	    vhc1.setVinCode("JN1TANS51U0303376");
	    vehicleFacade.fetchInfo(calculation, vhc1);
	    vhc1.setArea(KZArea.GALM);
	    vehicleFacade.evaluateMajorCity(vhc1);

	    calculationFacade.calculatePremiumCost(calculation);
	} catch (ValidationException e) {
	    logger.log(Level.SEVERE, e.getMessage(), e);
	}
    }

}
