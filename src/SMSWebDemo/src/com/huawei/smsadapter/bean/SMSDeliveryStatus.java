
package com.huawei.smsadapter.bean;

public class SMSDeliveryStatus
{
    /**
     * 是否发送成功
     */
    private boolean isSuccess;
    
    /**
     * 失败原因码
     */
    private String errorCode;
    
    
    /**
     * 短信编号
     */
    private String serialNo;

    public String getSerialNo()
    {
        return serialNo;
    }

    public void setSerialNo(String serialNo)
    {
        this.serialNo = serialNo;
    }

    public boolean isSuccess()
    {
        return isSuccess;
    }

    public void setSuccess(boolean isSuccess)
    {
        this.isSuccess = isSuccess;
    }

    public String getErrorCode()
    {
        return errorCode;
    }

    public void setErrorCode(String errorCode)
    {
        this.errorCode = errorCode;
    }
    
}
