package Composition.Util;

import java.util.regex.*;
public class StringUtil {
    //是否为空：一个or多个空格也是空
    public static boolean isEmpty(String str){
        String pattern="[\\s\\p{Zs}]+";
        if(str.isEmpty()|str.matches(pattern)){
            return true;
        }
        return false;
    }

    public static boolean isNum(String str){
        String pattern="^[0-9]*$";
        if(str.matches(pattern)) return true;
        return false;
    }

    public static boolean isFloat(String str){
        String pattern="^[0-9.]*$";
        if(str.matches(pattern)) return true;
        return false;
    }

    public static boolean isEmail(String str){
        String pattern="^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+\\.com$";
        if(str.matches(pattern))return true;
        return false;
    }
    public static boolean isPhone(String str){
        String pattern="^[0-9]{11}$";
        if(str.matches(pattern))return true;
        return false;
    }

    //XX元-->FLOAT两位
    public static float YtoF(String str){
        str=str.substring(0,str.length()-1);
        return Float.parseFloat(str);
    }

    //Float-->XX元
    public  static String FtoY(Float x){
        String str=String.format("%.2f",x)+"元";
        return str;
    }

    //提取int
    public static int select_int(String str){
        if(isEmpty(str))    return 0;
        String s=str.replaceAll("[^0-9]*","");
        return Integer.parseInt(s);
    }

    public static float select_float(String str){
        if(isEmpty(str)) return 0;
        String s=str.replaceAll("[^0-9.]*","");
        return Float.parseFloat(s);
    }

    public static String order_id(int id){
        String s=Integer.toString(id);
        if(s.length()==1){
            return "000"+s;
        }
        return "00"+s;
    }

//    public static void main(String[] args){
//        System.out.println(order_id(33));
//    }
}
