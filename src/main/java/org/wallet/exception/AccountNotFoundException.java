package org.wallet.exception;

import java.util.Date;

public class AccountNotFoundException extends RuntimeException {

    private Integer statusCode;
    private String msg;
    private Date timeStamp;

    public AccountNotFoundException(String accountNumber) {
        super("Account not found for account number: " + accountNumber);
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
