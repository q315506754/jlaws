package com.jiangli.amazon.lambda.wx.gzh.dto.reqmsg;

import lombok.Data;
import lombok.ToString;

/**
 *
 * @author Jiangli
 * @date 2020/3/5 18:24
 */
@Data
@ToString(callSuper = true)
public class WxMsgShortVideo extends WxMsgReq {
    private String MediaId;//text
    private String ThumbMediaId;//text
}
