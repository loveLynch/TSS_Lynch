package com.silver.tss.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by lynch on 2018/10/26. <br>
 **/
@Component
public class AliyunOSSConfig implements InitializingBean {

    @Value("${aliyunoss.file.endpoint}")
    private String aliyunoss_file_endpoint;

    @Value("${aliyunoss.file.keyid}")
    private String aliyunoss_file_keyid;

    @Value("${aliyunoss.file.keysecret}")
    private String aliyunoss_file_keysecret;

    @Value("${aliyunoss.file.bucketname}")
    private String aliyunoss_file_bucketname;


    public static String ALIYUNOSS_END_POINT;
    public static String ALIYUNOSS_ACCESS_KEY_ID;
    public static String ALIYUNOSS_ACCESS_KEY_SECRET;
    public static String ALIYUNOSS_BUCKET_NAME;

    @Override
    public void afterPropertiesSet() throws Exception {
        ALIYUNOSS_END_POINT = aliyunoss_file_endpoint;
        ALIYUNOSS_ACCESS_KEY_ID = aliyunoss_file_keyid;
        ALIYUNOSS_ACCESS_KEY_SECRET = aliyunoss_file_keysecret;
        ALIYUNOSS_BUCKET_NAME = aliyunoss_file_bucketname;
    }

}
