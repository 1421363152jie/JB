package com.mj.JB.framework.DataSource;

public class JDBCSourceFactory extends AbstractionDataSourceFactory{
    @Override
    public JBDataSource getJBDataSource(String DataSourcePath) {
        return new JDBCDataSource(DataSourcePath);
    }
}
