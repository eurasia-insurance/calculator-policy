package kz.theeurasia.policy.calc.facade;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.lapsa.insurance.domain.ContactData;
import com.lapsa.insurance.domain.IdentityCardData;
import com.lapsa.insurance.domain.OriginData;
import com.lapsa.insurance.domain.PersonalData;
import com.lapsa.insurance.domain.ResidenceData;
import com.lapsa.insurance.domain.policy.Policy;
import com.lapsa.insurance.domain.policy.PolicyDriver;
import com.lapsa.insurance.elements.InsuranceClassType;
import com.lapsa.insurance.elements.InsuredAgeClass;
import com.lapsa.insurance.esbd.domain.entities.general.SubjectPersonEntity;
import com.lapsa.insurance.esbd.services.InvalidInputParameter;
import com.lapsa.insurance.esbd.services.NotFound;
import com.lapsa.insurance.esbd.services.elements.InsuranceClassTypeServiceDAO;
import com.lapsa.insurance.esbd.services.general.SubjectPersonServiceDAO;

import kz.theeurasia.policy.calc.api.DriverFacade;
import kz.theeurasia.policy.calc.api.MessageBundleCode;
import kz.theeurasia.policy.calc.api.ValidationException;

@Named
@ApplicationScoped
public class DefaultDriverFacade implements DriverFacade {

    private static final long serialVersionUID = 1L;

    @Inject
    private SubjectPersonServiceDAO subjectPersonService;

    @Inject
    private InsuranceClassTypeServiceDAO insuranceClassTypeService;

    @Override
    public PolicyDriver add(Policy policy) throws ValidationException {
	if (policy.getInsuredDrivers().size() > 0 && policy.getInsuredVehicles().size() > 1)
	    throw new ValidationException(MessageBundleCode.ONLY_ONE_DRIVER_ALLOWED);
	PolicyDriver e = new PolicyDriver();
	policy.getInsuredDrivers().add(e);
	_reset(policy, e);
	return e;
    }

    @Override
    public void remove(Policy policy, PolicyDriver driver) throws ValidationException {
	if (policy.getInsuredDrivers().size() <= 1)
	    throw new ValidationException(MessageBundleCode.DRIVER_LIST_CANT_BE_EMPTY);
	policy.getInsuredDrivers().remove(driver);
    }

    @Override
    public void fetchInfo(Policy policy, PolicyDriver driver) throws ValidationException {
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

    private void _reset(Policy policy, PolicyDriver driver) {
	_resetFetchedInfo(policy, driver);
	driver.setExpirienceClass(null);
    }

    private void _resetFetchedInfo(Policy policy, PolicyDriver driver) {
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
