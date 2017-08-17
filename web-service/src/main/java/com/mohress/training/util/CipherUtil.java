package com.mohress.training.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * 信息的加解密工具
 */
public class CipherUtil {

    private static String strKey = "privilege.yzy";
    private static String mode = "AES/CBC/PKCS5Padding";
    private static String cipherIV = "0102030405060708";

    /**
     * 加密
     *
     * @param strIn String 需要加密的信息
     * @return String 密文
     */
    public static String encrypt(String strIn) throws Exception {
        SecretKeySpec skeySpec = getKey(strKey);

        Cipher cipher = Cipher.getInstance(mode);
        IvParameterSpec iv = new IvParameterSpec(cipherIV.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        byte[] encrypted = cipher.doFinal(strIn.getBytes());
        return new BASE64Encoder().encode(encrypted);
    }

    /**
     * 解密
     *
     * @param strIn String 密文
     * @return String 明文
     */
    public static String decrypt(String strIn) throws Exception {
        SecretKeySpec skeySpec = getKey(strKey);
        Cipher cipher = Cipher.getInstance(mode);

        IvParameterSpec iv = new IvParameterSpec(cipherIV.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
        byte[] encrypted1 = new BASE64Decoder().decodeBuffer(strIn);

        byte[] original = cipher.doFinal(encrypted1);
        return new String(original);
    }

    /**
     * 格式化key
     *
     * @param strKey String 密钥
     * @return String 格式化后的密钥
     */
    private static SecretKeySpec getKey(String strKey) {
        byte[] arrBTmp = strKey.getBytes();
        byte[] arrB = new byte[16]; // 创建一个空的16位字节数组（默认值为0）

        for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
            arrB[i] = arrBTmp[i];
        }

        return new SecretKeySpec(arrB, "AES");
    }

    public static String decryptName(String encryptedName){
        //todo 待解密
        return "";
    }

}
