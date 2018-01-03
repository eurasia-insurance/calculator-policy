package kz.theeurasia.policy.calc.bean.dataBuilder;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Default;
import javax.faces.FacesException;
import javax.faces.application.ProjectStage;
import javax.inject.Inject;

import com.lapsa.insurance.domain.InsurancePeriodData;
import com.lapsa.insurance.domain.policy.Policy;

import kz.theeurasia.policy.calc.api.DefaultCalculationDataBuilder;
import kz.theeurasia.policy.calc.api.DriverFacade;
import kz.theeurasia.policy.calc.api.ValidationException;
import kz.theeurasia.policy.calc.api.VehicleFacade;

@RequestScoped
@ProjectStageDepend(stage = ProjectStage.Production)
@Default
public class ProductionDataBuilder implements DefaultCalculationDataBuilder {

    @Inject
    private DriverFacade driverFacade;

    @Inject
    private VehicleFacade vehicleFacade;

    @Override
    public void buildDefaultData(final Policy policy) {
	try {
	    driverFacade.add(policy);
	    vehicleFacade.add(policy);
	    policy.setPeriod(new InsurancePeriodData());
	} catch (ValidationException e) {
	    throw new FacesException(e);
	}
    }

}
