package com.mj.JB.framework.executor;

import com.mj.JB.framework.config.JBConfiguration;

public class ExecutorFactory {

    private static  final  String SIMPLE="SIMPLE";

    public static Executor DEFAULT(JBConfiguration jbConfiguration){
       return get(SIMPLE,jbConfiguration);
    }

    public static  Executor get(String Executorkey,JBConfiguration jbConfiguration){
        if(Executorkey.equalsIgnoreCase(SIMPLE)){
            return new SimpleExecutor(jbConfiguration);
        }
       return  new SimpleExecutor(jbConfiguration);
    }

}
