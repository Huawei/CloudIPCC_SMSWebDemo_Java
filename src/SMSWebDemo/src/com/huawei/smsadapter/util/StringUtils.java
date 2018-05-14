
package com.huawei.smsadapter.util;


public abstract class StringUtils
{
    
    /**
     * 判断字符串是否为null或者为空字符串（含空格）。
     * Determine whether the string is null or empty string (including spaces).
     * @param str 字符串变量(String Input)
     * @return true/false
     */
    public static boolean isNullOrBlank(String str)
    {
        return str == null || str.trim().isEmpty();
    }
    
    
}
