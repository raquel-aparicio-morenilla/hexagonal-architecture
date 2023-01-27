package com.raquel.hexagon.infrastructure.outputAdapter;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Getter
public class S3BucketClient {

    private String bucketName;
    private Map<String, S3ObjectSummary> s3ObjectList;

    public S3BucketClient (String bucketName) {
        this.bucketName = bucketName;
        this.s3ObjectList = new HashMap<>();
    }


    public Map<String, S3ObjectSummary> listAllObjects(){
        System.out.format("Fetching objects from S3 bucket %s...\n", bucketName);
        return s3ObjectList;
    }

    public void putObject(S3ObjectSummary object) {
        System.out.format("Saving object to S3 bucket %s...\n", bucketName);
        s3ObjectList.put(object.getName(), object);

    }

    public Optional<S3ObjectSummary> getObject(String objectName) {
        System.out.format("Downloading %s from S3 bucket %s...\n", objectName, bucketName);
        return Optional.ofNullable(s3ObjectList.get(objectName));
    }

    public void deleteS3Bucket(){
        System.out.format("Deleting S3 bucket %s...\n",bucketName);
        s3ObjectList.clear();
    }


}
