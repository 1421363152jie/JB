package com.mj.JB.framework.executor;

public interface Executor<T> {

    T queryOne(String statement,Object parameter);

    T queryList(String statement,Object parameter);
}

