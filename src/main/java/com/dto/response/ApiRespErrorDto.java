package com.dto.response;


import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

public class ApiRespErrorDto {

	private HttpStatus httpstatus;
    private String msg;
    private List<String> error;
    
    public ApiRespErrorDto(HttpStatus status, String message, List<String> error) {
        this.httpstatus = httpstatus;
        this.msg = msg;
        this.error = error;
    }
    
    public ApiRespErrorDto(HttpStatus httpstatus, String msg, String error) {
        this(httpstatus, msg, Arrays.asList(error));
    }
    
    public HttpStatus getHttpStatus() {
        return httpstatus;
    }
    
    public void setHttpStatus(HttpStatus httpstatus) {
        this.httpstatus = httpstatus;
    }
    
    public String getMsg() {
        return msg;
    }
    
    public void setMsg(String msg) {
        this.msg = msg;
    }
    
    public List<String> getError() {
        return error;
    }
    
    public void setError(List<String> error) {
        this.error = error;
    }
}

