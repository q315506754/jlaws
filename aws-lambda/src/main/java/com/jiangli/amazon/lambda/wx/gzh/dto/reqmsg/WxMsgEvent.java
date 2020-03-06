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
public class WxMsgEvent extends WxMsgReq {
    private String Event;//text
    private String EventKey;//text
}
