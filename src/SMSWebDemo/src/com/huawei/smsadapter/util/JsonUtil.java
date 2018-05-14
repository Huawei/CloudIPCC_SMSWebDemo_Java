package com.huawei.smsadapter.util;



import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public abstract class JsonUtil
{
    /**
     * 记录日志的Logger对象。
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);
    
    private static ObjectMapper objectMapper = new ObjectMapper();
   
    
    /**
     * getBean时， 忽略多余字段。
     */
    static
    {
        objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
    
     
    
    /**     
     * 转化bean为json字符串   
     * @param o 对象  
     * @return bean json字符串
     */    
    public static String getJsonString(Object o) 
    {   
        String jsonStr = null;
        JsonGenerator jsonGenerator = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
        try
        {
            jsonGenerator = objectMapper.getJsonFactory().createJsonGenerator(baos, JsonEncoding.UTF8);
            jsonGenerator.writeObject(o);
            jsonStr = baos.toString("UTF-8");
        }
        catch (IOException e)
        {
            LOGGER.error("getJsonString fail, error message is {}." +  e.getMessage());
        }  
        finally
        {
            try
            {
                baos.close();
            }
            catch (IOException e)
            {
                LOGGER.error("getJsonString fail, error message is {}." + e.getMessage());
            }
            try 
            {
                if (jsonGenerator != null)
                {
                    jsonGenerator.close();
                }
            }
            catch (IOException e)
            {
                LOGGER.error(e.getMessage());
            }
        }
        return jsonStr;
    }
    
    /**
     * 从json字符串获取bean
     * @param <T> 类型
     * @param json json字符串
     * @param beanClass bean的class类
     * @return bean
     */
    public static <T> T getBean(String json, Class<T> beanClass)
    {
        T bean = null;
        
        if (StringUtils.isNullOrBlank(json))
        {
            return null;
        }
        
        try
        {
            
            bean = objectMapper.readValue(json, beanClass);
        }
        catch (IOException e)
        {
            LOGGER.error("getBean beanClass fail, error message is {}.", 
                    beanClass.getClass().getCanonicalName(),
                    e.getMessage());
        }
        return bean;
    }
}
