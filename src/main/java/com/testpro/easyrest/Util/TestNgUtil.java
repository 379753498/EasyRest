package com.testpro.easyrest.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class TestNgUtil {

    /*
    对象转化
     */
    public static Iterator<Object[]> createObjectList(List<Object> list) {
        List<Object[]> objects = new ArrayList<Object[]>();
        for (Object object : list) {
            objects.add(new Object[]{object});
        }
        return objects.iterator();
    }

    public static HashMap<String, String> createParameters(String key, String value) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(key, value);
        return map;
    }


}
