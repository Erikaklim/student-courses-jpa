package com.example.javawebtry.persistence;

import com.example.javawebtry.mybatis.dao.CourseMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.mybatis.cdi.SessionFactoryProvider;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import java.io.IOException;
import java.io.InputStream;

public class MyBatisResources {

    @Produces
    @ApplicationScoped
    @SessionFactoryProvider
    private SqlSessionFactory produceSqlSessionFactory(){
//        try{
//            return new SqlSessionFactoryBuilder().build(
//                Resources.getResourceAsStream("MyBatisConfig.xml")
//            );
//        }catch (IOException e){
//            throw new RuntimeException("MyBatisResources.produceSqlSessionFactory(): ", e);
//        }
        try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("MyBatisConfig.xml")) {
            if (inputStream == null) {
                throw new RuntimeException("Cannot find 'MyBatisConfig.xml' in the classpath.");
            }
            return new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load 'MyBatisConfig.xml': ", e);
        }
    }

    @Produces
    @ApplicationScoped
    public CourseMapper produceCourseMapper(SqlSessionFactory sqlSessionFactory) {
        // Obtain a SqlSession from the SqlSessionFactory
        SqlSession sqlSession = sqlSessionFactory.openSession();
        // Get the mapper from the SqlSession
        return sqlSession.getMapper(CourseMapper.class);
    }
}
