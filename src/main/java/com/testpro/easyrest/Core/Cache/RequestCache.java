package com.testpro.easyrest.Core.Cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 全局缓存 用于用例依赖情况的取值
 */
public class RequestCache {
    private RequestCache(){}
    public final static String method="METHOD";
    public final static ConcurrentHashMap<String,Object> METHOD = new ConcurrentHashMap<>();
    public final static String uri="URI";
    public final static ConcurrentHashMap<String,Object> URI = new ConcurrentHashMap<>();
    public final static String Proxy="PROXY";
    public final static ConcurrentHashMap<String,Object> PROXY = new ConcurrentHashMap<>();
    public final static String Requestparams="REQUEST_PARAMS";
    public final static ConcurrentHashMap<String,Object> REQUEST_PARAMS = new ConcurrentHashMap<>();
    public final static String QueryParams="QUERY_PARAMS";
    public final static ConcurrentHashMap<String,Object> QUERY_PARAMS = new ConcurrentHashMap<>();
    public final static String FormParams="FORM_PARAMS";
    public final static ConcurrentHashMap<String,Object> FORM_PARAMS = new ConcurrentHashMap<>();
    public final static String PathParams="PATH_PARAMS";
    public final static ConcurrentHashMap<String, Map<String, String>> PATH_PARAMS = new ConcurrentHashMap<>();
    public final static String Headers="HEADERS";
    public final static ConcurrentHashMap<String,Object> HEADERS = new ConcurrentHashMap<>();
    public final static String Cookies="COOKIES";
    public final static ConcurrentHashMap<String,Object> COOKIES = new ConcurrentHashMap<>();
    public final static String Multiparts="MULTIPARTS";
    public final static ConcurrentHashMap<String,Object> MULTIPARTS = new ConcurrentHashMap<>();
    public final static String Body="BODY";
    public final static ConcurrentHashMap<String,Object> BODY = new ConcurrentHashMap<>();

}
