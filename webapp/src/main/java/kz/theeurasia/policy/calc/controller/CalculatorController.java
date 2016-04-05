package kz.theeurasia.policy.calc.controller;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.lapsa.country.Country;
import com.lapsa.kz.country.KZArea;
import com.lapsa.kz.country.KZCity;

import kz.theeurasia.esbdproxy.domain.dict.general.IdentityCardTypeDict;
import kz.theeurasia.esbdproxy.domain.dict.general.SexDict;
import kz.theeurasia.esbdproxy.domain.enums.osgpovts.InsuredAgeClassEnum;
import kz.theeurasia.esbdproxy.domain.enums.osgpovts.InsuredExpirienceClassEnum;
import kz.theeurasia.policy.calc.bean.CalculationData;
import kz.theeurasia.policy.calc.facade.CalculationFacade;
import kz.theeurasia.policy.calc.facade.DriverFacade;
import kz.theeurasia.policy.calc.facade.GlobalMessageBundleCode;
import kz.theeurasia.policy.calc.facade.MessageBundleCode;
import kz.theeurasia.policy.calc.facade.ValidationException;
import kz.theeurasia.policy.calc.facade.VehicleFacade;
import kz.theeurasia.policy.domain.InsuredDriverData;
import kz.theeurasia.policy.domain.InsuredVehicleData;
import kz.theeurasia.policy.services.LocaleService;

@Named("calculationController")
@ViewScoped
public class CalculatorController implements Serializable {

    private static final long serialVersionUID = 1L;

    private ResourceBundle glb;

    private ResourceBundle gpovts;

    @Inject
    private DriverFacade driverFacade;

    @Inject
    private VehicleFacade vehicleFacade;

    @Inject
    private CalculationFacade calculationFacade;

    @Inject
    private CalculationData data;

    @Inject
    private LocaleService localeService;

    public String doVoid() {
	return "";
    }

    @PostConstruct
    public void init() {
	glb = ResourceBundle.getBundle(GlobalMessageBundleCode.BUNDLE_BASE_NAME, localeService.getLocale());
	gpovts = ResourceBundle.getBundle(MessageBundleCode.BUNDLE_BASE_NAME, localeService.getLocale());
	try {
	    FacesContext facesContext = FacesContext.getCurrentInstance();
	    Application application = facesContext.getApplication();

	    switch (application.getProjectStage()) {
	    case Development:
		// buildTestDataManyDrivers();
		buildTestDataManyVehicles();
		break;
	    case Production:
	    default:
		driverFacade.add(data);
		vehicleFacade.add(data);
	    }
	} catch (ValidationException e) {
	    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
		    glb.getString(GlobalMessageBundleCode.MESSAGES_SERVER_FATAL_ERROR_CAPTION.getMessageBundleCode()),
		    glb.getString(
			    GlobalMessageBundleCode.MESSAGES_SERVER_FATAL_ERROR_DESCRIPTION.getMessageBundleCode())));
	}
    }

    public void buildTestDataManyVehicles() throws ValidationException {
	InsuredDriverData drv2 = driverFacade.add(data);
	drv2.setIdNumber("870622300359");
	driverFacade.fetchInfo(data, drv2);
	drv2.setExpirienceClass(InsuredExpirienceClassEnum.MORE2);
	drv2.getResidenceData().setCity(KZCity.ALM);
	drv2.getDriverLicenseData().setNumber("123");
	drv2.getDriverLicenseData().setDateOfIssue(new Date());
	drv2.setHasAnyPrivilege(false);

	InsuredVehicleData vhc1 = vehicleFacade.add(data);
	vhc1.getVehicleData().setVinCode("JN1TANS51U0303376");
	vehicleFacade.fetchInfo(data, vhc1);
	vhc1.getVehicleCertificateData().setRegion(KZArea.GALM);
	vehicleFacade.evaluateMajorCity(vhc1);
	calculationFacade.calculatePremiumCost(data);

	InsuredVehicleData vhc2 = vehicleFacade.add(data);
	vhc2.getVehicleData().setVinCode("WDB2030421F503751");
	vehicleFacade.fetchInfo(data, vhc2);
	vhc2.getVehicleCertificateData().setRegion(KZArea.GALM);
	vehicleFacade.evaluateMajorCity(vhc2);
	calculationFacade.calculatePremiumCost(data);
    }

    public void buildTestDataManyDrivers() throws ValidationException {
	InsuredDriverData drv1 = driverFacade.add(data);
	drv1.setIdNumber("570325300699");
	driverFacade.fetchInfo(data, drv1);
	drv1.setExpirienceClass(InsuredExpirienceClassEnum.MORE2);
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

	InsuredDriverData drv2 = driverFacade.add(data);
	drv2.setIdNumber("870622300359");
	driverFacade.fetchInfo(data, drv2);
	drv2.setExpirienceClass(InsuredExpirienceClassEnum.MORE2);
	drv2.getResidenceData().setCity(KZCity.ALM);
	// drv2.getResidenceData().setAddress("Джамбула, 231");
	// drv2.getResidenceData().setResident(true);
	// drv2.getOriginData().setCountry(CountryDict.KAZ);
	drv2.getDriverLicenseData().setNumber("123");
	drv2.getDriverLicenseData().setDateOfIssue(new Date());
	drv2.setHasAnyPrivilege(false);

	InsuredDriverData drv3 = driverFacade.add(data);
	drv3.setIdNumber("800225000319");
	driverFacade.fetchInfo(data, drv3);
	drv3.setAgeClass(InsuredAgeClassEnum.OVER25);
	drv3.setExpirienceClass(InsuredExpirienceClassEnum.MORE2);
	drv3.getPersonalData().setName("Вадим");
	drv3.getPersonalData().setSurename("Исаев");
	drv3.getPersonalData().setPatronymic("Олегович");
	Calendar dob = Calendar.getInstance();
	dob.set(1980, Calendar.FEBRUARY, 25);
	drv3.getPersonalData().setDayOfBirth(dob.getTime());
	drv3.getPersonalData().setSex(SexDict.MALE);
	drv3.getIdentityCardData().setType(IdentityCardTypeDict.PASSPORT);
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

	InsuredDriverData drv4 = driverFacade.add(data);
	drv4.setIdNumber("860401402685");
	driverFacade.fetchInfo(data, drv4);
	drv4.setExpirienceClass(InsuredExpirienceClassEnum.MORE2);
	drv4.getPersonalData().setSex(SexDict.FEMALE);
	drv4.getIdentityCardData().setIssuingAuthority("МВД РК");

	drv4.getResidenceData().setCity(KZCity.ALM);
	drv4.getResidenceData().setAddress("Джамбула, 231");
	drv4.getResidenceData().setResident(true);
	drv4.getOriginData().setCountry(Country.KAZ);
	drv4.getDriverLicenseData().setNumber("123");
	drv4.getDriverLicenseData().setDateOfIssue(new Date());
	drv4.setHasAnyPrivilege(false);

	InsuredVehicleData vhc1 = vehicleFacade.add(data);
	vhc1.getVehicleData().setVinCode("JN1TANS51U0303376");
	vehicleFacade.fetchInfo(data, vhc1);
	vhc1.getVehicleCertificateData().setRegion(KZArea.GALM);
	vehicleFacade.evaluateMajorCity(vhc1);
	calculationFacade.calculatePremiumCost(data);
    }

    public void addInsuredDriver() {
	try {
	    driverFacade.add(data);
	} catch (ValidationException e) {
	    FacesContext.getCurrentInstance().addMessage(null,
		    new FacesMessage(FacesMessage.SEVERITY_WARN,
			    gpovts.getString(e.getMessageCode().getMessageBundleCode()),
			    gpovts.getString(e.getDescriptionCode().getMessageBundleCode())));
	}
	calculationFacade.calculatePremiumCost(data);
    }

    public void removeInsuredDriver(InsuredDriverData driver) {
	try {
	    driverFacade.remove(data, driver);
	} catch (ValidationException e) {
	    FacesContext.getCurrentInstance().addMessage(null,
		    new FacesMessage(FacesMessage.SEVERITY_WARN,
			    gpovts.getString(e.getMessageCode().getMessageBundleCode()),
			    gpovts.getString(e.getDescriptionCode().getMessageBundleCode())));
	}
	calculationFacade.calculatePremiumCost(data);
    }

    public void addInsuredVehicle() {
	try {
	    vehicleFacade.add(data);
	} catch (ValidationException e) {
	    FacesContext.getCurrentInstance().addMessage(null,
		    new FacesMessage(FacesMessage.SEVERITY_WARN,
			    gpovts.getString(e.getMessageCode().getMessageBundleCode()),
			    gpovts.getString(e.getDescriptionCode().getMessageBundleCode())));
	}
	calculationFacade.calculatePremiumCost(data);
    }

    public void removeInsuredVehicle(InsuredVehicleData insuredVehicle) {
	try {
	    vehicleFacade.remove(data, insuredVehicle);
	} catch (ValidationException e) {
	    FacesContext.getCurrentInstance().addMessage(null,
		    new FacesMessage(FacesMessage.SEVERITY_WARN,
			    gpovts.getString(e.getMessageCode().getMessageBundleCode()),
			    gpovts.getString(e.getDescriptionCode().getMessageBundleCode())));
	}
	calculationFacade.calculatePremiumCost(data);
    }

    public void doCalculatePolicyCost() {
	calculationFacade.calculatePremiumCost(data);
    }

    public void onDriverIdNumberChanged(InsuredDriverData insuredDriver) {
	try {
	    driverFacade.fetchInfo(data, insuredDriver);
	} catch (ValidationException e) {
	}
	calculationFacade.calculatePremiumCost(data);
    }

    public void onPolicyCostCalculationFormChanged() {
	calculationFacade.calculatePremiumCost(data);
    }

    public void onVehicleVinCodeChanged(InsuredVehicleData insuredVehicle) {
	try {
	    vehicleFacade.fetchInfo(data, insuredVehicle);
	} catch (ValidationException e) {
	}
	calculationFacade.calculatePremiumCost(data);
    }

    public void onVehicleRegionChanged(InsuredVehicleData insuredVehicle) {
	vehicleFacade.evaluateMajorCity(insuredVehicle);
	calculationFacade.calculatePremiumCost(data);
    }

    public void onVehicleCityChanged(InsuredVehicleData insuredVehicle) {
	calculationFacade.calculatePremiumCost(data);
    }
}
