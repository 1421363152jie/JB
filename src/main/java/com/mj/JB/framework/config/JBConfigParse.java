package com.mj.JB.framework.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 解析JB-Config中的Mapper-Config映射文件
 */
public class JBConfigParse {

    private  List<String> mapperConfig=new ArrayList<String>();


    public JBConfigParse(String ConfigPath){
        InputStream in = this.getClass().getClassLoader().getResourceAsStream(ConfigPath);
        Properties prop=new Properties();
        try {
            prop.load(in);
            String mappers = prop.getProperty(ConfigConstant.MAPPER_CONFIG);
            splitMappers(mappers);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    private void splitMappers(String mappers){
        String[] mapperArr = mappers.split(",");
        for (String mapper:mapperArr) {
            mapperConfig.add(mapper);
        }
    }

    public List<String> getMapperConfig() {
        return mapperConfig;
    }



}
