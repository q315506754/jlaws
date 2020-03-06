package com.jiangli.amazon.s3;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.File;

/**
 * @author Jiangli
 * @date 2020/3/5 15:12
 */
public class S3Util {
    public static Region selectedRegion = Region.US_EAST_2;

    public static Region region() {
        return selectedRegion;
    }

    public static S3Client client() {
        S3Client s3 = S3Client.builder().region(region()).build();
        return  s3;
    }

    /**
     * @param bucket
     * @param path  /aaa/bbb
     * @param file
     * @return
     */
    public static String uploadFile(MyBucket bucket, String path, File file) {
        if (file == null || !file.exists()) {
            throw new IllegalArgumentException("no such file:" + file);
        }

        S3Client s3 = client();

        //相对路径
        String key = path + "/" + file.getName();

        //上传
        PutObjectResponse putObjectResponse = s3.putObject(PutObjectRequest
                        .builder()
                        .bucket(bucket.getBucketName())
                        .key(key)
                        .build(),
                RequestBody.fromFile(file));

        //System.out.println(putObjectResponse);

        //https://jl-items.s3.us-east-2.amazonaws.com/codes/wx/aws-lambda-1.0.jar
        String url = "https://"+bucket.getBucketName()+".s3."+region().id()+".amazonaws.com"+key;
        return url;
    }
}
