package com.mydream.utils;

/**
 * Created by  lpw'ASUS on 2018/7/26.
 */
public class StringUtil {

    /**
     * 判断字符串是否为空
     * @date: 2018年7月26日
     * @param string
     * @return true 空，false 不为空
     */
    public static boolean isEmpty(String string) {
        if (string == null || string.trim().length() == 0)
            return true;
        else
            return false;
    }

    /**
     * 数据库列名转化为属性名,如DEAL_ID=dealId;
     * 不能保证完全正确，如DBUTIL不会智能的转化为DBUtil,而会转化为dbutil,
     * @date: 2018年7月26日
     * @param DBName
     * @return 转化后的属性名
     */
    public static String formatDBNameToVarName(String DBName) {
        StringBuilder result = new StringBuilder("");
        // 以"_"分割
        String[] DBNameArr = DBName.split("_");
        for (int i = 0, j = DBNameArr.length; i < j; i++) {
            // 获取以"_"分割后的字符数组的每个元素的第一个字母，第一个小写，其他的大写
            if(i==0){
                result.append((DBNameArr[i].charAt(0)+"").toLowerCase());
            }else{
                result.append((DBNameArr[i].charAt(0)+"").toUpperCase());
            }
            // 将其他字符转换成小写
            result.append(DBNameArr[i].substring(1).toLowerCase());
        }
        char c0 = result.charAt(0);
        if (c0 >= 'A' && c0 <= 'Z')
            c0 = (char) (c0 + 'a' - 'A');
        result.setCharAt(0, c0);
        return result.toString();
    }

    /**
     * 获取数据库类型对应的java类型
     * @date: 2018年7月26日
     * @param typeName
     * @param columnSize
     * @param decimalDigits
     * @return
     */
    public static String getJavaType(String typeName,String columnSize,String decimalDigits){
        String javaType="";
        if(("INT".equals(typeName)||"BIGINT".equals(typeName)) && Integer.parseInt(columnSize)<=10){
            javaType= "Integer";
        }
        else if(("INT".equals(typeName)||"BIGINT".equals(typeName)) && Integer.parseInt(columnSize)>10){
            javaType= "Long";
        }
        else if("DECIMAL".equals(typeName)){
            javaType= "java.math.BigDecimal";
        }
        else if("NUMBER".equals(typeName) && Integer.parseInt(columnSize)<=9){
            javaType= "Integer";
        }
        else if("NUMBER".equals(typeName) && Integer.parseInt(columnSize)>9 && Integer.parseInt(columnSize) <= 17 ){
            javaType= "Long";
        }
        else if("NUMBER".equals(typeName) && Integer.parseInt(columnSize) >= 18 ){
            javaType="java.math.BigDecimal";
        }
        else if("TEXT".equals(typeName)||"CHAR".equals(typeName)||"VARCHAR".equals(typeName)||"VARCHAR2".equals(typeName)||"DATETIME".equals(typeName)){
            javaType= "String";
        }
        else if(!isEmpty(decimalDigits)&&Integer.parseInt(decimalDigits)>0){
            javaType= "java.math.BigDecimal";
        }
        else if(("TINYBLOB".equals(typeName))||("BLOB".equals(typeName))||("MEDIUMBLOB".equals(typeName))||("LONGBLOB".equals(typeName))){
            javaType="byte[]";
        }
        return javaType;
    }

    /**
     * 首字母大写
     * @date: 2018年7月26日
     * @param s
     * @return
     */
    public static String firstBig(String s){
        if(s==null || s.length()<=1){
            return s;
        }
        return s.substring(0,1).toUpperCase()+(s.length()>1?s.substring(1):"");
    }

    /**
     * 字段类型拆箱操作，包装类转为基本数据类型
     * @param type
     * @return
     */
    public static String devanning(String type){
        String result="";
        if("Integer".equals(type)){
            result="int";
        }else if("Double".equals(type)){
            result="double";
        }else if("Boolean ".equals(type)){
            result="boolean";
        }else if("Float".equals(type)){
            result="float";
        }else if("Short".equals(type)){
            result="short";
        }else if("Long".equals(type)){
            result="long";
        }else if("Byte".equals(type)){
            result="byte";
        }else if("Character".equals(type)){
            result="char";
        }else{
            result=type;
        }
        return result;
    }

    /**
     * 获取数据表类型对应的jdbc类型
     * @param typeName
     * @return
     */
    public static String getJdbcType(String typeName){
        String jdbcType="";
        if("INT".equals(typeName)||"BIGINT".equals(typeName)){
            jdbcType="INTEGER";
        }else if("VARCHAR".equals(typeName)||"TEXT".equals(typeName)){
            jdbcType="VARCHAR";
        }else if("DATETIME".equals(typeName)||"DATE".equals(typeName)){
            jdbcType="DATE";
        }else if("DECIMAL".equals(typeName)){
            jdbcType="DECIMAL";
        }
        return jdbcType;
    }
}
