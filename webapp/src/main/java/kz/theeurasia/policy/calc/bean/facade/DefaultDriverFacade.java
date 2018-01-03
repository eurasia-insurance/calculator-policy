package kz.theeurasia.policy.calc.bean.facade;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.FacesException;
import javax.inject.Named;

import com.lapsa.insurance.domain.policy.Policy;
import com.lapsa.insurance.domain.policy.PolicyDriver;

import kz.theeurasia.policy.calc.api.DriverFacade;
import kz.theeurasia.policy.calc.api.MessagesBundleCode;
import kz.theeurasia.policy.calc.api.ValidationException;
import tech.lapsa.insurance.facade.PolicyDriverFacade.PolicyDriverFacadeRemote;
import tech.lapsa.java.commons.exceptions.IllegalArgument;

@Named
@RequestScoped
public class DefaultDriverFacade implements DriverFacade {

    private static final long serialVersionUID = 1L;

    @EJB
    private PolicyDriverFacadeRemote facade;

    @Override
    public PolicyDriver add(Policy policy) throws ValidationException {
	if (policy.getInsuredDrivers().size() > 0 && policy.getInsuredVehicles().size() > 1)
	    throw new ValidationException(MessagesBundleCode.ONLY_ONE_DRIVER_ALLOWED);
	PolicyDriver e = new PolicyDriver();
	policy.getInsuredDrivers().add(e);
	_reset(e);
	return e;
    }

    @Override
    public void remove(Policy policy, PolicyDriver driver) throws ValidationException {
	if (policy.getInsuredDrivers().size() <= 1)
	    throw new ValidationException(MessagesBundleCode.DRIVER_LIST_CANT_BE_EMPTY);
	policy.getInsuredDrivers().remove(driver);
    }

    @Override
    public void fetchInfo(Policy policy, PolicyDriver driver) {
	try {
	    PolicyDriver fetched = facade.getByTaxpayerNumberOrDefault(driver.getIdNumber());
	    driver.setFetched(fetched.isFetched());
	    driver.setInsuranceClassType(fetched.getInsuranceClassType());
	    driver.setAgeClass(fetched.getAgeClass());
	    driver.setPersonalData(fetched.getPersonalData());
	    driver.setResidenceData(fetched.getResidenceData());
	    driver.setOriginData(fetched.getOriginData());
	    driver.setIdentityCardData(fetched.getIdentityCardData());
	    driver.setTaxPayerNumber(fetched.getTaxPayerNumber());
	    driver.setContactData(fetched.getContactData());
	} catch (IllegalArgument e) {
	    throw new FacesException(e);
	}
    }

    private void _reset(PolicyDriver driver) {
	driver.setFetched(false);
	driver.setInsuranceClassType(null);
	driver.setAgeClass(null);
	driver.setPersonalData(null);
	driver.setResidenceData(null);
	driver.setOriginData(null);
	driver.setIdentityCardData(null);
	driver.setTaxPayerNumber(null);
	driver.setContactData(null);
	driver.setExpirienceClass(null);
    }
}
