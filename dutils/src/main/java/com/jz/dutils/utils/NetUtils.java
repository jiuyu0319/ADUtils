package com.jz.dutils.utils;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.util.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import Decoder.BASE64Encoder;

public class NetUtils {


    /***
     * 解密返回的参数
     * @param source
     * @return
     */
    public static String decryptParams(String source) {
        try {
            byte[] base64 = Base64.decode(source, Base64.DEFAULT);
            byte[] bytes = decryptRC4(base64, "b9Mk%c@qgh");
            byte[] base64_2 = Base64.decode(new String(bytes), Base64.DEFAULT);
            return new String(base64_2);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 将数据源进行RC4解密
     * @param data
     * @return
     */
    public static byte[] decryptRC4(byte[] data, String key) {
        if (data == null) {
            return null;
        }
        return RC4Base(data, key);
    }


    /**
     * 加密請求的串
     *
     * @param source
     * @return
     */
    public static String encryptParams(String source) {
        try {
            String base64 = new BASE64Encoder().encode(source.getBytes());
            return encryptRC4(base64, "b9Mk%c@qgh");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    /**
     * 将数据源进行RC4加密
     * @param encryString
     * @param key
     * @return
     */
    public static String encryptRC4(String encryString, String key) {
        byte[] b = encry_RC4_string(encryString, key);
        String res = new BASE64Encoder().encode(b);
        return res;
    }

    public static  byte[] encry_RC4_string(String data, String key) {
        if (data == null || key == null) {
            return null;
        }
        return encry_RC4_byte(data, key);
    }
    public static  byte[] encry_RC4_byte(String data, String key) {
        if (data == null) {
            return null;
        }
        byte b_data[] = data.getBytes();
        return RC4Base(b_data, key);
    }


    public static   byte[] initKey(String aKey) {
        byte[] b_key = aKey.getBytes();
        byte state[] = new byte[256];

        for (int i = 0; i < 256; i++) {
            state[i] = (byte) i;
        }
        int index1 = 0;
        int index2 = 0;
        if (b_key == null || b_key.length == 0) {
            return null;
        }
        for (int i = 0; i < 256; i++) {
            index2 = ((b_key[index1] & 0xff) + (state[i] & 0xff) + index2) & 0xff;
            byte tmp = state[i];
            state[i] = state[index2];
            state[index2] = tmp;
            index1 = (index1 + 1) % b_key.length;
        }
        return state;
    }

    public static byte[] RC4Base(byte[] input, String mKkey) {
        int x = 0;
        int y = 0;
        byte key[] = initKey(mKkey);
        int xorIndex;
        byte[] result = new byte[input.length];

        for (int i = 0; i < input.length; i++) {
            x = (x + 1) & 0xff;
            y = ((key[x] & 0xff) + y) & 0xff;
            byte tmp = key[x];
            key[x] = key[y];
            key[y] = tmp;
            xorIndex = ((key[x] & 0xff) + (key[y] & 0xff)) & 0xff;
            result[i] = (byte) (input[i] ^ key[xorIndex]);
        }
        return result;
    }

    public static String getUniqueId(Context context){
        String androidID = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        String id = androidID + Build.SERIAL;
        try {
            return toMD5(id);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return id;
        }
    }
    private static String toMD5(String text) throws NoSuchAlgorithmException {
        //获取摘要器 MessageDigest
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        //通过摘要器对字符串的二进制字节数组进行hash计算
        byte[] digest = messageDigest.digest(text.getBytes());

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < digest.length; i++) {
            //循环每个字符 将计算结果转化为正整数;
            int digestInt = digest[i] & 0xff;
            //将10进制转化为较短的16进制
            String hexString = Integer.toHexString(digestInt);
            //转化结果如果是个位数会省略0,因此判断并补0
            if (hexString.length() < 2) {
                sb.append(0);
            }
            //将循环结果添加到缓冲区
            sb.append(hexString);
        }
        //返回整个结果
        return sb.toString();
    }
}
