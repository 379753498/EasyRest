package com.testpro.easyrest.bean;


import lombok.Data;



@Data
public class Result<T> {

    private String Message;
    private int Code;
    public T data;
}
