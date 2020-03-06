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
public class WxMsgText extends WxMsgReq {
    private String Content;//text
}
