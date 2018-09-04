package com.mj.JB.framework.config;

import java.util.HashMap;
import java.util.Map;

public class MapperRegistory {

    private Map<String,MapperData> methodMapping=new HashMap<String,MapperData>();

    public MapperRegistory(String nameSpece,String sql,Class<?> type) {
            methodMapping.put(nameSpece,  new MapperData(sql,type));
        }

        public  class MapperData<T> {

            private String sql;

            private Class<T> type;

        public MapperData(String sql, Class<T> type) {
            this.sql = sql;
            this.type = type;
        }

        public String getSql() {
            return sql;
        }

        public void setSql(String sql) {
            this.sql = sql;
        }

        public Class<T> getType() {
            return type;
        }

        public void setType(Class<T> type) {
            this.type = type;
        }
    }

    public MapperData get(String nameSpece){
        return methodMapping.get(nameSpece);
    }

}
