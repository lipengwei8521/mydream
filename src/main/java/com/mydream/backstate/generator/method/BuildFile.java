package com.mydream.backstate.generator.method;

import com.mydream.backstate.generator.template.*;
import net.sf.json.JSONArray;

/**
 * Package: com.mydream.backstate.generator.method
 * Description: TODO
 * Author: 李鹏伟
 * Date: Created in 2018/8/138:53
 * Company: 公司
 * Copyright: Copyright (C) 2018
 * Version: 0.0.1
 * Modified By: 修改者
 */
public class BuildFile {

    public static boolean buildFile(String tableName, String tableComment, String basePath, boolean entity, boolean dao, boolean mapper,
                                    boolean service, boolean controller, boolean html, boolean js, JSONArray json, String keyNum, String author) throws Exception {
        String charset = "utf-8";
        String newBasePath =basePath + "src/main/java/com/mydream/backstate";


        // 各文件生成路径
        String entityPath = "\\" + tableName + "\\entity";
        String daoPath = "\\" + tableName + "\\dao";
        String mapperPath = "src/main/resources/mapping/" + tableName;
        String servicePath = "\\" + tableName + "\\service";
        String controllerPath = "\\" + tableName + "\\controller";

        // 模块根路径
        String projectPath = "com.mydream.backstate";
        // package 的路径，模块根路径+模块本身路径
        String entityPackagePath = projectPath + "." + tableName + ".entity";
        String daoPackagePath = projectPath + "." + tableName + ".dao";
        String servicePackagePath = projectPath + "." + tableName + ".service";
        String controllerPackagePath = projectPath + "." + tableName + ".controller";

        // 作者名称
//        author = "李鹏伟";
        if(controller) {
            ControllerTemplate.creatControoller(tableName, tableComment, newBasePath + controllerPath, charset,
                    controllerPackagePath, servicePackagePath, entityPackagePath, author);
        }
        if (entity) {
            EntityTemplate.creatEntity(tableName, tableComment, newBasePath + entityPath, charset, entityPackagePath, json, author);
        }
        if (dao) {
            MapperTemplate.creatDao(tableName, tableComment, newBasePath + daoPath, charset, daoPackagePath, entityPackagePath, json, keyNum, author);
        }
        if (mapper) {
            MapperXmlTemplate.creatMapper(tableName, basePath + mapperPath, charset, daoPackagePath, entityPackagePath, json, keyNum);
        }
        if (service) {
            ServiceTemplate.creatService(tableName, tableComment, newBasePath + servicePath, charset, entityPackagePath, servicePackagePath, author);
            ServiceImplTemplate.creatServiceImpl(tableName, tableComment, newBasePath + servicePath, charset, servicePackagePath, daoPackagePath, entityPackagePath, author);
        }
        return true;
    }

}
