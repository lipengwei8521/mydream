package com.mydream.utils;

import java.io.File;

/**
 * Package: com.mydream.utils
 * Description: TODO
 * Author: 李鹏伟
 * Date: Created in 2018/8/713:35
 * Company: 公司
 * Copyright: Copyright (C) 2018
 * Version: 0.0.1
 * Modified By: 修改者
 */
public class FileUtil {

    /**
     * 创建文件夹
     *
     * @param path
     * @return
     */
    public static boolean mkdirs(String path){
        File f = new File(path);
        f.mkdirs();
        return f.exists() && f.isDirectory();
    }
}
