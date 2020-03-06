package com.jiangli.amazon.s3.test;

import com.jiangli.amazon.s3.MyBucket;
import com.jiangli.amazon.s3.S3Util;
import org.junit.Test;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.File;
import java.nio.ByteBuffer;
import java.util.Random;

/**
 * @author Jiangli
 * @date 2020/3/5 13:45
 */
public class S3Test {

    @Test
    public void test_bucket() {
        //Region region = Region.US_EAST_2;
        Region region = Region.AP_SOUTHEAST_1;
        S3Client s3 = S3Client.builder().region(region).build();
        String bucket = "bucket" + System.currentTimeMillis();
        System.out.println(bucket);

        // List buckets
        ListBucketsRequest listBucketsRequest = ListBucketsRequest.builder().build();
        ListBucketsResponse listBucketsResponse = s3.listBuckets(listBucketsRequest);
        listBucketsResponse.buckets().stream().forEach(x -> System.out.println(x.name()));

        // Create bucket
        CreateBucketRequest createBucketRequest = CreateBucketRequest
                .builder()
                .bucket(bucket)
                .createBucketConfiguration(CreateBucketConfiguration.builder()
                        .locationConstraint(region.id())
                        .build())
                .build();
        s3.createBucket(createBucketRequest);


    }

    @Test
    public void test_object() {
        //Region region = Region.AP_SOUTHEAST_1;
        Region region = Region.US_EAST_2;
        S3Client s3 = S3Client.builder().region(region).build();

        String bucket = "wildrydes-jiang-li";
        //String bucket = "arn:aws:s3:us-east-2:611954003302:accesspoint/acall";
        //String bucket = "http://wildrydes-jiang-li.s3-website.us-east-2.amazonaws.com/";
        //String bucket = "http://wildrydes-jiang-li.s3-website.us-east-2.amazonaws.com/";

        //相对路径
        String key = "/aaa/bbb/cc.txt";

        // Put Object
        s3.putObject(PutObjectRequest
                        .builder()
                        .bucket(bucket)
                        .key(key)
                        .build(),
                RequestBody.fromByteBuffer(getRandomByteBuffer(10_000)));
    }

    @Test
    public void test_object2() {
        String upFile = "C:\\myprojects\\amazonaws\\aws-lambda\\target\\aws-lambda-1.0.jar";
        File file = new File(upFile);

        String fileUrl = S3Util.uploadFile(MyBucket.JL_ITEMS, "/codes/wx", file);
        System.out.println(fileUrl);
    }
    @Test
    public void test_object_big() {
        Region region = Region.US_EAST_2;
        S3Client s3 = S3Client.builder().region(region).build();

        String bucketName = "jl-items";
        String upFile = "C:\\myprojects\\amazonaws\\aws-lambda\\target\\aws-lambda-1.0.jar";
        File file = new File(upFile);
        String key = "/codes/wx/"+file.getName();

        // First create a multipart upload and get upload id
        CreateMultipartUploadRequest createMultipartUploadRequest = CreateMultipartUploadRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        CreateMultipartUploadResponse response = s3.createMultipartUpload(createMultipartUploadRequest);
        String uploadId = response.uploadId();
        System.out.println("uploadId:"+uploadId);

        // Upload all the different parts of the object
        UploadPartRequest uploadPartRequest1 = UploadPartRequest.builder().bucket(bucketName).key(key)
                .uploadId(uploadId)
                .partNumber(1).build();
        int mb = 1024*1024;
        String etag1 = s3.uploadPart(uploadPartRequest1, RequestBody.fromByteBuffer(getRandomByteBuffer(5 * mb))).eTag();
        CompletedPart part1 = CompletedPart.builder().partNumber(1).eTag(etag1).build();

        UploadPartRequest uploadPartRequest2 = UploadPartRequest.builder().bucket(bucketName).key(key)
                .uploadId(uploadId)
                .partNumber(2).build();
        String etag2 = s3.uploadPart(uploadPartRequest2, RequestBody.fromByteBuffer(getRandomByteBuffer(3 * mb))).eTag();
        CompletedPart part2 = CompletedPart.builder().partNumber(2).eTag(etag2).build();


        // Finally call completeMultipartUpload operation to tell S3 to merge all uploaded
        // parts and finish the multipart operation.
        CompletedMultipartUpload completedMultipartUpload = CompletedMultipartUpload.builder().parts(part1, part2).build();
        CompleteMultipartUploadRequest completeMultipartUploadRequest =
                CompleteMultipartUploadRequest.builder().bucket(bucketName).key(key).uploadId(uploadId)
                        .multipartUpload(completedMultipartUpload).build();
        s3.completeMultipartUpload(completeMultipartUploadRequest);

    }

    private static ByteBuffer getRandomByteBuffer(int size)   {
        byte[] b = new byte[size];
        new Random().nextBytes(b);
        return ByteBuffer.wrap(b);
    }
}
