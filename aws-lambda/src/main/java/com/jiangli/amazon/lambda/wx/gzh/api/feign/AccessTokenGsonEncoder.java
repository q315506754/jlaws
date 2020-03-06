package com.jiangli.amazon.lambda.wx.gzh.api.feign;

import com.jiangli.amazon.lambda.wx.gzh.api.common.CachedTokenService;
import feign.RequestTemplate;
import feign.gson.GsonEncoder;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Jiangli
 * @date 2020/3/4 18:59
 */
public class AccessTokenGsonEncoder extends GsonEncoder {
    public static String cachedToken = null;

    @Override
    public void encode(Object object, Type bodyType, RequestTemplate template) {
        //拼字符串参数
        Map<String, String> describe = new HashMap<String, String>();
        if (object instanceof Map) {
            describe = (Map) object;
        }

        //特殊要求
        if (cachedToken == null) {
            cachedToken = CachedTokenService.getToken();
        }
        describe.put("access_token",  cachedToken);

        String param = "";
        for (Map.Entry<String, String> stringEntry : describe.entrySet()) {
            param = param + stringEntry.getKey() + "=" + stringEntry.getValue() + "&";
        }

        //template.body("userId=232&timeStamp=343&token=dsf");
        template.body(param);
        //super.encode(object, bodyType, template);
        //System.out.println(template);
    }
}
