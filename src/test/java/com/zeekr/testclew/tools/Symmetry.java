package com.zeekr.testclew.tools;

/**
 * @author xibu
 * @version 1.0.0
 * @ClassName Symmetry.java
 * @Description TODO
 * @createTime 2022年10月08日 17:28:00
 */
public interface Symmetry {


    /**
     * 生成key
     */
    String generateKey() throws Exception;

    /**
     * 加密
     *
     * @param content 需要加密的内容
     * @param key     秘钥
     * @return 经过BASE64编码的解密结果
     */
    String encrypt(String content, String key) throws Exception;

    /**
     * 加密
     *
     * @param content 需要加密的内容
     * @param key     秘钥
     * @return 经过BASE64编码的解密结果
     */
    String encrypt(byte[] content, String key) throws Exception;

    /**
     * 解密
     *
     * @param encryptContent 需要解密的内容(BASE64编码)
     * @param key            秘钥
     */
    String decrypt(String encryptContent, String key) throws Exception;

    /**
     * 解密
     *
     * @param encryptContent 需要解密的内容(BASE64编码)
     * @param key            秘钥
     */
    String decrypt(byte[] encryptContent, byte[] key) throws Exception;

    /**
     * 验签
     *
     * @param sign
     * @param decryptedData
     * @param decrAesKey
     * @return
     */
    Boolean verifySign(String sign, String decryptedData, String decrAesKey) throws Exception;

    /**
     * 将字节数组生成16进制形式的字符串
     *
     * @param bytes
     * @return
     */
    default String byteToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            int v = bytes[i] & 0xff;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                sb.append("0");
            }
            sb.append(hv);
        }

        return sb.toString();
    }
}
