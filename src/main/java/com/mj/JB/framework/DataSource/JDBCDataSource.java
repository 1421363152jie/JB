package com.mj.JB.framework.DataSource;

import com.mj.JB.framework.config.ConfigConstant;
import com.mj.JB.framework.config.DataSourceParse;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCDataSource implements JBDataSource{

    private String DataSourcePath;


    private String driver;

    private String url;

    private String username;

    private String  password;


    public JDBCDataSource(String dataSourcePath) {
        DataSourcePath = dataSourcePath;
        InputStream in = DataSourceParse.class.getClassLoader().getResourceAsStream(dataSourcePath);
        Properties prop=new Properties();
        try {
            prop.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.driver = prop.getProperty(ConfigConstant.DATASOURCE_DRIVER);
        this.url=prop.getProperty(ConfigConstant.DATASOURCE_URL);
        this.username=prop.getProperty(ConfigConstant.DATASOURCE_USERNAME);
        this.password=prop.getProperty(ConfigConstant.DATASOURCE_PASSWORD);
    }

    @Override
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

}
