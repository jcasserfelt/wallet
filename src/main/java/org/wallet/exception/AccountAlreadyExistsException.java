package org.wallet.exception;

import java.util.Date;

public class AccountAlreadyExistsException extends RuntimeException {

    private Integer statusCode;
    private String msg;
    private Date timeStamp;

    public AccountAlreadyExistsException(String accountNumber) {
        super("Account already exists: " + accountNumber);
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }



}
