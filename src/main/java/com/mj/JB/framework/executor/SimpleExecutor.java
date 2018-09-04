package com.mj.JB.framework.executor;

import com.mj.JB.framework.config.JBConfiguration;
import com.mj.JB.framework.statement.statementHandler;

public class SimpleExecutor implements Executor{

    private JBConfiguration jbConfiguration;

    public SimpleExecutor(JBConfiguration jbConfiguration) {
        this.jbConfiguration = jbConfiguration;
    }
    @Override
    public Object queryOne(String statement, Object parameter) {
        statementHandler handler=new statementHandler(jbConfiguration);
        return handler.queryOne(statement,parameter);
    }

    @Override
    public Object queryList(String statement, Object parameter) {
        statementHandler handler=new statementHandler(jbConfiguration);
        return handler.queryList(statement,parameter);
    }
}
