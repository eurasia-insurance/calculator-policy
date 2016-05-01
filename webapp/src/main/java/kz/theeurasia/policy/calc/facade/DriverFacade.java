package kz.theeurasia.policy.calc.facade;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.lapsa.insurance.elements.InsuranceClassType;
import com.lapsa.insurance.elements.InsuredAgeClass;

import kz.theeurasia.esbdproxy.domain.entities.general.SubjectPersonEntity;
import kz.theeurasia.esbdproxy.services.InvalidInputParameter;
import kz.theeurasia.esbdproxy.services.NotFound;
import kz.theeurasia.esbdproxy.services.elements.InsuranceClassTypeServiceDAO;
import kz.theeurasia.esbdproxy.services.general.SubjectPersonServiceDAO;
import kz.theeurasia.policy.calc.bean.CalculationData;
import kz.theeurasia.policy.domain.ContactData;
import kz.theeurasia.policy.domain.IdentityCardData;
import kz.theeurasia.policy.domain.InsuredDriverData;
import kz.theeurasia.policy.domain.OriginData;
import kz.theeurasia.policy.domain.PersonalData;
import kz.theeurasia.policy.domain.ResidenceData;

@Named
@ApplicationScoped
public class DriverFacade implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private SubjectPersonServiceDAO subjectPersonService;

    @Inject
    private InsuranceClassTypeServiceDAO insuranceClassTypeService;

    public InsuredDriverData add(CalculationData policy) throws ValidationException {
	if (policy.getInsuredDrivers().size() > 0 && policy.getInsuredVehicles().size() > 1)
	    throw new ValidationException(MessageBundleCode.ONLY_ONE_DRIVER_ALLOWED);
	InsuredDriverData e = new InsuredDriverData();
	policy.getInsuredDrivers().add(e);
	_reset(policy, e);
	return e;
    }

    public void remove(CalculationData policy, InsuredDriverData driver) throws ValidationException {
	if (policy.getInsuredDrivers().size() <= 1)
	    throw new ValidationException(MessageBundleCode.DRIVER_LIST_CANT_BE_EMPTY);
	policy.getInsuredDrivers().remove(driver);
    }

    public void fetchInfo(CalculationData policy, InsuredDriverData driver) throws ValidationException {
	try {
	    SubjectPersonEntity fetched = subjectPersonService.getByIIN(driver.getIdNumber());
	    driver.setFetched(true);

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
		driver.getResidenceData().setCity(fetched.getOrigin().getCity());
	    driver.getOriginData().setCountry(fetched.getOrigin().getCountry());

	    if (fetched.getIdentityCard().getDateOfIssue() != null)
		driver.getIdentityCardData().setDateOfIssue(fetched.getIdentityCard().getDateOfIssue().getTime());

	    driver.getIdentityCardData().setType(fetched.getIdentityCard().getIdentityCardType());
	    driver.getIdentityCardData().setIssuingAuthority(fetched.getIdentityCard().getIssuingAuthority());
	    driver.getIdentityCardData().setNumber(fetched.getIdentityCard().getNumber());

	    driver.setTaxPayerNumber(fetched.getTaxPayerNumber());

	    driver.getContactData().setEmail(fetched.getContact().getEmail());
	    driver.getContactData().setPhone(fetched.getContact().getPhone());
	    driver.getContactData().setSiteUrl(fetched.getContact().getSiteUrl());

	    try {
		InsuranceClassType insuranceClassType = insuranceClassTypeService.getForSubject(fetched);
		driver.setInsuranceClassType(insuranceClassType);
	    } catch (NotFound | InvalidInputParameter e) {
		driver.setInsuranceClassType(insuranceClassTypeService.getDefault());
	    }
	    if (fetched.getPersonal().getDayOfBirth() != null) {
		int years = CalculatorUtil.calculateAgeByDOB(driver.getPersonalData().getDayOfBirth());
		driver.setAgeClass(years > 25 ? InsuredAgeClass.OVER25 : InsuredAgeClass.UNDER25);
	    }

	} catch (NotFound e) {
	    _resetFetchedInfo(policy, driver);
	    driver.setInsuranceClassType(insuranceClassTypeService.getDefault());
	} catch (InvalidInputParameter e1) {
	    _resetFetchedInfo(policy, driver);
	}
    }

    private void _reset(CalculationData policy, InsuredDriverData driver) {
	_resetFetchedInfo(policy, driver);
	driver.setExpirienceClass(null);
    }

    private void _resetFetchedInfo(CalculationData policy, InsuredDriverData driver) {
	driver.setFetched(false);
	driver.setPersonalData(new PersonalData());
	driver.setResidenceData(new ResidenceData());
	driver.setOriginData(new OriginData());
	driver.setIdentityCardData(new IdentityCardData());
	driver.setTaxPayerNumber(null);
	driver.setContactData(new ContactData());
	driver.setInsuranceClassType(null);
	driver.setAgeClass(null);
    }

}
