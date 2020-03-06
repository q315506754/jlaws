package com.jiangli.amazon.lambda.wx.gzh.dto.reqmsg;

import com.jiangli.amazon.lambda.wx.gzh.api.rs.BaseRs;
import com.jiangli.amazon.lambda.wx.gzh.dto.WxMsgType;
import lombok.Data;
import lombok.ToString;

/**
 *
 * https://developers.weixin.qq.com/doc/offiaccount/Message_Management/Passive_user_reply_message.html
 *
 * @author Jiangli
 * @date 2020/3/5 18:24
 */
@Data
@ToString(callSuper = true)
public class WxMsgReq extends BaseRs {
    private String ToUserName;
    private String FromUserName;
    private String CreateTime;
    private String MsgType; //text image voice video music
    private String MsgId;//text

    public WxMsgType getMsgTypeEn() {
        WxMsgType wxMsgType = WxMsgType.valueOf(this.getMsgType());
        return wxMsgType;
    }
}
