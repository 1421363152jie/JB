package com.mj.JB.framework.DataSource;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

public class DruidSource implements JBDataSource{


    private String DataSourcePath;

    private DataSource ds;

    public DruidSource(String dataSourcePath) {
        DataSourcePath = dataSourcePath;
        InputStream in=DruidSource.class.getClassLoader().getResourceAsStream(dataSourcePath);
        Properties prop=new Properties();
        try {
            prop.load(in);
            ds = DruidDataSourceFactory.createDataSource(prop);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Connection getConnection() throws Exception {
        return ds.getConnection();
    }
}
