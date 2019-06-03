package com.testpro.easyrest.Util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.testpro.easyrest.bean.Result;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class JsonUtil {

  private static Gson gson;

  static {
    JSONObject.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    if (gson == null) {
      gson = new GsonBuilder().setPrettyPrinting().create();
    }
  }

  /**
   * 将字符串转化为JSONObject对象
   *
   * @param JsonString
   * @return
   */
  public static JSONObject FastStringtoJSONObject(String JsonString) {

    JSONObject jsonObject = JSONObject.parseObject(JsonString);
    return jsonObject;
  }

  public static Map FastStringtoMap(String JsonString) {

    JSONObject jsonObject = JSONObject.parseObject(JsonString);
    if (jsonObject != null) {
      return (Map) jsonObject;
    } else {
      throw new RuntimeException("josn 字符串解析错位 请核对后再播");
    }
  }

  public static String FastStringtoJsonString(String jsonString) {
    return JSONObject.parseObject(jsonString, LinkedHashMap.class).toString();
  }
  /**
   * 将字符串转化为JSONArray对象
   *
   * @param JsonString
   * @return
   */
  public static JSONArray FastStringtoJSONArray(String JsonString) {

    JSONArray objects = JSONObject.parseArray(JsonString);
    return objects;
  }

  /**
   * 将字符串转化为Bean
   *
   * @param JsonString
   * @param Bean
   * @param <T>
   * @return
   */
  public static <T> T FastJsonStringToBean(String JsonString, Class<T> Bean) {
    T t = JSONObject.parseObject(JsonString, Bean);
    return t;
  }

  /**
   * 将字符串 转化为BeanList
   *
   * @param JsonString
   * @param Bean
   * @param <T>
   * @return
   */
  public static <T> List<T> FastJsonStringToBeanList(String JsonString, Class<T> Bean) {
    List<T> ts = JSONObject.parseArray(JsonString, Bean);
    return ts;
  }

  /**
   * 将对象转化为字符串
   *
   * @param object
   * @return
   */
  public static String FastObjectToJsonString(Object object) {
    String s =
        JSONObject.toJSONString(
            object,
            SerializerFeature.DisableCircularReferenceDetect,
            SerializerFeature.WriteDateUseDateFormat);
    return s;
  }

  public static void setGson(GsonBuilder GsonBuilder) {
    JsonUtil.gson = GsonBuilder.create();
  }

  /**
   * @param JsonString JSon 字符串
   * @param Bean 类对象
   * @param <T> 返回类对象
   * @return
   */
  public static <T> T GsonStringToBean(String JsonString, Class<T> Bean) {
    T t = gson.fromJson(JsonString, Bean);
    return t;
  }

  /**
   * 对象转化为Json字符
   *
   * @param object 对象
   * @return Json 字符串
   */
  public static String GsonBeanToString(Object object) {
    return gson.toJson(object);
  }

  /**
   * 接受一个字符串 返回对应的Beanlist
   *
   * @param JsonString
   * @param classtype
   * @param <T>
   * @return
   */
  public static <T> List<T> GsonStringToList(String JsonString, Class<T> classtype) {
    List<T> list = null;
    list = gson.fromJson(JsonString, new TypeToken<List<T>>() {}.getType());
    return list;
  }

  public static <T> Result<List<T>> GsonStringToResultList(String JsonString, Class<T> classtype) {
    Result<List<T>> list = null;
    list = gson.fromJson(JsonString, new TypeToken<Result<List<T>>>() {}.getType());
    return list;
  }

  /**
   * 将list排除值为null的字段转换成Json数组
   *
   * @param list
   * @param <T>
   * @return
   */
  public static <T> String toJsonArrayWithSerializeNulls(List<T> list) {
    gson = new GsonBuilder().serializeNulls().create();
    String result = "";
    result = gson.toJson(list);
    return result;
  }

  /**
   * 将list中将Expose注解的字段转换成Json数组
   *
   * @param list
   * @param <T>
   * @return
   */
  public static <T> String toJsonArrayWithExpose(List<T> list) {
    gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    String result = "";
    result = gson.toJson(list);
    return result;
  }

  /**
   * 转成list中有map的
   *
   * @param gsonString
   * @return
   */
  public static <T> List<Map<String, T>> GsonToListMaps(String gsonString) {
    List<Map<String, T>> list = null;
    if (gson != null) {
      list = gson.fromJson(gsonString, new TypeToken<List<Map<String, T>>>() {}.getType());
    }
    return list;
  }

  /**
   * 转成map的
   *
   * @param gsonString
   * @return
   */
  public static <T> Map<String, T> GsonToMaps(String gsonString) {
    Map<String, T> map = null;
    if (gson != null) {
      map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {}.getType());
    }
    return map;
  }

  public static String JsonArraygood(String assertResult) {
    JSONArray parse = (JSONArray) JSONArray.parse(assertResult);
    StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append("[");
    Object[] objects = parse.toArray();
    for (int i = 0; i < objects.length; i++) {
      if (i == objects.length - 1) {
        stringBuffer.append(JsonPretty(objects[i].toString()));
      } else {
        stringBuffer.append(JsonPretty(objects[i].toString())).append("," + "\n");
      }
    }
    stringBuffer.append("]");
    return stringBuffer.toString();
  }

  public static String JsonPretty(String requestInfo) {
    JSONObject jsonObject = JSONObject.parseObject(requestInfo);
    return JSONObject.toJSONString(jsonObject, true);
  }
}
