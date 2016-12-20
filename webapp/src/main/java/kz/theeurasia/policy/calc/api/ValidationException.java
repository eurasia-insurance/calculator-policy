package kz.theeurasia.policy.calc.api;

import kz.theeurasia.policy.calc.facade.MessageBundleCode;

public class ValidationException extends Exception {

    private static final long serialVersionUID = 1L;

    private final MessageBundleCode messageCode;
    private final MessageBundleCode descriptionCode;

    public ValidationException(MessageBundleCode code) {
	super();
	this.messageCode = code;
	this.descriptionCode = code;
    }

    public ValidationException(MessageBundleCode messageCode, MessageBundleCode descriptionCode) {
	super();
	this.messageCode = messageCode;
	this.descriptionCode = descriptionCode;
    }

    public ValidationException(MessageBundleCode messageCode, MessageBundleCode descriptionCode, String message,
	    Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
	super(message, cause, enableSuppression, writableStackTrace);
	this.messageCode = messageCode;
	this.descriptionCode = descriptionCode;
    }

    public ValidationException(MessageBundleCode code, String message, Throwable cause, boolean enableSuppression,
	    boolean writableStackTrace) {
	super(message, cause, enableSuppression, writableStackTrace);
	this.messageCode = code;
	this.descriptionCode = code;
    }

    public ValidationException(MessageBundleCode messageCode, MessageBundleCode descriptionCode, String message,
	    Throwable cause) {
	super(message, cause);
	this.messageCode = messageCode;
	this.descriptionCode = descriptionCode;
    }

    public ValidationException(MessageBundleCode code, String message, Throwable cause) {
	super(message, cause);
	this.messageCode = code;
	this.descriptionCode = code;
    }

    public ValidationException(MessageBundleCode code, String message) {
	super(message);
	this.messageCode = code;
	this.descriptionCode = code;
    }

    public ValidationException(MessageBundleCode code, Throwable cause) {
	super(cause);
	this.messageCode = code;
	this.descriptionCode = code;
    }

    public MessageBundleCode getMessageCode() {
	return messageCode;
    }

    public MessageBundleCode getDescriptionCode() {
	return descriptionCode;
    }
}
