package com.zeekr.testclew.seckey;

import com.zeekr.testclew.tools.AESUtils;
import com.zeekr.testclew.tools.MD5Utils;
import com.zeekr.testclew.tools.RSAUtils;
import org.testng.annotations.Test;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
/**
 * @ClassName CreateAppKey
 * @Description TODO
 * @Author xibu
 * @Date CreateAppKey 16:51
 * @Version 1.0
 **/
public class CreateAppKeyTest {

    @Test
    public  void  testCreateAppkeyh() throws Exception{
        AESUtils aesUtils = new AESUtils();
        // 加密报文，生成data
//        String origin = "{\"accountId\":1392727747172073472}";
        String origin = "{}";

        //appKey
        String appKey = "NjAwMDAwMDc5NDc2MTg5NQ==";
        //RSA公钥
        String rsaPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCmFeK7Y15kd/H+JwoLpNaMCLUToe3crQ22ibqa906ulqrLe72VyUFvJQJYY7sgVDxXe2WnvEO2WZO2yeYTqiyDUy5TKWjkpRE4bLQEI15J6rZvTSL3ZmB25pXuHiGcmaveyXtXYuSdxy6AqIjnLfKQCnHEsI1bNNO1l/S03uHlZwIDAQAB";
        //secret
        String secret = "cU5t3vNNahtbvLmnj9GhJhyFQnpumHrKuzcsiFg0/4A=";

        String testGatewayUrl = "http://openapi-test.lkhaowu.com";
        String methodUrl = "/zeekrlife-rpc-order/v1/tmall/store/batchSave";

        String aesKey = aesUtils.generateKey();
        String encryptedContent = aesUtils.encrypt(origin.getBytes(StandardCharsets.UTF_8), aesKey);
        System.out.println("encrypted content: " + encryptedContent);
        //生成sign
        String md5 = MD5Utils.getMD5(origin);
        String encryptedSign = aesUtils.encrypt(md5.getBytes(StandardCharsets.UTF_8), aesKey);
        System.out.println("sign: " + encryptedSign);

        // 生成加密的aeskey
        String key = RSAUtils.encryptByPublicKey(aesKey.getBytes(), rsaPublicKey);
        System.out.println("encrypted aes key for rsa-public encrypt result:" +key );

        //构建入参
        Map<String,String> map=new HashMap();
        map.put("data",encryptedContent);
        map.put("key",key);
        String str = JSONUtil.toJsonStr(map);
        //发送请求
        String response = HttpRequest.post(testGatewayUrl + methodUrl)
                .header("sign", encryptedSign)
                .header("Content-Type", "application/json")
                .header("appKey", appKey)
                .header("appSecret",secret)
                .body(str).execute().body();

        //返回值解密
        System.out.println("response:"+response);
        String responseData = JSONUtil.parseObj(response).get("data").toString();
        String responseKey = JSONUtil.parseObj(response).get("key").toString();
        String decryptedAesKey = RSAUtils.decryptByPublicKey(responseKey, rsaPublicKey);
        String decryptedContent = aesUtils.decrypt(responseData, decryptedAesKey);
        System.out.println("decrypted data result: " + decryptedContent);
    }

}
