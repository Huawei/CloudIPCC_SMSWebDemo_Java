
package com.huawei.smsadapter.data;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

import com.huawei.smsadapter.bean.SMSMessageEx;

public class MessageData
{
    public static final Map<String, Queue<SMSMessageEx>> MESSAGE_DATA = new HashMap<String, Queue<SMSMessageEx>>();
}
