package com.sonicop.ohm.optopus.myohmbeads.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient;
import com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClientBuilder;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsConfig {

  @Value("${aws.access_key_id}")
  private String awsId;

  @Value("${aws.secret_access_key}")
  private String awsKey;

  @Value("${aws.region}")
  private String region;
  
  @Value("${aws.s3.bucket}")
  private String s3Bucket;
  
  private AmazonS3 s3Client;
  private AWSSecurityTokenServiceClient stsClient;

  @PostConstruct
  public void init() {
    BasicAWSCredentials awsCreds = new BasicAWSCredentials(awsId, awsKey);
    this.s3Client = AmazonS3ClientBuilder.standard()
            .withRegion(Regions.fromName(region))
            .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
            .build();
    
    this.stsClient = (AWSSecurityTokenServiceClient) AWSSecurityTokenServiceClientBuilder.standard()
            .withRegion(Regions.fromName(region))
            .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
            .build();
  }

  public String getRegion() {
    return region;
  }

  public AmazonS3 getS3Client() {
    return s3Client;
  }

  public AWSSecurityTokenServiceClient getStsClient() {
    return stsClient;
  }

  public String getS3Bucket() {
    return s3Bucket;
  }
}
