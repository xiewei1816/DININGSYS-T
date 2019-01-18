package com.yqsh.diningsys.core.util;

import org.apache.commons.collections.Buffer;
import org.apache.commons.collections.map.HashedMap;
import org.ini4j.Wini;

import java.io.File;
import java.util.Map;

/**
 * INI文件读取
 *
 * @author zhshuo create on 2016-10-20 16:11
 */
public class IniReader {

    private static String INIFILEPATH = Thread.currentThread().getContextClassLoader().getResource("").getPath()+"systemFilePicPath.ini";

    public static Map<String,String> getConfig(String section, String ...keys) throws Exception{
        return getINIValue(section,keys);
    }


    public static String getConfig(String section, String keys){
        try {
            return getINIValue(section,keys);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private static Map<String,String> getINIValue(String section,String ...keys) throws Exception{
        Map<String,String> keyValue = new HashedMap();
        Wini ini = new Wini(new File(INIFILEPATH));
        for(String key:keys){
            keyValue.put(key,ini.get(section,key));
        }
        return keyValue;
    }

    private static String getINIValue(String section,String key) throws Exception{
        Wini ini = new Wini(new File(INIFILEPATH));
        return ini.get(section,key);
    }


}
