package kz.theeurasia.policy.calc.api;

public class ValidationException extends Exception {

    private static final long serialVersionUID = 1L;

    private final MessagesBundleCode messageCode;
    private final MessagesBundleCode descriptionCode;

    public ValidationException(MessagesBundleCode code) {
	super();
	this.messageCode = code;
	this.descriptionCode = code;
    }

    public ValidationException(MessagesBundleCode messageCode, MessagesBundleCode descriptionCode) {
	super();
	this.messageCode = messageCode;
	this.descriptionCode = descriptionCode;
    }

    public ValidationException(MessagesBundleCode messageCode, MessagesBundleCode descriptionCode, String message,
	    Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
	super(message, cause, enableSuppression, writableStackTrace);
	this.messageCode = messageCode;
	this.descriptionCode = descriptionCode;
    }

    public ValidationException(MessagesBundleCode code, String message, Throwable cause, boolean enableSuppression,
	    boolean writableStackTrace) {
	super(message, cause, enableSuppression, writableStackTrace);
	this.messageCode = code;
	this.descriptionCode = code;
    }

    public ValidationException(MessagesBundleCode messageCode, MessagesBundleCode descriptionCode, String message,
	    Throwable cause) {
	super(message, cause);
	this.messageCode = messageCode;
	this.descriptionCode = descriptionCode;
    }

    public ValidationException(MessagesBundleCode code, String message, Throwable cause) {
	super(message, cause);
	this.messageCode = code;
	this.descriptionCode = code;
    }

    public ValidationException(MessagesBundleCode code, String message) {
	super(message);
	this.messageCode = code;
	this.descriptionCode = code;
    }

    public ValidationException(MessagesBundleCode code, Throwable cause) {
	super(cause);
	this.messageCode = code;
	this.descriptionCode = code;
    }

    public MessagesBundleCode getMessageCode() {
	return messageCode;
    }

    public MessagesBundleCode getDescriptionCode() {
	return descriptionCode;
    }
}
