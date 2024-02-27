package com.tphy.peis.service.conf.conf.reponse.conf.reponse;


public class ErrorResponseData extends ResponseData {
    public ErrorResponseData() {
        super(false, DEFAULT_ERROR_CODE, DEFAULT_ERROR_MESSAGE, null);

    }

    public ErrorResponseData(String message) {
        super(false, DEFAULT_ERROR_CODE, message, null);
    }

    public ErrorResponseData(Integer code, String message) {
        super(false, code, message, null);
    }

    public ErrorResponseData(Integer code, String message, Object object) {
        super(false, code, message, object);
    }
}