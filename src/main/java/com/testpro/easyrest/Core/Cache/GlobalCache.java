package com.testpro.easyrest.Core.Cache;

import java.util.HashMap;

/**
 * 全局缓存 用于用例依赖情况的取值
 */
public class GlobalCache {
    public final static HashMap<String,Object> method = new HashMap<>();

    public final static HashMap<String,Object> URI = new HashMap<>();

    public final static HashMap<String,Object> Proxy = new HashMap<>();

    public final static HashMap<String,Object> Requestparams = new HashMap<>();

    public final static HashMap<String,Object> QueryParams = new HashMap<>();

    public final static HashMap<String,Object> FormParams = new HashMap<>();

    public final static HashMap<String,Object> PathParams = new HashMap<>();

    public final static HashMap<String,Object> Headers = new HashMap<>();

    public  final static HashMap<String,Object> Cookies = new HashMap<>();

    public final static HashMap<String,Object> Multiparts = new HashMap<>();

    public final static HashMap<String,Object> Body = new HashMap<>();

    public final static HashMap<String,Object> ResponseContentType = new HashMap<>();
}
