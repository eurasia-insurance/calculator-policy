package kz.theeurasia.policy.calc.facade;

public enum MessageBundleCode {

    DRIVER_LIST_CANT_BE_EMPTY("messages.driver-list.cant-be-empty"),
    VEHICLES_LIST_CANT_BE_EMPTY("messages.vehicle-list.cant-be-empty"),
    ONLY_ONE_DRIVER_ALLOWED("messages.driver-list.only-one-driver-allowed"),
    ONLY_ONE_VEHICLE_ALLOWED("messages.vehicle-list.only-one-vehicle-allowed"),
    // ID_NUMBER_CANT_BE_EMPTY("messages.insured-driver.id-number.cant-be-empty"),
    // VIN_CODE_CANT_BE_EMPTY("messages.insured-vehicle.vin-code.cant-be-empty"),
    CANT_CALCULATE_PREMIUM_COST("messages.policy.cant-calculate-premum.message"),
    CANT_CALCULATE_PREMIUM_COST_DESCRIPTION("messages.policy.cant-calculate-premum.description"),
    LABEL_INSURED_AGE_CLASS_PREFIX("label.si.insured-age-class."),
    LABEL_INSURED_EXPIRIENCE_CLASS_PREFIX("label.si.insured-expirience-class."),
    LABEL_VEHICLE_AGE_CLASS_PREFIX("label.si.vehicle-age-class."),
    LABEL_VEHICLE_CLASS_PREFIX("label.si.vehicle-class.");

    public static final String BUNDLE_BASE_NAME = "OSGPOVTSMessages";

    private final String messageBundleCode;

    MessageBundleCode(String messageBundleCode) {
	this.messageBundleCode = messageBundleCode;
    }

    public String getMessageBundleCode() {
	return messageBundleCode;
    }
}
