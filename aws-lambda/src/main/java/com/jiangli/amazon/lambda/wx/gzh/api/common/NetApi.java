package com.jiangli.amazon.lambda.wx.gzh.api.common;

import com.jiangli.amazon.lambda.wx.gzh.api.common.dto.Getcallbackip;
import feign.Param;
import feign.RequestLine;

/**
 * @author Jiangli
 * @date 2020/3/4 18:45
 */
public interface NetApi {
    @RequestLine("GET /cgi-bin/getcallbackip")
    Getcallbackip getcallbackip(@Param("access_token") String access_token);

    @RequestLine("GET /cgi-bin/get_api_domain_ip")
    Getcallbackip get_api_domain_ip(@Param("access_token") String access_token);
}
