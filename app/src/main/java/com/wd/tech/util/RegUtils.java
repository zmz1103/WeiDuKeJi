package com.wd.tech.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 作者: Wang on 2019/2/19 17:47
 * 寄语：加油！相信自己可以！！！
 */


public class RegUtils {

    // 手机号正则
    public static boolean isMobile(String str) {
        Pattern p = null;
        Matcher m = null;
        String s2 = "^1([358][0-9]|4[579]|66|7[0135678]|9[89])[0-9]{8}$";
//        String s2 = "^1[3578]\\d{9}$";
        // 验证手机号

            p = Pattern.compile(s2);
            m = p.matcher(str);
            return m.matches();

    }
    public static boolean rexCheckPassword(String input) {
        // 6-20 位，字母、数字、字符
        //String reg = "^([A-Z]|[a-z]|[0-9]|[`-=[];,./~!@#$%^*()_+}{:?]){6,20}$";
        String regStr = "^(?!^\\\\d+$)(?!^[a-zA-Z]+$)(?!^[_#@]+$).{6,15}$";
        return input.matches(regStr);
    }

}