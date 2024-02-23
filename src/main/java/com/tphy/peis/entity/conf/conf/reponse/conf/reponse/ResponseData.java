package com.tphy.peis.entity.conf.conf.reponse.conf.reponse;



public class ResponseData {
    public static final String DEFAULT_SUCCESS_MESSAGE = "请求成功";
    public static final Integer DEFAULT_SUCCESS_CODE = 200;
    public static final String DEFAULT_NO_LOGIN = "未登录";
    public static final Integer DEFAULT_NO_LOGIN_CODE = 401;
    public static final String DEFAULT_ERROR_MESSAGE = "请求异常";
    public static final Integer DEFAULT_ERROR_CODE = 500;
    public static final String DEFAULT_NO_PERMISSION = "无权访问";
    public static final Integer DEFAULT_NO_PERMISSION_CODE = 403;

    private Boolean success;
    private Integer code;
    private String message;
    private Object data;

    public ResponseData() {
    }

    public ResponseData(Boolean success, Integer code, String message, Object data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseData{" +
                "success=" + success +
                ", code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
