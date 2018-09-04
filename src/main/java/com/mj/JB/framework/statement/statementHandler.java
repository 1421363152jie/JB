package com.mj.JB.framework.statement;

import com.mj.JB.framework.config.ConfigConstant;
import com.mj.JB.framework.config.JBConfiguration;
import com.mj.JB.framework.config.MapperConfigParse;
import com.mj.JB.framework.result.ResultSetHandler;

import javax.sql.DataSource;
import java.beans.IntrospectionException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class statementHandler {

    static org.apache.log4j.Logger logger=org.apache.log4j.Logger.getLogger(statementHandler.class.getName());

    private JBConfiguration jbConfiguration;


    private ResultSetHandler resultSetHandler;

    private MapperConfigParse mapperConfigParse;

    public statementHandler(JBConfiguration jbConfiguration) {
        this.jbConfiguration = jbConfiguration;
        resultSetHandler=new ResultSetHandler(jbConfiguration);
        mapperConfigParse=jbConfiguration.getMapperConfigParse();
    }

    public Object queryOne(String statement, Object parameter)  {

        PreparedStatement ps = null;
        try {
            Connection conn = jbConfiguration.getJbDataSourceFactory().getJbDataSource().getConnection();
            for (Map.Entry<String,MapperConfigParse.MapperData> entry:mapperConfigParse.getSelectMapper().entrySet()) {
                if(!entry.getKey().equals(statement)){
                    /*throw  new Exception("SQL map name does not correspond, please check!");*/
                    continue;
                }
                String sql = entry.getValue().getSql();
                //解析sql语句的@{}并赋值
                Matcher matcher = matcher(sql);
                System.out.println(String.format("com.mj.JB.framework : SQL [ %s ], parameter [%s] ", sql, parameter));
                while (matcher.find()){
                    // 返回此匹配器模式中的捕获组数。
                    for (int i = 1; i <= matcher.groupCount(); i ++) {
                        //  返回在以前匹配操作期间由给定组捕获的输入子序列。
                        String paramName = matcher.group(i);
                        //判断值不为空
                        if(null == parameter){ continue; }
                        //将语法替换为值
                        sql=parseParamType(sql,entry,parameter,paramName);
                    }
                }
                logger.debug("assembled SQL:"+sql);
                ps = conn.prepareStatement(sql);
                ps.executeQuery();
                return resultSetHandler.handleOne(conn,ps,entry.getValue().getResultType());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Object queryList(String statement, Object parameter) {

        PreparedStatement ps = null;
        try {
            Connection conn =  jbConfiguration.getJbDataSourceFactory().getJbDataSource().getConnection();
            for (Map.Entry<String,MapperConfigParse.MapperData> entry:mapperConfigParse.getSelectMapper().entrySet()) {
                if(!entry.getKey().equals(statement)){
                    /*throw  new Exception("SQL map name does not correspond, please check!");*/
                    continue;
                }
                String sql = entry.getValue().getSql();
                //解析sql语句的@{}并赋值
                Matcher matcher = matcher(sql);
                logger.debug(String.format("com.mj.JB.framework : SQL [ %s ], parameter [%s] ", sql, parameter));
                //TODO 抽取成公用方法
                while (matcher.find()){
                    // 返回此匹配器模式中的捕获组数。
                    for (int i = 1; i <= matcher.groupCount(); i ++) {
                        //  返回在以前匹配操作期间由给定组捕获的输入子序列。
                        String paramName = matcher.group(i);
                        //判断值不为空
                        if(null == parameter){ continue; }
                        //将语法替换为值
                        sql=parseParamType(sql,entry,parameter,paramName);
                    }
                }
                logger.debug("assembled SQL:"+sql);
                ps = conn.prepareStatement(sql);
                ps.executeQuery();
                logger.debug("select SQL compieted !");
                return resultSetHandler.handleList(conn,ps,entry.getValue().getResultType());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }




    private Matcher matcher(String str) {
        Pattern pattern = Pattern.compile("@\\{(.+?)\\}",Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        return matcher;
    }

    /**
     * TODO 可扩展其他类型
     * 解析拼接sql
     */
    private String parseParamType(String sql,Map.Entry<String,MapperConfigParse.MapperData> entry,Object parameter,String paramName) throws IllegalAccessException, IntrospectionException, InstantiationException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException {
        if(entry.getValue().getParameterType().equals(ConfigConstant.SELECT_PARAMETERTYPE_INT)){
           return  sql.replaceAll("@\\{" + paramName + "\\}", parameter.toString());
        }else if(entry.getValue().getParameterType().equals(ConfigConstant.SELECT_PARAMETERTYPE_STRING)){
            return  sql.replaceAll("@\\{" + paramName + "\\}", parameter.toString());
        }else  if(entry.getValue().getParameterType().equals(ConfigConstant.SELECT_PARAMETERTYPE_INTEGER)){
            return  sql.replaceAll("@\\{" + paramName + "\\}", parameter.toString());
        }else if(entry.getValue().getParameterType().equals(ConfigConstant.SELECT_PARAMETERTYPE_MAP)){
            //TODO 用户在配置map传参数是必须是Map<String, Object>
            Map<String, Object> map = (Map<String, Object>) parameter;
            for (Map.Entry<?, Object> mapEntry:map.entrySet()) {
                String key = (String) mapEntry.getKey();
                Object value = mapEntry.getValue();
                if(!paramName.equals(key)){continue;}
                return sql.replaceAll("@\\{" + key + "\\}", value.toString());
            }
        }else if(entry.getValue().getParameterType()==null){
            //没有配置参数的
            return sql;
            //不是以上的就是类的映射
        }else {
            /*Method[] methods = parameter.getClass().getMethods();
            System.out.println(methods);*/
           Class<?> clazz = parameter.getClass();
                Class<?> clazz1 = Class.forName(entry.getValue().getParameterType());
                if(clazz!=clazz1){
                    System.out.println("Parameter types do not match parameters");
                    return null;
                }
            Field[] fields = clazz1.getDeclaredFields();
            String sql1=sql;
            for (Field field:fields) {
               String fieldName=field.getName();
                Method method = clazz1.getMethod("get" + upperCapital(fieldName),null);
                Object value = method.invoke(parameter);
                if(field.getType()==String.class){
                    sql1=sql1.replaceAll("@\\{" + fieldName + "\\}","'"+value.toString()+"'");
                }else {
                    sql1 = sql1.replaceAll("@\\{" + fieldName + "\\}", value.toString());
                }
            }
            return sql1;
        }
        return null;
    }

    private String upperCapital(String str){
        String first = str.substring(0, 1);
        String last = str.substring(1);
        return first.toUpperCase()+last;
    }






}
