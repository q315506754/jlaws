package com.jiangli.amazon.lambda.wx.gzh.dto;

import lombok.Data;

/**
 * @author Jiangli
 * @date 2020/3/4 14:08
 */
@Data
public class WxRequestParams {
    private String signature;
    private String timestamp;
    private String nonce;
    private String echostr;
    private String openid;
}
