package com.zeekr.testclew.tools;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
/**
 * @ClassName RSAUtils
 * @Description TODO
 * @Author xibu
 * @Date RSAUtils 16:47
 * @Version 1.0
 **/
public class RSAUtils {
    /**
     * 私钥解密
     *
     * @param encryptedData 已加密数据(BASE64编码)
     * @param privateKey    私钥(BASE64编码)
     */
    public static String decryptByPrivateKey(String encryptedData, String privateKey) throws Exception {
        byte[] dataBytes = Base64.decodeBase64(encryptedData);
        byte[] keyBytes = Base64.decodeBase64(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(GatewayConstant.RSA_KEY_ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(GatewayConstant.RSA_ALGORITHMS);
        cipher.init(Cipher.DECRYPT_MODE, privateK);
        byte[] decodeBytes = encryptAndDecryptOfSubsection(dataBytes, cipher, GatewayConstant.RSA_MAX_DECRYPT_BLOCK);

        return new String(decodeBytes);
    }

    /**
     * 私钥加密
     *
     * @param data       需要加密的数据
     * @param privateKey 私钥(BASE64编码)
     * @return 经过BASE64编码的加密结果
     */
    public static String encryptByPrivateKey(byte[] data, String privateKey) throws Exception {
        byte[] keyBytes = Base64.decodeBase64(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(GatewayConstant.RSA_KEY_ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(GatewayConstant.RSA_ALGORITHMS);
        cipher.init(Cipher.ENCRYPT_MODE, privateK);
        byte[] encodeBytes = encryptAndDecryptOfSubsection(data, cipher, GatewayConstant.RSA_MAX_ENCRYPT_BLOCK);

        //加密完毕，需要进行base64编码
        return Base64.encodeBase64String(encodeBytes);
    }

    /**
     * 公钥加密
     *
     * @param data      待加密数据
     * @param publicKey 公钥(BASE64编码)
     * @return 经过BASE64编码的加密结果
     */
    public static String encryptByPublicKey(byte[] data, String publicKey) throws Exception {
        byte[] keyBytes = Base64.decodeBase64(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(GatewayConstant.RSA_KEY_ALGORITHM);
        PublicKey publicK = keyFactory.generatePublic(x509KeySpec);
        Cipher cipher = Cipher.getInstance(GatewayConstant.RSA_ALGORITHMS);
        cipher.init(Cipher.ENCRYPT_MODE, publicK);
        byte[] encodeBytes = encryptAndDecryptOfSubsection(data, cipher, GatewayConstant.RSA_MAX_ENCRYPT_BLOCK);

        //加密完毕，需要进行base64编码
        return Base64.encodeBase64String(encodeBytes);
    }

    /**
     * 公钥解密
     *
     * @param encryptedData 已加密数据(BASE64编码)
     * @param publicKey    私钥(BASE64编码)
     */
    public static String decryptByPublicKey(String encryptedData, String publicKey) throws Exception {
        byte[] dataBytes = Base64.decodeBase64(encryptedData);
        byte[] keyBytes = Base64.decodeBase64(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(GatewayConstant.RSA_KEY_ALGORITHM);
        PublicKey publicK = keyFactory.generatePublic(x509KeySpec);
        Cipher cipher = Cipher.getInstance(GatewayConstant.RSA_ALGORITHMS);
        cipher.init(Cipher.DECRYPT_MODE, publicK);
        byte[] decodeBytes = encryptAndDecryptOfSubsection(dataBytes, cipher, GatewayConstant.RSA_MAX_DECRYPT_BLOCK);

        return new String(decodeBytes);
    }

    /**
     * 分段进行加密、解密操作
     *
     * @param data
     * @param cipher
     * @param encryptBlock
     * @return
     * @throws Exception
     */
    private static byte[] encryptAndDecryptOfSubsection(byte[] data, Cipher cipher, int encryptBlock) throws Exception {
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > encryptBlock) {
                cache = cipher.doFinal(data, offSet, encryptBlock);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * encryptBlock;
        }
        byte[] toByteArray = out.toByteArray();
        out.close();

        return toByteArray;
    }

    public static void main(String[] args) throws Exception {
//        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
//        keyGen.initialize(1024);
//        KeyPair pair = keyGen.generateKeyPair();
//
//        byte[] publicBytes = pair.getPublic().getEncoded();
//        byte[] privateBytes = pair.getPrivate().getEncoded();
//
//        String publicKey = Base64.encodeBase64String(publicBytes);
//        String privateKey = Base64.encodeBase64String(privateBytes);
//        System.out.println("public key: " + publicKey);
//        System.out.println("private key: " + privateKey);
        String rsaPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQD8ygRJphj639hlBM/vNjre9IvdWnWClkzm+D5Pf9/WsfNpa5pPNF5PwU2o17ikKEBdM9lTwv64dDi3lcZDy8Ec2RkhiC1t0plXXIT69pLSxnT7oFq33rOexcxNEzSHtZv9A3P0SMYN7T7/LfA+Yi4nKPgMc2rjHc4JjIZ9IAuL8QIDAQAB";
//        String rsaPrivateKey ="MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAPzKBEmmGPrf2GUEz+82Ot70i91adYKWTOb4Pk9/39ax82lrmk80Xk/BTajXuKQoQF0z2VPC/rh0OLeVxkPLwRzZGSGILW3SmVdchPr2ktLGdPugWrfes57FzE0TNIe1m/0Dc/RIxg3tPv8t8D5iLico+AxzauMdzgmMhn0gC4vxAgMBAAECgYEA67WfG+Fhx4Oz7jX3gXEKodxbElKH4fUtzexJha2R1Upm0rS/1Pzat/lh5X8IryDNK1ruWZkoFGxiemSQ06SI8ffdbP4yzpImDB7/z4ASl+MYHcZEiYb8/mqz64E748QegPAwScjN89m/GncGWDLDEqZCy/xOyfNBtfAfMN0oJkECQQD/+VsYeVGL277CkzeV2kZb8e3bqoZIK+5NsCoB0ufr9HV5qETgpKkFAZH944dKUOJRYe2jf17KeUT+wiOJb8qpAkEA/NCUB2JRDF6Q/DDM+/YEb03tK5ZPLKQHHt+jrUHII2240UnG7/NkxBl/ra5zyMmE5fl87VeJBpCW2ZQ8lKGMCQJBAOOONFqEd2cC2gm1wEN6gPP6Fb8jUC7WxwnPYviuGrkbYFffrCOcnhkyNSvkWoBJeXhpu02OzKDov9pOkjL668kCQQDUyNlOvblpp85v9MMS7qYc6IHORdytvgEgQysIEG3bI+aCgI4oN7uAMdX84PAi9zocNjinqx1xoRDWYKX8k1vBAkB7gnDL4o55MMZCj+hnasE8OYrWwylpamwKCd5lBOTFTvEmC2N1P1IMGjWXTtmsRz+HRg0NFM6O4+UQZPWuUBrc";
//        String aesKey = AESUtils.generateKey();
        String key = "00710b9b389d54f2d986181bb7a4ada7";
//        String encryptedData = "FlfJ/4WYob0luhM031CpvYf1zI2GdQo7KjQHc87ISPT2DK1bKSZgLQu02FEUtHFuaz/tHP+vOiGAypZVVWoQmZ/OXxA6lt05W6fKRL4sOE9CDyM7P/WGjjRy/o70lmu7R2W6gGHgkW4VdCvZCs56ypUQ/csLhoVogEOQfF6JK3cmpI1q5IqqNNvpvUvAPVyHNNzKTHG//9DalaP7osYd/1bM";
//        System.out.println("aes key: " + aesKey);
//        System.out.println("encrypted key for rsa-public encrypt result:" + decryptByPrivateKey("GaQzKmMd98zQPmlaCt6iEW7wJ0kPjhz4hYi09gFoI7Rh5jOxK2cCHnN6VMejftYRD4PR7H+Pa8PGOOg+L50L1z/V6UNpQKPpBimOzffIgO56F5ngX+4YnIgPusLwxLzt/5nn+LS/GUX0JeQ3m+0pPZDuUEtAqRIpJORBeUSf+pE=", rsaPrivateKey));

        String encryptedAesKey = "8V2LdFbVGpzpH0YjxmtdWuMWeNqz3gpdn5x9YoGKgl/ltUOTNQxoqAQcIJALIdeLvXqWEvQ5kXY2I2qGaV05zO+KCxzPS5BGVE4jpaRyDIkUsA5fqSzlPq8kDyLa7fGBY3W91OhhfxAU6mgimWTkvt5RB6JP1NxMBqVIry+RwG8=";
        System.out.println("decrypted aes key for rsa-public decrypt result:" + encryptByPublicKey(key.getBytes(StandardCharsets.UTF_8), rsaPublicKey));
    }
}
