package esmocyp.core.api.service.exception;

public class ReasoningServiceException extends Exception {

    public ReasoningServiceException() {
        super();
    }

    public ReasoningServiceException(String message) {
        super(message);
    }

    public ReasoningServiceException(Throwable throwable) {
        super(throwable);
    }

    public ReasoningServiceException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
