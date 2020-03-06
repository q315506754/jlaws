package com.jiangli.amazon.lambda.wx.gzh.dto.passivemsg;

import com.jiangli.amazon.lambda.wx.gzh.config.WeixinConfig;
import com.jiangli.amazon.lambda.wx.gzh.dto.WxMsgType;
import lombok.Data;
import lombok.ToString;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * https://developers.weixin.qq.com/doc/offiaccount/Message_Management/Passive_user_reply_message.html
 *
 * @author Jiangli
 * @date 2020/3/5 18:24
 */
@Data
@ToString(callSuper = true)
public class WxMsgRes  {
    private String ToUserName;
    private String FromUserName;
    private Number CreateTime;
    private String MsgType; //text image voice video music

    public WxMsgType getMsgTypeEn() {
        WxMsgType wxMsgType = WxMsgType.valueOf(this.getMsgType());
        return wxMsgType;
    }



    public static WxMsgRes build(WxMsgType type)   {
        WxMsgRes ret = null;
        switch (type) {
            case text:ret =  new WxMsgResText();break;
            case music:ret =  new WxMsgResMusic();break;
            case video:ret =  new WxMsgResVideo();break;
            case voice:ret =  new WxMsgResVoice();break;
            case image:ret =  new WxMsgResImage();break;
        }


        if (ret != null) {
            ret.setMsgType(type.name());
            //ret.setCreateTime(System.currentTimeMillis()/1000);
            ret.setCreateTime(12345678);
            ret.setFromUserName(WeixinConfig.WX_NUM);
        }
        return ret;
    }

    public  Field[] getFieldsIncludeAncestor(Class cls)   {
        List<List<Field>> ret = new ArrayList<>();

        Class parent = cls;
        while (parent!=null && parent != Object.class) {
            List<Field> p = new ArrayList<>();
            for (Field declaredField : parent.getDeclaredFields()) {
                p.add(declaredField) ;
            }
            ret.add(p);
            parent = parent.getSuperclass();
        }

        Collections.reverse(ret);
        List<Field> temp = new ArrayList<>();
        for (List<Field> s1 : ret) {
            for (Field s2 : s1) {
                temp.add(s2);
            }
        }
        return temp.toArray(new Field[temp.size()]);
    }

    public String xml()   {
        StringBuilder sb = new StringBuilder();
        try {
            sb.append("<xml>\n");
            //Field[] declaredFields = this.getClass().getDeclaredFields();
            Field[] declaredFields = getFieldsIncludeAncestor(this.getClass());
            for (Field declaredField : declaredFields) {
                Class<?> type = declaredField.getType();
                declaredField.setAccessible(true);
                String name = declaredField.getName();
                Object v = declaredField.get(this);
                String vStr = String.valueOf(v);

                sb.append("<"+name+">");

                if (type == String.class) {
                    vStr = "<![CDATA[" + v + "]]>";
                }
                sb.append(vStr+"");

                sb.append("</"+name+">\n");
            }
            sb.append("</xml>");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sb.toString();
    }
}
