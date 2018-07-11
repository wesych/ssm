package org.wesc.ssm.utils.tool;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

/**
 * @author Wesley Cheung
 * @Date Created in 14:50 2017/12/20
 */
public class JarMetaUtils {
    public static String findEntry(String name) {
        Enumeration<URL> resEnum;
        try {
            resEnum = Thread.currentThread().getContextClassLoader().getResources(JarFile.MANIFEST_NAME);
            while (resEnum.hasMoreElements()) {
                try {
                    URL url = resEnum.nextElement();
                    InputStream is = url.openStream();
                    if (is != null) {
                        Manifest manifest = new Manifest(is);
                        Attributes mainAttribs = manifest.getMainAttributes();
                        String val = mainAttribs.getValue(name);
                        if (val != null) {
                            return val;
                        }
                    }
                } catch (Exception e) {
                    //ignore
                }
            }
        } catch (IOException e1) {
            //ignore
        }
        return null;
    }
}
