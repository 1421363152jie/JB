package JBTest;

import com.mj.JB.framework.config.JBConfiguration;
import com.mj.JB.framework.executor.ExecutorFactory;
import com.mj.JB.framework.session.JBSqlsession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JBTest {

    public static void main(String[] args) {
/*
        MapperRegistory mapperRegistory=new MapperRegistory("JBTest.TestMapper.selectByPrimaryKey"
        ,"select * from test where id = %d",Test.class);
        JBConfiguration jbConfiguration=new JBConfiguration(mapperRegistory);
        JBSqlsession jbSqlsession=new JBSqlsession(jbConfiguration,ExecutorFactory.DEFAULT(jbConfiguration));
        TestMapper mapper = jbSqlsession.getMapper(TestMapper.class);
        Test test = mapper.selectByPrimaryKey(1);
        System.out.println(test);
*/     JBConfiguration jbConfiguration=new JBConfiguration("JB-Config.properties");
       JBSqlsession jbSqlsession=new JBSqlsession(jbConfiguration,ExecutorFactory.DEFAULT(jbConfiguration));
       /* Test test= jbSqlsession.selectOne("cn.mj.TestMapper.selectTestById", 1);
        System.out.println(test);*/
        /*Map<String,Object> map=new HashMap<String,Object>();
        map.put("ids",5);
       List<Test> testList=jbSqlsession.selectList("cn.mj.TestMapper.selectTest");
        System.out.println(testList.toString());*/
        /*Map<String,Object> map=new HashMap<String,Object>();
        map.put("name","e");*/
        TestQuery testQuery=new TestQuery();
        testQuery.setId(2);
        testQuery.setName("zhangsan");
        List<Test> testList=jbSqlsession.selectList("cn.mj.TestMapper.selectTestByTestQuery",testQuery);
        System.out.println(testList.toString());
    }
}
