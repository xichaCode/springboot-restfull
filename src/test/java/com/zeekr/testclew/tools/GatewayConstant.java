package com.zeekr.testclew.tools;
import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * @ClassName GatewayConstant
 * @Description TODO
 * @Author xibu
 * @Date GatewayConstant 17:02
 * @Version 1.0
 **/
public class GatewayConstant {
    /**
     * appKey
     */
    public final static String HEADER_APP_KEY = "appKey";

    /**
     * appSecret
     */
    public final static String HEADER_APP_SECRET = "appSecret";

    /**
     * 加密后的签名串
     */
    public final static String HEADER_SIGN = "sign";

    public static final String ENCR_DATA = "data";

    public static final String ENCR_KEY = "key";

    public static final String METHOD_GET = "GET";

    public static final String METHOD_POST = "POST";

    public static final String APPLICATION_JSON = "application/json";

    /**
     * 加密算法AES
     */
    public static final String RSA_KEY_ALGORITHM = "RSA";

    /**
     * 算法名称/加密模式/数据填充方式
     * 默认：RSA/ECB/PKCS1Padding
     */
    public static final String RSA_ALGORITHMS = "RSA/ECB/PKCS1Padding";

    /**
     * RSA最大加密明文大小
     */
    public static final int RSA_MAX_ENCRYPT_BLOCK = 117;

    /**
     * RSA最大解密密文大小
     */
    public static final int RSA_MAX_DECRYPT_BLOCK = 128;

    /**
     * RSA 位数 如果采用2048 上面最大加密和最大解密则须填写:  245 256
     */
    public static final int RSA_INITIALIZE_LENGTH = 1024;

    /**
     * 加密算法AES
     */
    public static final String AES_KEY_ALGORITHM = "AES";

    /**
     * 加密算法DES
     */
    public static final String DES_KEY_ALGORITHM = "DES";

    /**
     * 加密算法3DES
     */
    public static final String TRIPLE_DES_KEY_ALGORITHM = "DESede";

    /**
     * key的长度，Wrong key size: must be equal to 128, 192 or 256
     * 传入时需要16、24、36
     */
    public static final Integer AES_KEY_LENGTH = 128;

    /**
     * 算法名称/加密模式/数据填充方式
     * 默认：AES/ECB/PKCS5Padding
     */
    public static final String AES_ALGORITHMS = "AES/ECB/PKCS5Padding";

    public static final String DES_ALGORITHMS = "DES/ECB/PKCS5Padding";

    public static final String TRIPLE_DES_ALGORITHMS = "DESede/ECB/PKCS5Padding";

    public static final String CACHE_REQUEST_BODY_OBJECT_KEY = "cachedRequestBodyObject";

    public static final String ERROR_MSG_PREFIX = "error: exception occurred 10000 error code ";

    public static final String INJECTION_MSG_ATTACK = "error: XSS Injection Attacked ";

    public static final String SIGN_DATA_FAILED = "sign data failed: ";

    public static final String CONTENT_TYPE = "Content-Type";

    public static final Joiner JOINER = Joiner.on(StringUtils.EMPTY);

    public static final String ENCRYPT_STRENGTH_WHITE_LIST = "白名单";

    public static final String ENCRYPT_STRENGTH_VERIFY_SIGN = "只验签";

    public static final String ENCRYPT_STRENGTH_ENCRYPT_DECRYPT = "加解密";

    public static final Map<String, Symmetry> SYMMETRY_MAP = Maps.newHashMap();

    public static final String BLACK_IP_PREFIX = "ip:black:";

    public static final String NORMAL_IP_PREFIX = "ip:normal:";

    public static final Integer REQUEST_MAX_COUNT = 1500;

    public static final Integer REQUEST_UNIT_TIME = 60 * 1000;
}
