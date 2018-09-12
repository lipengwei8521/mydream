package com.mydream.backstate.generator.template;

import com.mydream.backstate.response.entity.ResponseBean;
import com.mydream.utils.ConstantString;
import com.mydream.utils.FileUtil;
import com.mydream.utils.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Package: com.mydream.backstate.generator.template
 * Description: 代码生成entity的模板
 * Author: 李鹏伟
 * Date: Created in 2018/8/711:26
 * Company: 公司
 * Copyright: Copyright (C) 2018
 * Version: 0.0.1
 * Modified By: 修改者
 */
public class EntityTemplate {
    /**
     * 代码生成entity的模板的方法
     *
     * @param tableName 表名
     * @param tableComment 字段名称
     * @param filePath 文件路径
     * @param charset 编码格式
     * @param packagePath 包的路径
     * @param author 作者名称
     * @return
     * @throws Exception
     */
    public static void creatEntity(String tableName,String tableComment,String filePath,String charset,
                                        String packagePath,JSONArray json,String author) throws Exception{

        // 错误日志
        Logger logger = LoggerFactory.getLogger(EntityTemplate.class);

        // 创建文件夹
        if(!FileUtil.mkdirs(filePath)){

            logger.error("文件夹["+filePath+"创建不成功");
            System.out.println("文件夹["+filePath+"创建不成功");

        }
        // 转换实体类名称
        String className = StringUtil.firstBig(StringUtil.formatDBNameToVarName(tableName));
        String fileName = className+".java";//生成文件的名称
        // 创建文件输出流
        File entityFile = new File(filePath + File.separator+fileName);
        PrintWriter print = new PrintWriter(new OutputStreamWriter(new FileOutputStream(entityFile),charset));

        // 用来保存信息
        StringBuilder builder = new StringBuilder();
        // 包名
        builder.append("package "+packagePath+";").append("\r\n");
        // 增加类注释
        builder.append("/**").append("\r\n");
        tableComment=tableComment!=null&&!"".equals(tableComment)?tableComment:"";
        if(!"".equals(tableComment)){
            builder.append(" * "+tableComment).append("\r\n");
        }

        builder.append(" * 对应数据库表"+tableName).append("\r\n");
        builder.append(" * @Author "+author).append("\r\n");
        // 注释中添加创建时间
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        builder.append(" * @Date "+df.format(date) ).append("\r\n");
        builder.append(" */").append("\r\n");

        // 增加类名
        builder.append("public class "+className+" { ").append("\r\n");
        int count = json.size();//获取总列数
        // 属性
        for (int i = 0; i < count; i++) {
            JSONObject obj=json.getJSONObject(i);
            // 数据库的列名称
            String columnName = obj.getString("columnName");
            // java类的属性名
            String columnJavaName = obj.getString("columnJavaName");
            // java类属性的注释
            String columnComment = obj.get("columnComment")!=null&&!"".equals(obj.get("columnComment"))?obj.getString("columnComment"):"";
            // java类属性类型
            String columnJavaType = obj.getString("columnJavaType");
            builder.append("    /**").append("\r\n");
            if(!"".equals(columnComment)) builder.append("     * "+columnComment).append("\r\n");
            builder.append("     * "+tableName+"."+columnName).append("\r\n");
            builder.append("     */").append("\r\n");
            builder.append("    private "+ StringUtil.devanning(columnJavaType)+" "+columnJavaName+";").append("\r\n").append("\r\n");
        }

        // 空参构造方法
        builder.append("    /**").append("\r\n");
        builder.append("     * 空参构造函数").append("\r\n");
        builder.append("     */").append("\r\n");
        builder.append("    public "+className+"(){}").append("\r\n").append("\r\n");

        // 全参构造方法
        builder.append("    /**").append("\r\n");
        builder.append("     * 全参构造函数").append("\r\n");
        builder.append("     */").append("\r\n");
        builder.append("    public " + className + "(");
        for(int i=0;i<count;i++){
            JSONObject obj=json.getJSONObject(i);
            // java类的属性名
            String columnJavaName = obj.getString("columnJavaName");
            // java类的属性类型
            String columnJavaType = obj.getString("columnJavaType");
            // 属性说明
            builder.append(StringUtil.devanning(columnJavaType)+" "+columnJavaName+"");
            if(i!=count-1){
                builder.append(",");
            }
        }
        builder.append(") {").append("\r\n");
        for(int i=0;i<count;i++){
            JSONObject obj=json.getJSONObject(i);
            // 属性名
            String columnJavaName = obj.getString("columnJavaName");
            builder.append("        this."+columnJavaName+" = "+columnJavaName+";").append("\r\n");
        }
        builder.append("    }").append("\r\n").append("\r\n");

        // setter和getter方法
        for(int i=0;i<count;i++){
            JSONObject obj=json.getJSONObject(i);
            // 数据库的列名称
            String columnName = obj.getString("columnName");
            // java类的属性名
            String columnJavaName = obj.getString("columnJavaName");
            // java类的属性的注释
            String columnComment = obj.get("columnComment")!=null&&!"".equals(obj.get("columnComment"))?obj.getString("columnComment"):"";
            // 数据库列的类型
            String columnType = obj.getString("columnType");
            // java类属性类型
            String columnJavaType = obj.getString("columnJavaType");

            builder.append("    /**").append("\r\n");
            if(!"".equals(columnComment))  builder.append("     * "+columnComment).append("\r\n");
            builder.append("     * "+tableName+"."+columnName).append("\r\n");
            builder.append("     */").append("\r\n");
            builder.append("    public "+ StringUtil.devanning(columnJavaType)+" get"+StringUtil.firstBig(columnJavaName)+"(){").append("\r\n");
            builder.append("        return "+columnJavaName+";").append("\r\n");
            builder.append("    }").append("\r\n").append("\r\n");

            builder.append("    /**").append("\r\n");
            builder.append("     * "+columnComment).append("\r\n");
            builder.append("     * "+tableName+"."+columnName).append("\r\n");
            builder.append("     */").append("\r\n");
            builder.append("    public void set"+StringUtil.firstBig(columnJavaName)+"("+StringUtil.devanning(columnJavaType)+" "+columnJavaName+"){").append("\r\n");
            builder.append("        this."+columnJavaName+"="+columnJavaName+";").append("\r\n");
            builder.append("    }").append("\r\n").append("\r\n");

        }

        // toString方法
        builder.append("	public String toString(){").append("\r\n");
        builder.append("		return \""+className+"[");
        for(int i=0;i<count;i++){
            JSONObject obj=json.getJSONObject(i);
            // java类的属性名
            String columnJavaName = obj.getString("columnJavaName");
            builder.append(columnJavaName+ "=\"+" + columnJavaName + "+\"");
            if(i!=count-1){
                builder.append(",");
            }
        }
        builder.append("]\";").append("\r\n");
        builder.append("	}").append("\r\n");

        builder.append("}").append("\r\n");
        print.println(builder.toString());
        print.close();

        System.out.println("Entity创建成功");

    }

}
