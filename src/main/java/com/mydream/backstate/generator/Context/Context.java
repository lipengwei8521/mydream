package com.mydream.backstate.generator.Context;

import java.util.HashMap;

/**
 * 全局配置
 */
public class Context extends HashMap<String,Object>{
	private static final long serialVersionUID = 1L;
	/**生成的实体类路径*/
	public static final String ENTITY_PATH="\\generate\\src\\main\\java\\com\\gcg\\entity";
	/**实体类包名*/
	public static final String ENTITY_PACKAGE="com.hxkr.entity";
	/**生成的dao路径*/
    public static final String DAO_PATH = "\\generate\\src\\main\\java\\com\\gcg\\dao";
    /**dao包名*/
	public static final String DAO_PACKAGE="com.hxkr.dao";
    /**生成的Mapper文件路径*/
    public static final String MAPPER_PATH = "\\generate\\src\\main\\java\\com\\gcg\\mapping";
    /**生成的Service路径*/
    public static final String SERVICE_PATH = "\\generate\\src\\main\\java\\com\\gcg\\service";
    /**service包名*/
	public static final String SERVICE_PACKAGE="com.hxkr.service";
    /**生成的Service实现类的路径*/
    public static final String SERVICE_IMPL_PATH = "\\generate\\src\\main\\java\\com\\gcg\\serviceImpl";
    /**serviceImpl包名*/
  	public static final String SERVICEIMPL_PACKAGE="com.hxkr.service.impl";
    /**生成的Controller文件路径*/
    public static final String CONTROLLER_PATH = "controllerPath";
    /**生成的html文件路径*/
    public static final String HTML_PATH = "htmlPath";
    /**生成的js文件的路径*/
    public static final String JS_PATH = "jsPath";
    /**文件编码*/
    public static final String FILE_CHARSET = "fileCharset";
   
}
