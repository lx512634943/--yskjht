package com.sojpt.kit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class FileCopyKit {

    /**
     *
     * @param source 源文件
     * @param target 目标文件
     */
    public static boolean fileCopy(File source, File target) {

        FileInputStream fi = null;

        FileOutputStream fo = null;

        FileChannel in = null;

        FileChannel out = null;

        try {

            fi = new FileInputStream(source);

            fo = new FileOutputStream(target);

            in = fi.getChannel();// 得到对应的文件通道

            out = fo.getChannel();// 得到对应的文件通道

            in.transferTo(0, in.size(), out);// 连接两个通道，并且从in通道读取，然后写入out通道

            return true;
        } catch (IOException e) {

            e.printStackTrace();
            return false;
        } finally {

            try {

                fi.close();

                in.close();

                fo.close();

                out.close();

            } catch (IOException e) {

                e.printStackTrace();

            }

        }

    }
}
