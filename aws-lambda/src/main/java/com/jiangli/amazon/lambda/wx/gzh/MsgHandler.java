package com.jiangli.amazon.lambda.wx.gzh;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import com.jiangli.amazon.lambda.wx.gzh.dto.WxMsgType;
import com.jiangli.amazon.lambda.wx.gzh.dto.WxRequestParams;
import com.jiangli.amazon.lambda.wx.gzh.dto.passivemsg.WxMsgRes;
import com.jiangli.amazon.lambda.wx.gzh.dto.passivemsg.WxMsgResText;
import com.jiangli.amazon.lambda.wx.gzh.dto.reqmsg.*;
import com.jiangli.amazon.lambda.wx.gzh.helper.XmlToBeanParser;

import java.util.HashMap;
import java.util.Map;

import static com.jiangli.amazon.lambda.wx.gzh.dto.MapBuilder.mapOf;

/**

 URL https://k9noqpg5pa.execute-api.us-east-2.amazonaws.com/prod/config
 Token 123321123321123321
 EncodingAESKey DN91qruKrUcEkJYeOSbThPhzGd03DUsWGRrNNakfw3N

 可以使用客服消息接口进行异步回复），否则，将出现严重的错误提示。详见下面说明：

 1、直接回复success（推荐方式） 2、直接回复空串（指字节长度为0的空字符串，而不是XML结构体中content字段的内容为空）

 一旦遇到以下情况，微信都会在公众号会话中，向用户下发系统提示“该公众号暂时无法提供服务，请稍后再试”：

 1、开发者在5秒内未回复任何内容 2、开发者回复了异常数据，比如JSON数据等

 另外，请注意，回复图片（不支持gif动图）等多媒体消息时需要预先通过素材管理接口上传临时素材到微信服务器，可以使用素材管理中的临时素材，也可以使用永久素材。

 * com.jiangli.amazon.lambda.wx.gzh.ConfigRequester
 * @author Jiangli
 * @date 2020/3/3 15:01
 */
public class MsgHandler implements RequestHandler<Map, Map<String, Object>> {

    public Map<String, Object> handleRequest(Map wxRequestParams, Context context) {
        LambdaLogger logger = context.getLogger();

        logger.log("wxRequestParams:"+wxRequestParams);

        Map map = (Map)wxRequestParams.get("queryStringParameters");
        String body = (String)wxRequestParams.get("body");
        logger.log("param map:"+map);
        logger.log("body:"+body);

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

            XmlToBeanParser<WxMsgRes> xml = XmlToBeanParser.parseByContent(body, "xml", WxMsgRes.class);
            WxMsgRes wxMsgRes = xml.get();
            String reply = "";
            switch (wxMsgRes.getMsgTypeEn()) {
                case text:{
                    WxMsgText msg = XmlToBeanParser.parseByContent(body, "xml", WxMsgText.class).get();
                    reply = msg.getContent();
                    break;
                }
                case image:{
                    WxMsgImage msg = XmlToBeanParser.parseByContent(body, "xml", WxMsgImage.class).get();
                    reply = msg.getPicUrl();
                    break;
                }
                case voice:{
                    WxMsgVoice msg = XmlToBeanParser.parseByContent(body, "xml", WxMsgVoice.class).get();
                    reply = msg.getMediaId();
                    break;
                }
                case video:{
                    WxMsgVideo msg = XmlToBeanParser.parseByContent(body, "xml", WxMsgVideo.class).get();
                    reply = msg.getMediaId();
                    break;
                }
                case shortvideo:{
                    WxMsgShortVideo msg = XmlToBeanParser.parseByContent(body, "xml", WxMsgShortVideo.class).get();
                    reply = msg.getMediaId();
                    break;
                }
                case location:{
                    WxMsgLocation msg = XmlToBeanParser.parseByContent(body, "xml", WxMsgLocation.class).get();
                    reply = msg.getLocation_X()+","+msg.getLocation_Y();
                    break;
                }
                case link:{
                    WxMsgLink msg = XmlToBeanParser.parseByContent(body, "xml", WxMsgLink.class).get();
                    reply = msg.getUrl();
                    break;
                }
                case event:{
                    WxMsgEvent msg = XmlToBeanParser.parseByContent(body, "xml", WxMsgEvent.class).get();
                    reply = msg.getEvent() + " " + msg.getEventKey();
                    break;
                }
            }


            WxMsgResText build = (WxMsgResText) WxMsgRes.build(WxMsgType.text);
            build.setToUserName(req.getOpenid());
            build.setContent("您好,已收到您的"+wxMsgRes.getMsgTypeEn()+"消息:"+reply);
            String resXml = build.xml();

            logger.log("res:"+resXml);

            //1、直接回复success（推荐方式）
            //response.put("body", "success");
            // 2、直接回复空串（指字节长度为0的空字符串，而不是XML结构体中content字段的内容为空）
            //response.put("body", "");

            //https://developers.weixin.qq.com/doc/offiaccount/Message_Management/Passive_user_reply_message.html
            //被动消息回复
            response.put("body", resXml);

            response.put("headers", mapOf("Content-Type","text/xml").build());

        }catch (Exception e) {
            e.printStackTrace();
        }

        //response.put("headers", true);
        return response;
    }


}
