package kz.theeurasia.policy.calc.api;

import com.lapsa.insurance.domain.policy.PolicyDriver;
import com.lapsa.insurance.domain.policy.PolicyVehicle;

public interface ActionFacade {

    String doInitialize();

    void addInsuredDriver();

    void removeInsuredDriver(PolicyDriver driver);

    void addInsuredVehicle();

    void removeInsuredVehicle(PolicyVehicle insuredVehicle);

    void doCalculatePolicyCost();

    void onDriverIdNumberChanged(PolicyDriver insuredDriver);

    void onPolicyCostCalculationFormChanged();

    void onVehicleTemporaryEntryChanged(PolicyVehicle vehicle);

    void onVehicleRegionChanged(PolicyVehicle insuredVehicle);

    void onVehicleCityChanged(PolicyVehicle vehicle);

    String doPeriodYear();

    String doPeriodMonth(int monthsCount);

}
