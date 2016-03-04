package kz.theeurasia.eurasia36.facade;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import kz.theeurasia.esbdproxy.domain.dict.osgpovts.InsuranceClassTypeDict;
import kz.theeurasia.esbdproxy.domain.entities.general.SubjectPersonEntity;
import kz.theeurasia.esbdproxy.domain.enums.osgpovts.InsuredAgeClassEnum;
import kz.theeurasia.esbdproxy.domain.enums.osgpovts.InsuredExpirienceClassEnum;
import kz.theeurasia.esbdproxy.services.InvalidInputParameter;
import kz.theeurasia.esbdproxy.services.NotFound;
import kz.theeurasia.esbdproxy.services.general.KZCityServiceDAO;
import kz.theeurasia.esbdproxy.services.general.SubjectPersonServiceDAO;
import kz.theeurasia.esbdproxy.services.osgpovts.InsuranceClassTypeServiceDAO;
import kz.theeurasia.eurasia36.bean.MessageBundleCode;
import kz.theeurasia.eurasia36.bean.ValidationException;
import kz.theeurasia.policy.domain.ContactData;
import kz.theeurasia.policy.domain.IdentityCardData;
import kz.theeurasia.policy.domain.InsuredDriverData;
import kz.theeurasia.policy.domain.OriginData;
import kz.theeurasia.policy.domain.PersonalData;
import kz.theeurasia.policy.domain.PolicyRequestData;
import kz.theeurasia.policy.domain.ResidenceData;

@Named
@ViewScoped
public class DriverFacade implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private SubjectPersonServiceDAO subjectPersonService;

    @Inject
    private InsuranceClassTypeServiceDAO insuranceClassTypeService;

    @Inject
    private KZCityServiceDAO kzCityServiceDAO;

    public InsuredDriverData add(PolicyRequestData policy) throws ValidationException {
	if (policy.getInsuredDrivers().size() > 0 && policy.getInsuredVehicles().size() > 1)
	    throw new ValidationException(MessageBundleCode.ONLY_ONE_DRIVER_ALLOWED);
	InsuredDriverData e = new InsuredDriverData();
	policy.getInsuredDrivers().add(e);
	_reset(policy, e);
	return e;
    }

    public void remove(PolicyRequestData policy, InsuredDriverData driver) throws ValidationException {
	if (policy.getInsuredDrivers().size() <= 1)
	    throw new ValidationException(MessageBundleCode.DRIVER_LIST_CANT_BE_EMPTY);
	policy.getInsuredDrivers().remove(driver);
    }

    public void fetchInfo(PolicyRequestData policy, InsuredDriverData driver) throws ValidationException {
	try {
	    SubjectPersonEntity fetched = subjectPersonService.getByIIN(driver.getIdNumber());
	    driver.setFetchedEntity(fetched);

	    driver.getPersonalData().setName(fetched.getPersonal().getName());
	    driver.getPersonalData().setSurename(fetched.getPersonal().getSurename());
	    driver.getPersonalData().setPatronymic(fetched.getPersonal().getPatronymic());
	    driver.getPersonalData().setSex(fetched.getPersonal().getSex());
	    driver.getPersonalData().setDayOfBirth(fetched.getPersonal().getDayOfBirth().getTime());

	    driver.getResidenceData().setResident(fetched.getOrigin().isResident());
	    driver.getResidenceData().setAddress(fetched.getContact().getHomeAdress());

	    // TODO Здесь потенциальная проблема связанная с тем несоответстием
	    // мапирования
	    if (fetched.getOrigin().getCity() != null)
		driver.getResidenceData().setCity(kzCityServiceDAO.getById(fetched.getOrigin().getCity().getId()));
	    driver.getOriginData().setCountry(fetched.getOrigin().getCountry());

	    driver.getIdentityCardData().setDateOfIssue(fetched.getIdentityCard().getDateOfIssue().getTime());
	    driver.getIdentityCardData().setType(fetched.getIdentityCard().getIdentityCardType());
	    driver.getIdentityCardData().setIssuingAuthority(fetched.getIdentityCard().getIssuingAuthority());
	    driver.getIdentityCardData().setNumber(fetched.getIdentityCard().getNumber());

	    driver.setTaxPayerNumber(fetched.getTaxPayerNumber());

	    driver.getContactData().setEmail(fetched.getContact().getEmail());
	    driver.getContactData().setPhone(fetched.getContact().getPhone());
	    driver.getContactData().setSiteUrl(fetched.getContact().getSiteUrl());

	    try {
		InsuranceClassTypeDict insuranceClassType = insuranceClassTypeService.getForSubject(fetched);
		driver.setInsuranceClassType(insuranceClassType);
	    } catch (NotFound | InvalidInputParameter e) {
		driver.setInsuranceClassType(insuranceClassTypeService.getDefault());
	    }
	    if (fetched.getPersonal().getDayOfBirth() != null) {
		int years = CalculatorUtil.calculateAgeByDOB(driver.getPersonalData().getDayOfBirth());
		driver.setAgeClass(years > 25 ? InsuredAgeClassEnum.OVER25 : InsuredAgeClassEnum.UNDER25);
	    }

	} catch (NotFound e) {
	    _resetFetchedInfo(policy, driver);
	    driver.setInsuranceClassType(insuranceClassTypeService.getDefault());
	} catch (InvalidInputParameter e1) {
	    _resetFetchedInfo(policy, driver);
	}
    }

    private void _reset(PolicyRequestData policy, InsuredDriverData driver) {
	_resetFetchedInfo(policy, driver);
	driver.setExpirienceClass(InsuredExpirienceClassEnum.UNSPECIFIED);
    }

    private void _resetFetchedInfo(PolicyRequestData policy, InsuredDriverData driver) {
	driver.setFetchedEntity(null);
	driver.setPersonalData(new PersonalData());
	driver.setResidenceData(new ResidenceData());
	driver.setOriginData(new OriginData());
	driver.setIdentityCardData(new IdentityCardData());
	driver.setTaxPayerNumber(null);
	driver.setContactData(new ContactData());
	driver.setInsuranceClassType(InsuranceClassTypeDict.UNSPECIFIED);
	driver.setAgeClass(InsuredAgeClassEnum.UNSPECIFIED);
    }

}
