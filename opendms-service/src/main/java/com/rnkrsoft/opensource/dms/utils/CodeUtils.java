package com.rnkrsoft.opensource.dms.utils;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class CodeUtils {
    //产生6位验证码
    public static String generate(int len) {
        String res = "";
        int num;
        for (int i = 0; i < len;i++) {
            num = new Random(System.nanoTime()).nextInt(10);
            res += num;
        }
        return res;
    }

    /**
     * 随机生成指定长度的验证码
     * @param len 验证码长度
     * @param letterFlag 是否可以有字母，true：可以有字母，false：不能有字母
     * @return 生成的验证码
     */
    public static String generateCode(int len, boolean letterFlag){
        return generateCode(len, letterFlag, true);
    }

    /**
     * 随机生成指定长度的验证码
     * @param len 验证码长度
     * @param letterFlag 是否可以有字母，true：可以有字母，false：不能有字母
     * @param capitalFlag 是否区分大小写，true：区分大小写，false：不区分大小写
     * @return 生成的验证码
     */
    public static String generateCode( int len, boolean letterFlag, boolean capitalFlag){
        Set<Object> excludeSet = new HashSet<Object>();
        excludeSet.add('o');
        excludeSet.add('O');
        excludeSet.add('i');
        excludeSet.add('I');
        excludeSet.add('l');
        excludeSet.add('L');
        return generateCode(len, letterFlag, capitalFlag, excludeSet);
    }

    /**
     * 随机生成指定长度的验证码(排除指定的字符)
     * @param len 验证码长度
     * @param letterFlag 是否可以有字母，true：可以有字母，false：不能有字母
     * @param capitalFlag 是否区分大小写，true：区分大小写，false：不区分大小写
     * @param excludeSet 排除指定的字符串
     * @return
     */
    public static String generateCode(int len, boolean letterFlag, boolean capitalFlag, Set<Object> excludeSet){
        String code = "";
        Random random = new Random();
        for (int i = 0; i < len; i++){
            int key = random.nextInt(3);
            switch (key){
                case 0:
                    int number = random.nextInt(10);
                    if (excludeSet.contains(number)){
                        i = i - 1;
                    }else {
                        code += number;
                    }
                    break;
                case 1:
                    if (letterFlag && !capitalFlag) {
                        char lowerCase = (char) (random.nextInt(26) + 97);
                        if (excludeSet.contains(lowerCase)){
                            i = i - 1;
                        }else {
                            code += lowerCase;
                        }
                    }else {
                        i = i - 1;
                    }
                    break;
                case 2:
                    if (letterFlag) {
                        char upperCase = (char) (random.nextInt(26) + 65);
                        if (excludeSet.contains(upperCase)){
                            i = i - 1;
                        }else {
                            code += upperCase;
                        }
                    }else {
                        i = i - 1;
                    }
                    break;
            }
        }

        return code;
    }
}
