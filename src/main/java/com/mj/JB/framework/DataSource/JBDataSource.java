package com.mj.JB.framework.DataSource;

import java.sql.Connection;

public interface JBDataSource {

    Connection getConnection()throws Exception;
}
