package com.huawei.smsadapter.rest;


import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.huawei.smsadapter.bean.SMSDeliveryStatus;
import com.huawei.smsadapter.bean.SMSMessage;
import com.huawei.smsadapter.bean.SMSMessageEx;
import com.huawei.smsadapter.data.MessageData;
import com.huawei.smsadapter.util.JsonUtil;
import com.huawei.smsadapter.util.StringUtils;

@Path("/sms")
public class SendSMSRest
{
    private static final Logger LOG = LoggerFactory.getLogger(SendSMSRest.class);
    
    private static final Queue<String> MESSAGE_QUEUE = new ConcurrentLinkedQueue<String>();
    
    
    /**
     * 接收smsadapter侧的短信
     * @param request
     * @return
     */
    @POST
    @Path("/sendfromadapter")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> sendformadapter(@Context HttpServletRequest request)
    {
        String message = request.getParameter("message");
        SMSMessageEx sms = JsonUtil.getBean(message, SMSMessageEx.class);
        if (sms == null)
        {
            LOG.error("convert failed. sms is null");
        }
        else
        {
            Queue<SMSMessageEx> queue = MessageData.MESSAGE_DATA.get(sms.getReceiverAddress());
            if (null == queue)
            {
                queue = new ConcurrentLinkedQueue<SMSMessageEx>();
                MessageData.MESSAGE_DATA.put(sms.getReceiverAddress(), queue);
            }
            queue.add(sms);
        }
        return new HashMap<String, Object>();
    }
    
    /**
     * smsadapter接收用户发送的短信
     * @param request
     * @return
     */
    @GET
    @Path("/readsms")
    @Produces(MediaType.APPLICATION_JSON)
    public String readsms(@Context HttpServletRequest request)
    {
        String message = MESSAGE_QUEUE.poll();
        if (null != message)
        {
            LOG.info("readsms from sms. message {}", message);
        }
        return message;
        
    }
    
    
    /**
     * 用户侧发送短信Rest接口
     * @param request
     * @return
     */
	@POST
    @Path("/send")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> send(@Context HttpServletRequest request)
    {
        SMSMessage msg = new SMSMessage();
        msg.setMessage(request.getParameter("message"));
        msg.setSenderAddress(request.getParameter("senderAddress"));
        msg.setReceiverAddress(request.getParameter("smsServerActiveNum"));
        String text = JsonUtil.getJsonString(msg);
        LOG.info("send from user. message {}", text);
        MESSAGE_QUEUE.add(text);
        return new HashMap<String, Object>();
    }
    
    /**
     * 用户侧接收短信Rest接口
     * @param request
     * @return
     */
    @POST
    @Path("/query")
    @Produces(MediaType.APPLICATION_JSON)
    public SMSMessageEx query(@Context HttpServletRequest request)
    {
        String smsServerActiveNum = request.getParameter("destinationAddresses");
        try
        {
            Queue<SMSMessageEx> queue = MessageData.MESSAGE_DATA.get(smsServerActiveNum);
            if (queue != null)
            {
                SMSMessageEx ex =  queue.poll();
                if (ex == null)
                {
                    return null;
                }
                if (!StringUtils.isNullOrBlank(ex.getId()))
                {
                    //需要回执
                    SMSDeliveryStatus status = new SMSDeliveryStatus();
                    status.setSerialNo(ex.getId());
                    status.setSuccess(true);
                    MESSAGE_QUEUE.add(JsonUtil.getJsonString(status));
                }
                LOG.info("get from adapter. message {}", JsonUtil.getJsonString(ex));
                return ex;
            }
            return null;
        }
        catch (Exception e)
        {
            LOG.error("{} unkown exception {}",  smsServerActiveNum, e.getMessage());
            return null;
        }
    }
}