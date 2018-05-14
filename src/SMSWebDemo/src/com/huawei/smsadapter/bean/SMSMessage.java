
package com.huawei.smsadapter.bean;

public class SMSMessage
{
    /**
     * 短信内容
     */
    private String message;
   
    /**
     * 发送方地址
     */
    private String senderAddress;
  
    /**
     * 接收方地址
     */
    private String receiverAddress;
 

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }
    
    public String getSenderAddress()
    {
        return senderAddress;
    }

    public void setSenderAddress(String senderAddress)
    {
        this.senderAddress = senderAddress;
    }

    public String getReceiverAddress()
    {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress)
    {
        this.receiverAddress = receiverAddress;
    }
}
