package com.mj.JB.framework.result;

import com.mj.JB.framework.config.JBConfiguration;
import com.mj.JB.framework.statement.statementHandler;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ResultSetHandler {


     private JBConfiguration jbConfiguration;

    public ResultSetHandler(JBConfiguration jbConfiguration) {
        this.jbConfiguration = jbConfiguration;
    }

    public <T> T handleOne(Connection conn,PreparedStatement ps, String resultType) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, SQLException, ClassNotFoundException {
         //实例化一个对象
        Class<?> clazz = Class.forName(resultType);
        Object  resultObj= clazz.getConstructor(new Class[]{}).newInstance(new Object[]{});
        ResultSet rs =null;
        try {
             rs=ps.getResultSet();
            while (rs.next()) {
                for (Field field : clazz.getDeclaredFields()) {
                    setValue(resultObj, field, rs, clazz);
                }
            }
        }finally {
            jbConfiguration.getJbDataSourceFactory().release(conn,ps,rs);
        }
        return (T) resultObj;
    }

    public <T> List<T>  handleList(Connection conn,PreparedStatement ps, String resultType) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, SQLException, ClassNotFoundException {
        //实例化一个对象
        List<T> list=new ArrayList<T>();
        ResultSet rs = null;
        try {
            rs = ps.getResultSet();
            while (rs.next()){
                Class<?> clazz = Class.forName(resultType);
                Object  resultObj= clazz.getConstructor(new Class[]{}).newInstance(new Object[]{});
                for(Field field:clazz.getDeclaredFields()){
                    setValue(resultObj,field,rs,clazz);
                }
                list.add((T) resultObj);
            }
        }finally {
            jbConfiguration.getJbDataSourceFactory().release(conn,ps,rs);
        }

        return list;
    }



    private void setValue(Object resultObj, Field field, ResultSet rs, Class clazz) throws NoSuchMethodException, SQLException, InvocationTargetException, IllegalAccessException {
        Method method = clazz.getMethod("set" + upperCapital(field.getName()), field.getType());
        method.invoke(resultObj,getResult(field,rs));
    }



    private Object getResult(Field field,ResultSet rs) throws SQLException {
        Class<?> type = field.getType();
        if(type==Integer.class){
          return  rs.getInt(field.getName());
        }else if(type==String.class){
            return  rs.getString(field.getName());
        }
        return rs.getString(field.getName());
    }


    private String upperCapital(String str){
        String first = str.substring(0, 1);
        String last = str.substring(1);
        return first.toUpperCase()+last;
    }


}
