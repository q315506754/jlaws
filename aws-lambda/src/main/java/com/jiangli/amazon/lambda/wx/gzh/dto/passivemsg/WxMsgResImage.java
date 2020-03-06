package com.jiangli.amazon.lambda.wx.gzh.dto.passivemsg;

import lombok.Data;
import lombok.ToString;

/**
 *
 * @author Jiangli
 * @date 2020/3/5 18:24
 */
@Data
@ToString(callSuper = true)
public class WxMsgResImage extends WxMsgRes {
    private WxMsgDtoMedia Image;//image
}
