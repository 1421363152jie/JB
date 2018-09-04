package com.mj.JB.framework.config;


public interface ConfigConstant {


    //使用纯jdbc数据源配置
    public static  final String DATASOURCE_DRIVER_JDBC_OPEN="DataSource-jdbc-Open";

    public static  final String DATASOURCE_DRIVER="DataSource-driver";

    public static  final String DATASOURCE_URL="DataSource-url";


    public static  final String DATASOURCE_USERNAME="DataSource-username";


    public static  final String DATASOURCE_PASSWORD="DataSource-password";

    //dbcp数据源支持

    public static  final String DATASOURCE_DECP_CONFIG="DataSource-DECP-Config";

    public static  final String DATASOURCE_DECP_OPEN="DataSource-DECP-Open";


    //c3p0数据源支持
    public static  final String DATASOURCE_C3P0_OPEN="DataSource-C3P0-Open";

    //用户自定义配置文件
    public static  final String DATASOURCE_C3P0_DEFINED_OPEN="DataSource-C3P0-defined-Open";

    public static  final String DATASOURCE_C3P0_DRIVER="DataSource-C3P0-driver";

    public static  final String DATASOURCE_C3P0_URL="DataSource-C3P0-url";

    public static  final String DATASOURCE_C3P0_USERNAME="DataSource-C3P0-username";

    public static  final String DATASOURCE_C3P0_PASSWORD="DataSource-C3P0-password";

    //用户自定义配置文件

    //c3p0默认properties配置
    public static  final String DATASOURCE_C3P0_PROP_OPEN="DataSource-C3P0-Prop-Open";

    //c3p0默认xml形式配置
    public static  final String DATASOURCE_C3P0_XML_OPEN="DataSource-C3P0-Xml-Open";


    //druid数据源开启
    public static  final String DATASOURCE_DRUID_OPEN="DataSource-Druid-Open";

    //druid数据源的配置文件位置
    public static  final String DATASOURCE_DRUID_CONFIG="DataSource-Druid-Config";


    public static  final String MAPPER_CONFIG="Mapper-Config";

    /**
     * mapper映射文件
     */

    public static  final String Mapper_NAMESPACE="namespace";


    public static  final  String SELECT="select";

    public static  final  String SELECT_ID="id";

    public static  final  String SELECT_PARAMETERtype="parameterType";

    public static  final  String SELECT_RESULTTYPE="resultType";


    public static  final  String SELECT_PARAMETERTYPE_INT="int";

    public static  final  String SELECT_PARAMETERTYPE_INTEGER="Integer";

    public static  final  String SELECT_PARAMETERTYPE_STRING="String";

    public static  final  String SELECT_PARAMETERTYPE_MAP="map";

}
