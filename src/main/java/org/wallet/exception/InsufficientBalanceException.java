package org.wallet.exception;

import java.util.Date;

public class InsufficientBalanceException extends RuntimeException{

    private Integer statusCode;
    private String msg;
    private Date timeStamp;

    public InsufficientBalanceException(String accountNumber){
        super("Insufficient balance in source account: " + accountNumber);
    }


}