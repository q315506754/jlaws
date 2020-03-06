package com.jiangli.amazon.s3.test;

import com.jiangli.amazon.mycommons.utils.FileUtil;
import com.jiangli.amazon.mycommons.utils.PathUtil;
import com.jiangli.amazon.s3.MyBucket;
import com.jiangli.amazon.s3.S3Util;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jiangli
 * @date 2020/3/5 13:45
 */
public class DeployTest {
    @Test
    public void test_pkg() {
        String basePath = pkgAndGetBasePath();
    }

    @Test
    public void test_deploy() {
        Map<String, List<String>> configMap = new HashMap<>();
        configMap.put("/aws-lambda/target/aws-lambda-1.0.jar", Arrays.asList("wx_gzh_msg_handler"));

        final String up_path = "/codes/wx";
        String basePath = pkgAndGetBasePath();

        for (Map.Entry<String, List<String>> entry : configMap.entrySet()) {
            String relaFile = entry.getKey();
            String absFile = basePath + relaFile;

            File upFile = new File(absFile);
            String fileUrl = S3Util.uploadFile(MyBucket.JL_ITEMS, up_path, upFile);
            System.out.println("↓↓↓upload localfile"+upFile + " to ↓↓↓");
            System.out.println(fileUrl);

            String id = S3Util.region().id();

            for (String lamdaName : entry.getValue()) {
                System.out.println("manual publish lambda code of" + lamdaName + ":");
                String consoleUrl = "https://"+id+".console.aws.amazon.com/lambda/home?region="+id+"#/functions/"+lamdaName+"?tab=configuration";
                System.out.println(consoleUrl);
            }
        }

        //String upFile = "C:\\myprojects\\amazonaws\\aws-lambda\\target\\aws-lambda-1.0.jar";
        //File batFile = new File(upFile);
        //
        //String fileUrl = S3Util.uploadFile(MyBucket.JL_ITEMS, "/codes/wx", batFile);
        //System.out.println(fileUrl);
    }

    private String pkgAndGetBasePath() {
        String batName = "pkg.bat";
        String basePath = PathUtil.getBaseProjectPath();
        //System.out.println(basePath);

        File batFile = new File(basePath, batName);
        if (!batFile.exists()) {
            throw new Error("no file " + batFile);
        }

        //打包
        FileUtil.executeOrigin(batFile.getAbsolutePath());
        return basePath;
    }
}
