package com.jiangli.amazon.lambda.wx.gzh;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import com.jiangli.amazon.lambda.wx.gzh.dto.WxRequestParams;

import java.util.HashMap;
import java.util.Map;

/**

 URL https://k9noqpg5pa.execute-api.us-east-2.amazonaws.com/prod/config
 Token 123321123321123321
 EncodingAESKey DN91qruKrUcEkJYeOSbThPhzGd03DUsWGRrNNakfw3N

 * com.jiangli.amazon.lambda.wx.gzh.ConfigRequester
 * @author Jiangli
 * @date 2020/3/3 15:01
 */
public class ConfigRequester implements RequestHandler<Map, Map<String, Object>> {

    public Map<String, Object> handleRequest(Map wxRequestParams, Context context) {
        LambdaLogger logger = context.getLogger();

        logger.log("wxRequestParams:"+wxRequestParams);

        Map map = (Map)wxRequestParams.get("queryStringParameters");
        logger.log("map:"+map);

        Gson gson = new Gson();
        String mapstr = gson.toJson(map);
        logger.log("map str:"+mapstr);
        WxRequestParams req = gson.fromJson(mapstr, WxRequestParams.class);

        logger.log("req:"+req);

        Map<String, Object> response = new HashMap<String, Object>();
        response.put("statusCode", 200);

        try {
            //WXBizMsgCrypt wxBizMsgCrypt = new WXBizMsgCrypt(WeixinConfig.token,WeixinConfig.EncodingAESKey,WeixinConfig.appId);
            //wxBizMsgCrypt.verifyUrl(req.getSignature(), req.getTimestamp(), req.getNonce(), "");

            response.put("body", req.getEchostr());

        }catch (Exception e) {
            e.printStackTrace();
        }


        //response.put("headers", true);
        return response;
    }
}
