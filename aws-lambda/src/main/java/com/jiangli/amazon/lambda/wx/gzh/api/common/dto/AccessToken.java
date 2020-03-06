package com.jiangli.amazon.lambda.wx.gzh.api.common.dto;

import com.jiangli.amazon.lambda.wx.gzh.api.rs.BaseRs;
import lombok.Data;
import lombok.ToString;

/**
 * @author Jiangli
 * @date 2020/3/4 18:48
 */
@Data
@ToString(callSuper = true)
public class AccessToken extends BaseRs {
    private String access_token;
    private Integer expires_in;
}
