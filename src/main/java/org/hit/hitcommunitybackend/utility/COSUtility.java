package org.hit.hitcommunitybackend.utility;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.*;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.utils.IOUtils;
import org.springframework.stereotype.Service;

@Service
public class COSUtility {
    private static final String secretId = "AKIDHWr6PcjgT7d2JTXSEqHk5INI6lIgv1xp";
    private static final String secretKey = "ueEfx674va3LafeUAg3IoLn9NUSK5hw1";
    private static final String bucketName = "hitcommunity-1326079410";
    private static final String region = "ap-beijing";
    private final COSClient cosClient;

    public COSUtility() {
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        ClientConfig clientConfig = new ClientConfig(new Region(region));
        this.cosClient = new COSClient(cred, clientConfig);
    }


    public void putData(String key, byte[] data) {
        InputStream inputStream = new ByteArrayInputStream(data);
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(data.length);
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, inputStream, objectMetadata);
        try {
            cosClient.putObject(putObjectRequest);
        } catch (CosServiceException e) {
            e.printStackTrace();
        } catch (CosClientException e) {
            e.printStackTrace();
        }
    }

    public byte[] getData(String key) {
        GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, key);
        InputStream cosObjectInput = null;
        try {
            COSObject cosObject = cosClient.getObject(getObjectRequest);
            cosObjectInput = cosObject.getObjectContent();
        } catch (CosServiceException e) {
            e.printStackTrace();
        } catch (CosClientException e) {
            e.printStackTrace();
        }
        byte[] bytes = null;
        try {
            bytes = IOUtils.toByteArray(cosObjectInput);
            return bytes;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            // 用完流之后一定要调用 close()
            try {
                cosObjectInput.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
