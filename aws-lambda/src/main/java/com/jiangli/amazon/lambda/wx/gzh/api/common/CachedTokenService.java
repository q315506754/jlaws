package com.jiangli.amazon.lambda.wx.gzh.api.common;

import com.jiangli.amazon.lambda.wx.gzh.api.common.dto.AccessToken;
import com.jiangli.amazon.lambda.wx.gzh.config.WeixinConfig;

/**
 * @author Jiangli
 * @date 2020/3/4 19:17
 */
public class CachedTokenService {
    public static String getToken() {
        AuthApi github = ApiHelper.api(AuthApi.class, AuthApi.API_DOMAIN);

        AccessToken token = github.token(AuthApi.grant_type, WeixinConfig.appId, WeixinConfig.token2);
        String access_token = token.getAccess_token();
        if (access_token == null) {
            System.err.println("[error]while get token"+token);
        } else {

        }
        return access_token;
    }
}
