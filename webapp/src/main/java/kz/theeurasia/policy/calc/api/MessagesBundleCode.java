package kz.theeurasia.policy.calc.api;

public enum MessagesBundleCode {

    DRIVER_LIST_CANT_BE_EMPTY("messages.driver-list.cant-be-empty"),
    VEHICLES_LIST_CANT_BE_EMPTY("messages.vehicle-list.cant-be-empty"),
    ONLY_ONE_DRIVER_ALLOWED("messages.driver-list.only-one-driver-allowed"),
    ONLY_ONE_VEHICLE_ALLOWED("messages.vehicle-list.only-one-vehicle-allowed");

    public static final String BUNDLE_BASE_NAME = "Messages";

    private final String messageBundleCode;

    MessagesBundleCode(String messageBundleCode) {
	this.messageBundleCode = messageBundleCode;
    }

    public String getMessageBundleCode() {
	return messageBundleCode;
    }
}
