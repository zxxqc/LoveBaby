package com.hb.lovebaby.util;

import android.os.Environment;

import java.io.File;
import java.util.UUID;

/**
 * 
 * @author liangliang
 *
 */

public class FileStorage {
    private File cropIconDir;
    private File iconDir;

    public FileStorage() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            File external = Environment.getExternalStorageDirectory();
            String rootDir = "/" + "demo";
            cropIconDir = new File(external, rootDir + "/crop");
            if (!cropIconDir.exists()) {
                cropIconDir.mkdirs();
            }
            iconDir = new File(external, rootDir + "/icon");
            if (!iconDir.exists()) {
                iconDir.mkdirs();
            }
        }
    }

    //在crop目录下创建随机UUID为名的png文件
    public File createCropFile() {
        String fileName = "";
        if (cropIconDir != null) {
            fileName = UUID.randomUUID().toString() + ".jpg";
        }
        return new File(cropIconDir, fileName);
    }

    //在icon目录下创建随机UUID为名的png文件
//    (UUID在蓝牙中使用到(8-4-4-8-12))
    public File createIconFile() {
        String fileName = "";
        if (iconDir != null) {
            fileName = UUID.randomUUID().toString() + ".jpg";
        }
        return new File(iconDir, fileName);
    }

}
