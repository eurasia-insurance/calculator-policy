package kz.theeurasia.policy.calc.bean.facade;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.lapsa.insurance.domain.policy.Policy;
import com.lapsa.insurance.domain.policy.PolicyDriver;

import kz.theeurasia.policy.calc.api.DriverFacade;
import kz.theeurasia.policy.calc.api.MessagesBundleCode;
import kz.theeurasia.policy.calc.api.ValidationException;

@Named
@ApplicationScoped
public class DefaultDriverFacade implements DriverFacade {

    private static final long serialVersionUID = 1L;

    @Inject
    private com.lapsa.insurance.services.domain.PolicyDriverFacade facade;

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
    public void fetchInfo(Policy policy, PolicyDriver driver) throws ValidationException {
	facade.fetch(driver);
    }

    private void _reset(PolicyDriver driver) {
	facade.clearFetched(driver);
	driver.setExpirienceClass(null);
    }
}
