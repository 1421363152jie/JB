package com.mj.JB.framework.DataSource;

public class C3P0SourceFactory extends AbstractionDataSourceFactory {
    @Override
    public JBDataSource getJBDataSource(String DataSourcePath) {
        return new C3P0Source(DataSourcePath);
    }
}
