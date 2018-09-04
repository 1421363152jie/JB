package com.mj.JB.framework.DataSource;

import com.mj.JB.framework.config.ConfigConstant;
import com.mj.JB.framework.config.DataSourceParse;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class JBDataSourceFactory {


    private JBDataSource jbDataSource;

    public JBDataSourceFactory(String ConfigPath) {
        InputStream in = DataSourceParse.class.getClassLoader().getResourceAsStream(ConfigPath);
        Properties prop=new Properties();
        try {
            prop.load(in);
            String dbcp = prop.getProperty(ConfigConstant.DATASOURCE_DECP_OPEN);
            String dbcpDatatConfigPath = prop.getProperty(ConfigConstant.DATASOURCE_DECP_CONFIG);
            String c3p0=prop.getProperty(ConfigConstant.DATASOURCE_C3P0_OPEN);
            String druid=prop.getProperty(ConfigConstant.DATASOURCE_DRUID_OPEN);
            String jdbcData=prop.getProperty(ConfigConstant.DATASOURCE_DRIVER_JDBC_OPEN);
            //dbcp数据源的支持
            if(dbcp!=null&&dbcp.equals("true")){
                jbDataSource=new  DBCPSource(dbcpDatatConfigPath);
            //c3p0数据源的支持
            }else if(c3p0!=null&&c3p0.equals("true")){
                jbDataSource=new C3P0Source(ConfigPath);
            //druid数据源的支持
            }else if(druid!=null&&druid.equals("true")){
                String druidConfig = prop.getProperty(ConfigConstant.DATASOURCE_DRUID_CONFIG);
                if(druidConfig==null){return;}
                jbDataSource=new DruidSource(druidConfig);
            }else if(jdbcData!=null&&jdbcData.equals("true")){
                jbDataSource=new JDBCDataSource(ConfigPath);
            }else {
              jbDataSource=null;
              throw new  Exception("The database connection driver is configured to be null!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public JBDataSource getJbDataSource() {
        return jbDataSource;
    }





    public  void release(Connection conn, Statement st, ResultSet rs){
        if(rs!=null){
            try{
                rs.close();
            }catch(Exception e){
                e.printStackTrace();
            }
            rs=null;
        }
        if(st!=null){
            try{
                st.close();
            }catch(Exception e){
                e.printStackTrace();
            }
            st=null;
        }
        if(conn!=null){
            try{
                conn.close();
            }catch(Exception e){
                e.printStackTrace();
            }
            conn=null;
        }
    }
}
