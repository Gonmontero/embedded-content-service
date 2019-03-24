package com.embed.exceptions;

import com.embed.exceptions.errors.ErrorCode;

public class ApplicationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private ErrorCode errorCode;

    private String message;


    /**
     * Default Constructor.
     * @param errorCode the errorCode
     * @param message the error message.
     */
    public ApplicationException(ErrorCode errorCode, String message) {
        super();
        this.errorCode = errorCode;
        this.message = message;
    }


    /**
     * Default Constructor.
     * @param errorCode the errorCode
     */
    public ApplicationException(ErrorCode errorCode) {
        super();
        this.errorCode = errorCode;
        this.message = errorCode.getDescription();
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}