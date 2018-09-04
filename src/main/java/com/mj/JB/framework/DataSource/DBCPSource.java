package com.mj.JB.framework.DataSource;


import org.apache.commons.dbcp.BasicDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 解析dbcp数据源
 */
public class DBCPSource implements JBDataSource{

    static org.apache.log4j.Logger logger=org.apache.log4j.Logger.getLogger(DataSource.class.getName());

    private String DataSourcePath;

    private  DataSource ds;


    public DBCPSource(String dataSourcePath) {
        DataSourcePath = dataSourcePath;
        InputStream in=DBCPSource.class.getClassLoader().getResourceAsStream(dataSourcePath);
        Properties prop=new Properties();
        try {
            prop.load(in);
            //创建数据库的连接池
             this.ds=BasicDataSourceFactory.createDataSource(prop);
            logger.debug("DECPData Source connect Success!");
        }catch (Exception e){
            logger.error("DECPData Source Exception");
        }
    }
    @Override
    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}
