package com.jiangli.amazon.lambda.wx.gzh.api.common.dto;

import com.jiangli.amazon.lambda.wx.gzh.api.rs.BaseRs;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author Jiangli
 * @date 2020/3/4 19:16
 */
@Data
@ToString(callSuper = true)
public class Getcallbackip extends BaseRs {
    private List<String> ip_list;
}
