package com.testpro.easyrest.Core.Cache;

import java.util.concurrent.ConcurrentHashMap;

public class ResponseCache {

    private ResponseCache(){}

    public final static String ContentType="CONTENT_TYPE";
    public final static ConcurrentHashMap<String,Object> CONTENT_TYPE = new ConcurrentHashMap<>();
    public final static String Body="BODY";
    public final static ConcurrentHashMap<String,Object> BODY = new ConcurrentHashMap<>();
    public final static String Cookies="COOKIES";
    public final static ConcurrentHashMap<String,Object> COOKIES = new ConcurrentHashMap<>();
    public final static String Time="TIME";
    public final static ConcurrentHashMap<String,Object> TIME = new ConcurrentHashMap<>();
    public final static String Headers="HEADERS";
    public final static ConcurrentHashMap<String,Object> HEADERS = new ConcurrentHashMap<>();
    public final static String SessionId="SESSION_ID";
    public final static ConcurrentHashMap<String,Object> SESSION_ID = new ConcurrentHashMap<>();

}
