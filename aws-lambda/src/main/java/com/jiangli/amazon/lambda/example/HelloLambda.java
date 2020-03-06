package com.jiangli.amazon.lambda.example;

import com.amazonaws.services.lambda.runtime.Context;

/**
 * https://docs.aws.amazon.com/lambda/latest/dg/java-handler.html
 * com.jiangli.amazon.lambda.example.HelloLambda::myHandler
 * @author Jiangli
 * @date 2020/3/3 15:01
 */
public class HelloLambda {
    //AWS Lambda supports the String, Integer, Boolean, Map, and List types.
    public String myHandler(String name, Context context) {
        return String.format("Hello %s.", name);
    }
}
