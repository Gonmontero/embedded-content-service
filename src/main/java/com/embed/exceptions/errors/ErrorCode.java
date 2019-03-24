package com.embed.exceptions.errors;

public enum ErrorCode {

    PROVIDER_NOT_FOUND ("0001", "Provider not found", 404),

    FIELD_VALIDATION_ERROR ("0002", "Field validation error", 400);

    private String code;
    private String description;
    private int httpStatusCode;


    private ErrorCode(String code, String description, int httpStatusCode) {
        this.code = code;
        this.description = description;
        this.httpStatusCode = httpStatusCode;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }


    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(int httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }
}
