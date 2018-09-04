package com.mj.JB.framework.mapperProxy;

import com.mj.JB.framework.config.MapperRegistory;
import com.mj.JB.framework.session.JBSqlsession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class JBMapperProxy<T> implements InvocationHandler {


    private JBSqlsession jbSqlsession;

    private Class<T> mapperInterface;


    public JBMapperProxy(JBSqlsession jbSqlsession, Class<T> mapperInterface) {
        this.jbSqlsession = jbSqlsession;
        this.mapperInterface = mapperInterface;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        MapperRegistory.MapperData mapperData=jbSqlsession.getJbConfiguration()
                .getMapperRegistory().get(method.getDeclaringClass().getName()+"."+method.getName());
        if(mapperData!=null){
            System.out.println(String.format("SQL [ %s ], parameter [%s] ", mapperData.getSql(), args[0]));
            return jbSqlsession.selectOne(null, String.valueOf(args[0]));
        }
        return method.invoke(mapperData,args);
    }
}
