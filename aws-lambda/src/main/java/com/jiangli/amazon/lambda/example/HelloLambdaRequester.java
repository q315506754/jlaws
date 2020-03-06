package com.jiangli.amazon.lambda.example;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

/**
 * com.jiangli.amazon.lambda.example.HelloLambdaRequester::handleRequest
 * @author Jiangli
 * @date 2020/3/3 15:01
 */
public class HelloLambdaRequester  implements RequestHandler<Integer, String> {
    public String handleRequest(Integer myCount, Context context) {
        LambdaLogger logger = context.getLogger();

        logger.log("Log data from LambdaLogger \n with multiple lines");
        // Print info from the context object
        logger.log("Function name: " + context.getFunctionName());
        logger.log("Max mem allocated: " + context.getMemoryLimitInMB());
        logger.log("Time remaining in milliseconds: " + context.getRemainingTimeInMillis());
        String.format("Hello %s. log stream = %s", myCount, context.getLogStreamName());

        System.out.println("你输入了:"+myCount);

        if (myCount < 30) {
            int fibonacci = fibonacci(myCount);
            System.out.println("执行fibonacci:"+fibonacci);
            return String.valueOf(fibonacci);
        }


        return String.valueOf(myCount);
    }

    public static int fibonacci(int n) {
        if (n==1) {
            return 1;
        }
        if (n==2) {
            return 1;
        }

        return fibonacci(n-2) + fibonacci (n-1);
    }

    public static void main(String[] args) {
        System.out.println(fibonacci(30));
    }
}
