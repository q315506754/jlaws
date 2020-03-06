package com.jiangli.amazon.lambda.wx.gzh;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;

import java.io.*;

/**

 com.jiangli.amazon.lambda.wx.gzh.MsgHandler
 * @author Jiangli
 * @date 2020/3/3 15:01
 */
public class MsgHandler_deprecated implements RequestStreamHandler {
    @Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        int letter;
        while ((letter = inputStream.read()) != -1) {
            bos.write(letter);
        }
        bos.close();

        LambdaLogger logger = context.getLogger();

        String xml = bos.toString();
        logger.log("xml:"+xml);
        logger.log("context:"+context);

        OutputStreamWriter writer = new OutputStreamWriter(outputStream);
        writer.write(xml);
        writer.flush();
        writer.close();

    }

}
