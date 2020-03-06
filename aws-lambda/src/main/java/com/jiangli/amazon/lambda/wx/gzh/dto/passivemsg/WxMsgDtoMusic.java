package com.jiangli.amazon.lambda.wx.gzh.dto.passivemsg;

import lombok.Data;

/**
 * @author Jiangli
 * @date 2020/3/5 19:04
 */
@Data
public class WxMsgDtoMusic {
    private String Title;
    private String Description;
    private String MusicUrl;
    private String HQMusicUrl;
    private String ThumbMediaId;
}
