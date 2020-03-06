package com.jiangli.amazon.lambda.wx.xml;

import com.jiangli.amazon.lambda.wx.BaseTest;
import com.jiangli.amazon.lambda.wx.gzh.dto.WxMsgType;
import com.jiangli.amazon.lambda.wx.gzh.dto.passivemsg.WxMsgRes;
import com.jiangli.amazon.lambda.wx.gzh.dto.passivemsg.WxMsgResText;
import com.jiangli.amazon.lambda.wx.gzh.dto.reqmsg.WxMsgLink;
import com.jiangli.amazon.lambda.wx.gzh.helper.XmlToBeanParser;
import org.junit.Test;

/**
 * @author Jiangli
 * @date 2020/3/4 18:50
 */
public class ParseXmlTest extends BaseTest {

    @Test
    public void token() {
        String msg = "<xml><ToUserName><![CDATA[gh_f1de43b8057a]]></ToUserName>\n" +
                "<FromUserName><![CDATA[o3c6kwgm-T4ZyjpTMCayIdASckjs]]></FromUserName>\n" +
                "<CreateTime>1583396408</CreateTime>\n" +
                "<MsgType><![CDATA[text]]></MsgType>\n" +
                "<Content><![CDATA[水电费]]></Content>\n" +
                "<MsgId>22668790124366078</MsgId>\n" +
                "</xml>";

        XmlToBeanParser<WxMsgRes> xml = XmlToBeanParser.parseByContent(msg, "xml", WxMsgRes.class);
        System.out.println(xml);
        System.out.println(xml.get().xml());


    }

    @Test
    public void token_xml() {
        WxMsgResText build = (WxMsgResText)WxMsgRes.build(WxMsgType.text);
        build.setContent("aaaaaa");
        System.out.println(build.xml());
    }

    @Test
    public void test_xml2() {
        WxMsgResText build = (WxMsgResText) WxMsgRes.build(WxMsgType.text);
        build.setToUserName("asdasdsdsadas");
        build.setContent("您好,已收到您的消息33");
        String resXml = build.xml();
        System.out.println(resXml);
    }




    @Test
    public void token_2() {
        String msg = "<xml>\n" +
                "  <ToUserName><![CDATA[toUser]]></ToUserName>\n" +
                "  <FromUserName><![CDATA[fromUser]]></FromUserName>\n" +
                "  <CreateTime>12345678</CreateTime>\n" +
                "  <MsgType><![CDATA[music]]></MsgType>\n" +
                "  <Music>\n" +
                "    <Title><![CDATA[TITLE]]></Title>\n" +
                "    <Description><![CDATA[DESCRIPTION]]></Description>\n" +
                "    <MusicUrl><![CDATA[MUSIC_Url]]></MusicUrl>\n" +
                "    <HQMusicUrl><![CDATA[HQ_MUSIC_Url]]></HQMusicUrl>\n" +
                "    <ThumbMediaId><![CDATA[media_id]]></ThumbMediaId>\n" +
                "  </Music>\n" +
                "</xml>\n";

        XmlToBeanParser xml = XmlToBeanParser.parseByContent(msg, "xml", WxMsgRes.class);
        System.out.println(xml);
    }

    @Test
    public void token_3() {
        String msg = "<xml>\n" +
                "  <ToUserName><![CDATA[toUser]]></ToUserName>\n" +
                "  <FromUserName><![CDATA[fromUser]]></FromUserName>\n" +
                "  <CreateTime>1351776360</CreateTime>\n" +
                "  <MsgType><![CDATA[link]]></MsgType>\n" +
                "  <Title><![CDATA[公众平台官网链接]]></Title>\n" +
                "  <Description><![CDATA[公众平台官网链接]]></Description>\n" +
                "  <Url><![CDATA[url]]></Url>\n" +
                "  <MsgId>1234567890123456</MsgId>\n" +
                "</xml>";

        XmlToBeanParser<WxMsgLink> xml = XmlToBeanParser.parseByContent(msg, "xml", WxMsgLink.class);
        System.out.println(xml.get());
        System.out.println(xml.get().getMsgTypeEn());
    }


}