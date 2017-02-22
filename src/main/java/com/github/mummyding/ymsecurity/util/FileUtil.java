package com.github.mummyding.ymsecurity.util;

import android.text.TextUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;

/**
 * Created by MummyDing on 2017/1/31.
 */

public class FileUtil {

    public static long getFileSize(File file) {
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

    public static String formatSize(long size) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        String wrongSize = "0B";
        if (size == 0) {
            return wrongSize;
        }
        if (size < 1024) {
            fileSizeString = df.format((double) size) + "B";
        } else if (size < 1048576) {
            fileSizeString = df.format((double) size / 1024) + "KB";
        } else if (size < 1073741824) {
            fileSizeString = df.format((double) size / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) size / 1073741824) + "GB";
        }
        return fileSizeString;
    }

    public static boolean isValidPath(String rootPath) {
        if (TextUtils.isEmpty(rootPath)) {
            return false;
        }
        File rootFile = new File(rootPath);
        if (rootFile == null && !rootFile.exists()) {
            return false;
        }
        return true;
    }

    public static boolean isExist(File file) {
        if (file == null) {
            return false;
        }
        return file.exists();
    }
}
