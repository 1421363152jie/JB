package com.mj.JB.framework.DataSource;

public class DBCPSourceFactory extends AbstractionDataSourceFactory {

    @Override
        public JBDataSource getJBDataSource(String DataSourcePath) {

        return new DBCPSource(DataSourcePath);
    }
}
