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
public class WxMsgLocation extends WxMsgReq {
    private String Location_X;//text
    private String Location_Y;//text
    private String Scale;//text
    private String Label;//text
}
