package com.zeekr.testclew.tools;

import org.springframework.stereotype.Service;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;


import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
/**
 * @ClassName AESUtils
 * @Description TODO
 * @Author xibu
 * @Date AESUtils 18:25
 * @Version 1.0
 **/
@Service("AES")
public class AESUtils implements Symmetry{
    @Override
    public String generateKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(GatewayConstant.AES_KEY_ALGORITHM);
        keyGenerator.init(GatewayConstant.AES_KEY_LENGTH);
        SecretKey secretKey = keyGenerator.generateKey();

        return byteToHexString(secretKey.getEncoded());
    }

    @Override
    public String encrypt(String content, String key) throws Exception {
        Cipher cipher = Cipher.getInstance(GatewayConstant.AES_ALGORITHMS, new BouncyCastleProvider());
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getBytes(), GatewayConstant.AES_KEY_ALGORITHM));
        byte[] encodeByte = cipher.doFinal(content.getBytes(StandardCharsets.UTF_8));

        // 加密完毕，进行base64编码
        return Base64.encodeBase64String(encodeByte);
    }

    @Override
    public String encrypt(byte[] content, String key) throws Exception {
        Cipher cipher = Cipher.getInstance(GatewayConstant.AES_ALGORITHMS, new BouncyCastleProvider());
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), GatewayConstant.AES_KEY_ALGORITHM));
        byte[] encodeByte = cipher.doFinal(content);

        // 加密完毕，进行base64编码
        return Base64.encodeBase64String(encodeByte);
    }

    @Override
    public String decrypt(String encryptContent, String key) throws Exception {
        byte[] decodeBase64 = Base64.decodeBase64(encryptContent);
        Cipher cipher = Cipher.getInstance(GatewayConstant.AES_ALGORITHMS, new BouncyCastleProvider());
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), GatewayConstant.AES_KEY_ALGORITHM));
        byte[] decodeBytes = cipher.doFinal(decodeBase64);

        return new String(decodeBytes);
    }

    @Override
    public String decrypt(byte[] encryptContent, byte[] key) throws Exception {
        Cipher cipher = Cipher.getInstance(GatewayConstant.AES_ALGORITHMS, new BouncyCastleProvider());
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, GatewayConstant.AES_KEY_ALGORITHM));
        byte[] decodeBytes = cipher.doFinal(encryptContent);

        return new String(decodeBytes);
    }

    @Override
    public Boolean verifySign(String sign, String decryptedData, String decrAesKey) throws Exception {
        // 解密签名串
        String decrSign = decrypt(sign, decrAesKey);
        // 验签
        String encrypted = MD5Utils.getMD5(decryptedData);
        if (!StringUtils.upperCase(encrypted).equals(StringUtils.upperCase(decrSign))) {
            return false;
        }

        return true;
    }

    public static void main(String[] args) throws Exception {
        AESUtils aesUtils = new AESUtils();
//        // 加密报文，生成data
//        String origin = "{\"test\":\"test\"}";
//        String aesKey = aesUtils.generateKey();
//        String encryptedContent = aesUtils.encrypt(origin.getBytes(StandardCharsets.UTF_8), aesKey);
//        System.out.println("encrypted content: " + encryptedContent);
//        //生成sign
//        String md5 = MD5Utils.getMD5(origin);
//        String encryptedSign = aesUtils.encrypt(md5.getBytes(StandardCharsets.UTF_8), aesKey);
//        System.out.println("sign: " + encryptedSign);
//        // 生成加密的aeskey
//        String rsaPublicKey = "xxxxxxxxxxx"; //颁发给你的rsa公钥
//        System.out.println("encrypted aes key for rsa-public encrypt result:" + RSAUtils.encryptByPublicKey(aesKey.getBytes(), rsaPublicKey));
//
//        ////// 返回值解密 /////
//        //返回给商户端的加密的aeskey
//        String encryptedAesKey = "xxxxxxxxxxxxx";
//        // 解密aes key
//        String decryptedAesKey = RSAUtils.decryptByPublicKey(encryptedAesKey, rsaPublicKey);
        String decryptedAesKey = "1bdd04cc1b50a8b3f6fdba52277c8802";
//        System.out.println("decrypted aes key for rsa-public decrypt result:" + decryptedAesKey);
        // 解密data
        String encryptedData = "lBTFDvR5E3m1hh8btLlN9/1S8/qn1Nh9cRD6OT5qtAKjMAqR+jNbEZZV2sTMDgf+/aTZEJy3dwVLhQiGBmhyH0YpDTscn8bpkuOau+vPGXJefP3d0747QLtqN8Jj4PXcj2cMfPKTcHOUrSk5YAAzv8cUCuoSB7FVnawcfKIyu2d6e2xXqzEgVUCWLGsAE4lzr7H8PxiFPMDwVdVE7ufA1t";
        String decryptedContent = aesUtils.decrypt(encryptedData, decryptedAesKey);
        System.out.println("decrypted data result: " + decryptedContent);
    }
}
