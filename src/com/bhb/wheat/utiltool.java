package com.bhb.wheat;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class utiltool {

    /**
     * 传入文本内容，返回 SHA-256 串
     *
     * @param strText
     * @return
     */
    public static String  SHA256(final String strText)
    {
        return SHA(strText, "SHA-256");
    }

    /**
     * 传入文本内容，返回 SHA-512 串
     *
     * @param strText
     * @return
     */
    public static String SHA512(final String strText)
    {
        return SHA(strText, "SHA-512");
    }

    /**
     * 字符串 SHA 加密
     *
     * @param
     * @return
     */
    private static String SHA(final String strText, final String strType)
    {
        // 返回值
        String strResult = null;

        // 是否是有效字符串
        if (strText != null && strText.length() > 0)
        {
            try
            {
                // SHA 加密开始
                // 创建加密对象 并傳入加密類型
                MessageDigest messageDigest = MessageDigest.getInstance(strType);
                // 传入要加密的字符串
                messageDigest.update(strText.getBytes());
                // 得到 byte 類型结果
                byte byteBuffer[] = messageDigest.digest();

                // 將 byte 轉換爲 string
                StringBuffer strHexString = new StringBuffer();
                // 遍歷 byte buffer
                for (int i = 0; i < byteBuffer.length; i++)
                {
                    String hex = Integer.toHexString(0xff & byteBuffer[i]);
                    if (hex.length() == 1)
                    {
                        strHexString.append('0');
                    }
                    strHexString.append(hex);
                }
                // 得到返回結果
                strResult = strHexString.toString();
            }
            catch (NoSuchAlgorithmException e)
            {
                e.printStackTrace();
            }
        }
        return strResult;
    }


    public static byte[] AES_CBC_Encrypt(byte[] content, byte[] keyBytes){

        try{
            KeyGenerator keyGenerator=KeyGenerator.getInstance("AES");
            keyGenerator.init(128, new SecureRandom(keyBytes));
            SecretKey key=keyGenerator.generateKey();
            Cipher cipher=Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] result=cipher.doFinal(content);
            return result;
        }catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.println("exception:"+e.toString());
        }
        return null;
    }

    public static byte[] AES_CBC_Decrypt(byte[] content, byte[] keyBytes){

        try{
            KeyGenerator keyGenerator=KeyGenerator.getInstance("AES");
            keyGenerator.init(128, new SecureRandom(keyBytes));//key长可设为128，192，256位，这里只能设为128
            SecretKey key=keyGenerator.generateKey();
            Cipher cipher=Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] result=cipher.doFinal(content);
            return result;
        }catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.println("exception:"+e.toString());
        }
        return null;
    }

    public static String byteToHexString(byte[] bytes) {
        StringBuffer sb = new StringBuffer(bytes.length);
        String sTemp;
        for (int i = 0; i < bytes.length; i++) {
            sTemp = Integer.toHexString(0xFF & bytes[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

}
