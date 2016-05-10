package kz.theeurasia.policy.calc.bean.impl;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Default;
import javax.faces.application.ProjectStage;
import javax.inject.Inject;

import kz.theeurasia.policy.calc.bean.Calculation;
import kz.theeurasia.policy.calc.bean.DefaultCalculationDataBuilder;
import kz.theeurasia.policy.calc.bean.ProjectStageDepend;
import kz.theeurasia.policy.calc.facade.DriverFacade;
import kz.theeurasia.policy.calc.facade.ValidationException;
import kz.theeurasia.policy.calc.facade.VehicleFacade;

@RequestScoped
@ProjectStageDepend(stage = ProjectStage.Production)
@Default
public class ProductionDataBuilder implements DefaultCalculationDataBuilder {

    @Inject
    private DriverFacade driverFacade;

    @Inject
    private VehicleFacade vehicleFacade;

    @Inject
    private Logger logger;

    @Override
    public void buildDefaultData(Calculation calculation) {
	try {
	    driverFacade.add(calculation);
	    vehicleFacade.add(calculation);
	} catch (ValidationException e) {
	    logger.log(Level.SEVERE, e.getMessage(), e);
	}
    }

}
