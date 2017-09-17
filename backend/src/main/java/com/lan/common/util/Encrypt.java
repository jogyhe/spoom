package com.lan.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;

/**
 * package com.lan.common.util
 *
 * @author lanzongxiao
 * @date 2017/9/16
 */
public class Encrypt {
    private final static Logger logger = LoggerFactory.getLogger(Encrypt.class);

    private static String key = "3839484782674830";

    /**
     * AES加密为base 64 code
     *
     * @param content 待加密的内容
     * @return 加密后的base 64 code
     * @throws Exception
     */
    public static String aesEncrypt(String content) throws Exception {
        logger.info("content to be encrypted is: " + content);
        return base64Encode(aesEncryptToBytes(content));
    }

    /**
     * AES解密 返回解密的内容
     *
     * @param encryptString 待解密的String，base64编码
     * @return
     * @throws Exception
     */
    public static String aesDecrypt(String encryptString) throws Exception {
        return aesDecryptByBytes(base64Decode(encryptString));
    }

    public static void main(String[] args) throws Exception {
        String content = "742d2e7b871b4394ad91d9b796455b38#13#1505713689490";
        System.out.println("加密前：" + content);
        String result = aesEncrypt(content);
        System.out.println("加密后：" + result);
        System.out.println("解密后：" + aesDecrypt(result));
    }

    /**
     * base 64 encode
     *
     * @param bytes 待编码的byte[]
     * @return 编码后的base 64 code
     */
    public static String base64Encode(byte[] bytes) {
        String encrypted = new BASE64Encoder().encode(bytes);
        return encrypted.replaceAll("\n|\r", "");
    }

    /**
     * base 64 decode
     *
     * @param base64Code 待解码的base 64 code
     * @return 解码后的byte[]
     * @throws Exception
     */
    public static byte[] base64Decode(String base64Code) throws Exception {
        return base64Code.isEmpty() ? null : new BASE64Decoder().decodeBuffer(base64Code);
    }

    /**
     * 获取byte[]的md5值
     *
     * @param bytes byte[]
     * @return md5
     * @throws Exception
     */
    public static byte[] md5(byte[] bytes) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(bytes);
        return md.digest();
    }

    /**
     * 获取字符串md5值
     *
     * @param msg
     * @return md5
     * @throws Exception
     */
    public static byte[] md5(String msg) throws Exception {
        return msg.isEmpty() ? null : md5(msg.getBytes());
    }

    /**
     * 结合base64实现md5加密
     *
     * @param msg 待加密字符串
     * @return 获取md5后转为base64
     * @throws Exception
     */
    public static String md5Encrypt(String msg) throws Exception {
        return msg.isEmpty() ? null : base64Encode(md5(msg));
    }

    /**
     * AES加密
     *
     * @param content 待加密的内容
     * @return 加密后的byte[]
     * @throws Exception
     */
    public static byte[] aesEncryptToBytes(String content) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(getKey().getBytes("utf-8"), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        return cipher.doFinal(content.getBytes("utf-8"));
    }

    /**
     * AES解密
     *
     * @param encryptBytes 待解密的byte[]
     * @return 解密后的String
     * @throws Exception
     */
    public static String aesDecryptByBytes(byte[] encryptBytes) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(getKey().getBytes("utf-8"), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        byte[] decryptBytes = cipher.doFinal(encryptBytes);
        return new String(decryptBytes);
    }

    public static String getKey() {
        return key;
    }

    public static void setKey(String key) {
        Encrypt.key = key;
    }
}
