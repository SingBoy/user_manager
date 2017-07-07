package cn.net.ibingo.common.utils;

import java.util.Random;

public class CodeUtils {

    public static String getCode(String code) {
        Integer num = Integer.valueOf(code);
        num = num + 1;

        if (String.valueOf(num).length() == 1) {
            return "000" + num;
        } else if (String.valueOf(num).length() == 2) {
            return "00" + num;
        } else if (String.valueOf(num).length() == 3) {
            return "0" + num;
        }
        return String.valueOf(num);
    }

    public static void main(String args[]) {

           /* int a[] = new int[10];

            for(int i=0;i<a.length;i++) {

                a[i] = (int)(10*(Math.random()));

                System.out.println(a[i]);

            }*/
        for (int j = 0; j < 1000; j++) {
            System.out.println(getSixNum());
        }
    }

    /**
     * 产生随机的六位数
     * @return
     */
    public static String getSixNum(){
        Random rad=new Random();

        String result  = rad.nextInt(1000000) +"";

        if(result.length()!=6){
            return getSixNum();
        }
        return result;
    }




}