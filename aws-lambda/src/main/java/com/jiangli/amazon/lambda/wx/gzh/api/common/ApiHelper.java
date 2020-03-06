package com.jiangli.amazon.lambda.wx.gzh.api.common;

import com.jiangli.amazon.lambda.wx.gzh.api.feign.AccessTokenGsonEncoder;
import com.jiangli.amazon.lambda.wx.gzh.api.feign.MapToParamGsonEncoder;
import com.jiangli.amazon.lambda.wx.gzh.api.feign.MyGsonDecoder;
import feign.Feign;
import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * @author Jiangli
 * @date 2020/3/4 19:21
 */
public class ApiHelper {
    public static <T> T api(Class<T> cls,String baseUrl) {
        T github = Feign.builder()
                .encoder(new MapToParamGsonEncoder())
                .decoder(new MyGsonDecoder())
                .requestInterceptor(new RequestInterceptor() {
                    @Override
                    public void apply(RequestTemplate template) {
                        //System.out.println(template);
                    }
                })
                .target(cls, baseUrl);
        return github;
    }
    public static <T> T access_token_api(Class<T> cls,String baseUrl) {
        T github = Feign.builder()
                .encoder(new AccessTokenGsonEncoder())
                .decoder(new MyGsonDecoder())
                .requestInterceptor(new RequestInterceptor() {
                    @Override
                    public void apply(RequestTemplate template) {
                        //System.out.println(template);
                    }
                })
                .target(cls, baseUrl);
        return github;
    }
}
