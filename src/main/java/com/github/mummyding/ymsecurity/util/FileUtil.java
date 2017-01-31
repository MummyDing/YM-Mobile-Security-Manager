package com.github.mummyding.ymsecurity.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by MummyDing on 2017/1/31.
 */

public class FileUtil {

    public static long getFileSize(String path) {
        File file = new File(path);
        long size = 0;
        if (file != null && file.exists() && file.isFile()) {
            FileInputStream is = null;
            try {
                is = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                size = is.available();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return size;
    }
}
