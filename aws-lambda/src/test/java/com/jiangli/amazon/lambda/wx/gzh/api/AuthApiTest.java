package com.jiangli.amazon.lambda.wx.gzh.api;

import com.jiangli.amazon.lambda.wx.BaseTest;
import com.jiangli.amazon.lambda.wx.gzh.api.common.ApiHelper;
import com.jiangli.amazon.lambda.wx.gzh.api.common.AuthApi;
import com.jiangli.amazon.lambda.wx.gzh.api.common.NetApi;
import com.jiangli.amazon.lambda.wx.gzh.api.common.dto.AccessToken;
import com.jiangli.amazon.lambda.wx.gzh.config.WeixinConfig;
import org.junit.Test;

/**
 * @author Jiangli
 * @date 2020/3/4 18:50
 */
public class AuthApiTest extends BaseTest {

    @Test
    public void token() {
        AuthApi github = ApiHelper.api(AuthApi.class, AuthApi.API_DOMAIN);

        AccessToken token = github.token(AuthApi.grant_type, WeixinConfig.appId, WeixinConfig.token2);
        System.out.println(token.getErrcode());
        System.out.println(token);

    }

    @Test
    public void getcallbackip() {
        NetApi github = ApiHelper.access_token_api(NetApi.class, AuthApi.API_DOMAIN);
        System.out.println(github.getcallbackip(null));
        System.out.println(github.get_api_domain_ip(null));
    }


}