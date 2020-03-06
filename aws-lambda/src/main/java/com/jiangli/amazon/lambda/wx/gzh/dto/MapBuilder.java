package com.jiangli.amazon.lambda.wx.gzh.dto;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jiangli
 * @date 2020/3/4 16:26
 */
public class MapBuilder {
    private Map<String, Object> mp = new HashMap<String,Object>();

    public static MapBuilder mapOf(String k, String v) {
        MapBuilder ret = new MapBuilder();
        ret.mp.put(k, v);
        return ret;
    }

    public Map<String, Object> build() {
        return mp;
    }

    @Override
    public String toString() {
        return mp.toString();
    }
}
