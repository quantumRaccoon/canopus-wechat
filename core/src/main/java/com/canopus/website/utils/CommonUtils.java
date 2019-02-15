package com.canopus.website.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha256Hash;

/**
 * @Author: dai-ych
 * @Date: 2019/2/1 14:50
 * @Description:
 */
@Slf4j
public class CommonUtils {

    /**
     * 生成盐
     */
    public static String generateSalt() {

        return new SecureRandomNumberGenerator().nextBytes().toBase64();
    }


    /**
     * 根据盐, 原始密码加密
     *
     * @param salt             盐
     * @param originalPassword 原始密码
     * @return 加密后的密码
     */
    public static String saltEncrypt(String salt, String originalPassword) {

        return new Sha256Hash(originalPassword, salt, 1024).toBase64();
    }


    /**
     *  生成随机数字
     */
    public static String makeRandomNum(int len) {

        String str = "";
        for (int i = 0; i < len; i++) {
            int x = (int) (Math.random() * 10) % 10;
            str += x;
        }
        return str;
    }
}
