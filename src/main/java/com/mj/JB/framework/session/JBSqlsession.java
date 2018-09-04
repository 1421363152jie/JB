package com.mj.JB.framework.session;

import com.mj.JB.framework.config.JBConfiguration;
import com.mj.JB.framework.config.MapperRegistory;
import com.mj.JB.framework.executor.Executor;
import com.mj.JB.framework.mapperProxy.JBMapperProxy;

import java.lang.reflect.Proxy;
import java.util.List;


public class JBSqlsession {

    private JBConfiguration jbConfiguration;

    private Executor executor;

    public JBSqlsession(JBConfiguration jbConfiguration, Executor executor) {
        this.jbConfiguration = jbConfiguration;
        this.executor = executor;
    }

    public JBConfiguration getJbConfiguration() {
        return jbConfiguration;
    }

    public <T> T getMapper(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader()
                ,new Class[]{clazz}
        ,new JBMapperProxy(this,clazz));
    }

    /*public <T> T selectOne(MapperRegistory.MapperData mapperData,Object parameter){
        return (T) executor.query(mapperData,parameter);
    }*/

    public <T> T selectOne(String statement,Object parameter){
        return (T) executor.queryOne(statement,parameter);
    }

    public <T> List<T> selectList(String statement){
        return (List<T>) executor.queryList(statement,null);
    }


    public <T> List<T> selectList(String statement, Object parameter){
        return (List<T>) executor.queryList(statement,parameter);
    }

}
