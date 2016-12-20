package kz.theeurasia.policy.calc.bean.facade;

public enum GlobalMessageBundleCode {

    MESSAGES_SERVER_FATAL_ERROR_DESCRIPTION("messages.server_fatal_error.description"),
    MESSAGES_SERVER_FATAL_ERROR_CAPTION("messages.server_fatal_error.caption"),
    LABEL_SI_SEX_PREFIX("label.si.sex."),
    LABEL_SI_COUNTRY_REGION_PREFIX("label.si.country-region."),
    LABEL_SI_IDENTITY_CARD_TYPE_PREFIX("label.si.identity-card-type.");

    public static final String BUNDLE_BASE_NAME = "GlobalMessages";

    private final String messageBundleCode;

    GlobalMessageBundleCode(String messageBundleCode) {
	this.messageBundleCode = messageBundleCode;
    }

    public String getMessageBundleCode() {
	return messageBundleCode;
    }
}
