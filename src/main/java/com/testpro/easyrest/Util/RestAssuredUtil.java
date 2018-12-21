package com.testpro.easyrest.Util;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.HashMap;

import static io.restassured.RestAssured.*;

public class RestAssuredUtil {


    public RestAssuredUtil(String baseURI) {
        RestAssured.baseURI = baseURI;
    }

    public Response GetResponse(String url) {
        return get(url);
    }

    public Response GetResponse(String url, HashMap <String, String> parameters) {
        return given().queryParams(parameters).get(url);

    }

    public Response GetResponse(String url, HashMap <String, String> parameters, HashMap <String, String> heders) {
        return given().params(parameters).headers(heders).when().get(url);

    }

    public Response PostResponse(String url) {
        return post(url);
    }

    public Response PostResponse(String url, HashMap <String, String> parameters) {

        return given().params(parameters).when().post(url);

    }

    public Response PostResponse(String url, HashMap <String, String> parameters, HashMap <String, String> heders) {

        return given().params(parameters).headers(heders).when().post(url);

    }

}
