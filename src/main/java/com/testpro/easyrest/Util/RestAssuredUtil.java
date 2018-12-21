package com.testpro.easyrest.Util;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;

@Component
public class RestAssuredUtil {

    public String getBaseurl() {
        return Baseurl;
    }

    public void setBaseurl(String baseurl) {

        Baseurl = baseurl;
        baseURI = Baseurl;
    }

    private String Baseurl;

    RestAssuredUtil() {
    }

    ;

    public RestAssuredUtil(String baseURI) {
        RestAssured.baseURI = baseURI;
    }

    /**
     * GetResponse 纯url
     * @param url
     * @return
     */
    public Response GetResponse(String url) {
        return get(url);
    }

    /**
     *   GetResponseparameters Url +参数信息
     * @param url
     * @param parameters
     * @return
     */
    public Response GetResponseparameters(String url, Map parameters) {
        return given().queryParams(parameters).get(url);
    }

    /**
     *  GetResponseheards url+头部信息
     * @param url
     * @param heards
     * @return
     */
    public Response GetResponseheards(String url, Map heards) {
        return given().headers(heards).get(url);
    }

    /**
     * GetResponse url+头部信息+参数信息
     * @param url
     * @param parameters
     * @param heders
     * @return
     */
    public Response GetResponse(String url, Map parameters, Map heders) {
        return given().params(parameters).headers(heders).when().get(url);
    }

    /**
     * PostResponse 纯post
     * @param url
     * @return
     */
    public Response PostResponse(String url) {
        return post(url);
    }

    /**
     *  PostResponseparameters url+参数信息
     * @param url
     * @param parameters
     * @return
     */
    public Response PostResponseparameters(String url, Map parameters) {

        return given().params(parameters).when().post(url);

    }

    /**
     *  PostResponseheards url+头部信息
     * @param url
     * @param heards
     * @return
     */
    public Response PostResponseheards(String url, Map heards) {

        return given().headers(heards).when().post(url);

    }

    /**
     * PostResponse url +头部信息+参数信息
     * @param url
     * @param parameters
     * @param heders
     * @return
     */
    public Response PostResponse(String url, Map parameters, Map heders) {

        return given().params(parameters).headers(heders).when().post(url);

    }

}
