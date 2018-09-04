package com.mj.JB.framework.config;

import com.mj.JB.framework.DataSource.JBDataSourceFactory;

public class JBConfiguration {


    private MapperRegistory mapperRegistory;

    /**
     * 数据源的读取
     */
  /*  private DataSourceParse dataSourceParse;*/

     private JBDataSourceFactory jbDataSourceFactory;

    private JBConfigParse jbConfigParse;

    private MapperConfigParse mapperConfigParse;

    public JBConfiguration( MapperRegistory mapperRegistory) {
        this.mapperRegistory = mapperRegistory;
    }

    public JBConfiguration(String ConfigPath) {
       /* this.dataSourceParse=new DataSourceParse(ConfigPath);*/
        this.jbDataSourceFactory=new JBDataSourceFactory(ConfigPath);
        this.jbConfigParse =new JBConfigParse(ConfigPath);
        this.mapperConfigParse=new MapperConfigParse(jbConfigParse);
    }

    public JBDataSourceFactory getJbDataSourceFactory() {
        return jbDataSourceFactory;
    }

    public MapperRegistory getMapperRegistory() {
        return mapperRegistory;
    }

    public JBConfigParse getJbConfigParse() {
        return jbConfigParse;
    }

    public MapperConfigParse getMapperConfigParse() {
        return mapperConfigParse;
    }
}
