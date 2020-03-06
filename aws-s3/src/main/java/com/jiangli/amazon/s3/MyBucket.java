package com.jiangli.amazon.s3;

/**
 * @author Jiangli
 * @date 2020/3/5 15:10
 */
public enum  MyBucket {
    JL_ITEMS("jl-items")
    ;

    private String bucketName;

    MyBucket(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }
}
