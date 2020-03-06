package com.jiangli.amazon.lambda.wx.gzh.api.common;

import com.jiangli.amazon.lambda.wx.gzh.api.common.dto.AccessToken;
import feign.Param;
import feign.RequestLine;

/**
 * @author Jiangli
 * @date 2020/3/4 18:45
 */
public interface AuthApi {
    String API_DOMAIN = "https://api.weixin.qq.com";
    String grant_type = "client_credential";

    //获取access_token填写client_credential
    @RequestLine("GET /cgi-bin/token")
    AccessToken token(
            @Param("grant_type") String grant_type
            , @Param("appid") String appid
            , @Param("secret") String secret
    );
}
