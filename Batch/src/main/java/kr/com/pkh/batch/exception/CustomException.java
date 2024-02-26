package kr.com.pkh.batch.exception;

import kr.com.pkh.batch.code.CustomErrorCode;
import lombok.Data;

// @Data
public class CustomException extends RuntimeException{

    private CustomErrorCode customErrorCode;
    private String detailMessage;

    public CustomException(CustomErrorCode customErrorCode){
        super(customErrorCode.getDescription());
        this.customErrorCode = customErrorCode;
        this.detailMessage = customErrorCode.getDescription();
    }

    public CustomErrorCode getCustomErrorCode(){
        return this.customErrorCode;
    }

    public String getDetailMessage(){
        return this.detailMessage;
    }

}
