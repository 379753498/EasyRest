package com.testpro.easyrest.Enum;

public enum ContentType {


    JSON("application/json"),XML("application/xml ");

    public String getValue() {
        return value;
    }

    private String value;
    private ContentType(String value) {
        this.value = value;
    }

}
