package com.jiangli.amazon.lambda.wx.gzh.helper;

/**
 * @author Jiangli
 * @date 2020/3/5 18:31
 */
public class StringUtils {

    public static boolean isNotEmpty(String attrValue) {
        return attrValue!=null && attrValue.trim().length()>0;
    }
}
