package com.mj.JB.framework.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mj.JB.framework.config.ConfigConstant;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

public class C3P0Source implements JBDataSource {

    static org.apache.log4j.Logger logger=org.apache.log4j.Logger.getLogger(C3P0Source.class.getName());

    private String DataSourcePath;

    private  DataSource ds;

    public C3P0Source(String dataSourcePath) {
        this.DataSourcePath = dataSourcePath;
        InputStream in=DBCPSource.class.getClassLoader().getResourceAsStream(dataSourcePath);
        Properties prop=new Properties();
        try {
            prop.load(in);
            String definedC3P0= prop.getProperty(ConfigConstant.DATASOURCE_C3P0_DEFINED_OPEN);
            String propC3p0=null;
            String xmlC3p0=null;
            if(definedC3P0==null){
                 propC3p0=prop.getProperty(ConfigConstant.DATASOURCE_C3P0_PROP_OPEN);
            }
            if(propC3p0==null){
                 xmlC3p0=prop.getProperty(ConfigConstant.DATASOURCE_C3P0_XML_OPEN);
            }
            if(definedC3P0!=null&&definedC3P0.equals("true")){
                logger.debug("c3p0DataSource dinfined start!");
                ds=definedC3p0GetSource(prop);
            }else if(propC3p0!=null&&propC3p0.trim().equals("true")){
                ds=propC3p0GetSource();
            }else if(xmlC3p0!=null&&xmlC3p0.trim().equals("true")){
                ds=xmlC3p0GetSource();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 读取classpath下配置的c3p0.xml
     */
    private DataSource xmlC3p0GetSource() {
      return (ComboPooledDataSource) new ComboPooledDataSource();
    }

    /**
     * classpath下c3p0.properties配置读取获得数据源
     */
    private DataSource propC3p0GetSource() {
        return (ComboPooledDataSource)new ComboPooledDataSource();
    }

    /**
     * 读取自定义配置的c3p0配置
     */
    private DataSource definedC3p0GetSource(Properties prop){
        DataSource ds=null;
        String driver = prop.getProperty(ConfigConstant.DATASOURCE_C3P0_DRIVER);
        String url=prop.getProperty(ConfigConstant.DATASOURCE_C3P0_URL);
        String username=prop.getProperty(ConfigConstant.DATASOURCE_C3P0_USERNAME);
        String password=prop.getProperty(ConfigConstant.DATASOURCE_C3P0_PASSWORD);
        try {
            ds=new ComboPooledDataSource();
            ((ComboPooledDataSource) ds).setDriverClass(driver);
            ((ComboPooledDataSource) ds).setJdbcUrl(url);
            ((ComboPooledDataSource) ds).setUser(username);
            ((ComboPooledDataSource) ds).setPassword(password);
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        return  ds;
    }



    @Override
    public Connection getConnection() throws Exception {
        return ds.getConnection();
    }
}
