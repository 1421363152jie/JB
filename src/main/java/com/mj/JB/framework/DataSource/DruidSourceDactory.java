package com.mj.JB.framework.DataSource;

public class DruidSourceDactory extends AbstractionDataSourceFactory {
    @Override
    public JBDataSource getJBDataSource(String DataSourcePath) {
        return new DruidSource(DataSourcePath);
    }
}
