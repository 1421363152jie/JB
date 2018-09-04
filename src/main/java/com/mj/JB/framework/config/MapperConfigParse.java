package com.mj.JB.framework.config;

import com.mj.JB.framework.mapping.ResultMap;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 解析MapperConfig文件
 */
public class MapperConfigParse {

    private final Map<String,MapperData> selectMapper=new HashMap<String,MapperData>();

    private JBConfigParse jbConfigParse;



    public MapperConfigParse(JBConfigParse jbConfigParse) {
        this.jbConfigParse = jbConfigParse;
        parse();
    }

    public Map<String, MapperData> getSelectMapper() {
        return selectMapper;
    }




    public void parse(){
       SAXReader reader =new SAXReader();
        //创建文档流
        for (String xmlpath: jbConfigParse.getMapperConfig()) {
            //创建文档流
            try {
                InputStream in =this.getClass().getClassLoader().getResourceAsStream(xmlpath);
                //读取文档,返回文档对象
                Document doc=reader.read(in);
                //获得文档对象的根元素
                Element root=doc.getRootElement();
                Attribute namespece = root.attribute(ConfigConstant.Mapper_NAMESPACE);
                //获得子元素
                List<Element>  elementList = root.elements();
                for (Element element:elementList) {
                    //获得子元素名称
                    String pname=element.getName();
                    //TODO 判断标签的name获得类型，根据不同类型分析每个标签,可扩展
                    if(pname.equals(ConfigConstant.SELECT)){
                       parseSelect(element,namespece.getValue());
                    }


                }


            } catch (Exception e) {
                e.printStackTrace();
            }

        }
   }

    /**
     * 解析select标签
     */
    private void parseSelect(Element element,String nameSpece) throws Exception {
        Attribute idAttr = element.attribute(ConfigConstant.SELECT_ID);
        if(idAttr==null){
            throw new Exception("The id has to be configured");
        }
        Attribute  paramAttr = element.attribute(ConfigConstant.SELECT_PARAMETERtype);
        Attribute resultAttr = element.attribute(ConfigConstant.SELECT_RESULTTYPE);
        String sql = element.getText();
        MapperData mapperData=new MapperData();
        mapperData.setId(idAttr.getValue());
        if(paramAttr==null){
          mapperData.setParameterType(null);
        }else {
            mapperData.setParameterType(paramAttr.getValue());
        }
        mapperData.setResultType(resultAttr.getValue());
        if(resultAttr==null){
            throw new Exception("The resultAttr has to be configured");
        }
        mapperData.setSql(sql.trim());
        String mapperName= nameSpece +"."+idAttr.getValue();
        selectMapper.put(mapperName,mapperData);
    }







    public  class  MapperData{


       private String id;

       private String parameterType;

       private String resultType;

       private String sql;


       public String getId() {
           return id;
       }

       public void setId(String id) {
           this.id = id;
       }

       public String getParameterType() {
           return parameterType;
       }

       public void setParameterType(String parameterType) {
           this.parameterType = parameterType;
       }

       public String getResultType() {
           return resultType;
       }

       public void setResultType(String resultType) {
           this.resultType = resultType;
       }

       public String getSql() {
           return sql;
       }

       public void setSql(String sql) {
           this.sql = sql;
       }
   }




}
