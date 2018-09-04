package com.mj.JB.framework.config;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DataSourceParse {

    private String driver;

    private String url;

    private String username;

    private String  password;

    private String ConfigPath;

   public DataSourceParse(String ConfigPath){
       InputStream in = DataSourceParse.class.getClassLoader().getResourceAsStream(ConfigPath);
       Properties prop=new Properties();
       try {
           prop.load(in);
       } catch (IOException e) {
           e.printStackTrace();
       }
       //TODO 优化忽略配置文件大小写问题
       this.driver = prop.getProperty(ConfigConstant.DATASOURCE_DRIVER);
       this.url=prop.getProperty(ConfigConstant.DATASOURCE_URL);
       this.username=prop.getProperty(ConfigConstant.DATASOURCE_USERNAME);
       this.password=prop.getProperty(ConfigConstant.DATASOURCE_PASSWORD);
   }

    //获得数据库连接
    public Connection getConnection(){
        Connection  conn=null;
        try {
            Class.forName(this.driver);
            conn=DriverManager.getConnection(this.url,this.username,this.password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }


    private class  JDBCDataSource{

        private String driver;

        private String url;

        private String username;

        private String  password;


    }



}
