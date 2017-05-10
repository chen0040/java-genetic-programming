package com.github.chen0040.gp.utils;


import java.io.IOException;
import java.io.InputStream;


/**
 * Created by xschen on 10/5/2017.
 */
public class FileUtil {
   public static InputStream getResource(String filename) throws IOException {
      return FileUtil.class.getClassLoader().getResource(filename).openStream();
   }
}
