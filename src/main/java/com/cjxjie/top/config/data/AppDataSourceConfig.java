//package com.cjxjie.top.config.data;
//
//import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.mybatis.spring.SqlSessionTemplate;
//import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//
//import javax.sql.DataSource;
//
///**
// * @author 刘杰
// * @version 1.0
// * @since 2022/09/21 18:41
// * app数据源配置
// */
//@Configuration
//@MapperScan(basePackages = "com.cjxjie.top.modules.app.dao", sqlSessionFactoryRef = "appSqlSessionFactory")
//public class AppDataSourceConfig {
//    @Bean(name = "appDataSource")
//    @Primary
//    @ConfigurationProperties(prefix = "spring.datasource.app")
//    public DataSource dataSource() {
//        DataSource dataSource = DataSourceBuilder.create().build();// 创建数据源连接
//        System.out.println("app数据源连接成功!");
//        return dataSource;
//    }
//
//    @Bean(name = "appSqlSessionFactory")
//    @Primary
//    public SqlSessionFactory sqlSessionFactory(@Qualifier("appDataSource") DataSource dataSource) throws Exception {
////        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean(); // 会话工厂
//        // Mybatis 不能使用原生的会话工厂
//        MybatisSqlSessionFactoryBean sessionFactoryBean = new MybatisSqlSessionFactoryBean();
//        sessionFactoryBean.setDataSource(dataSource); // 设置数据源
//
//        // 设置映射器位置
//        sessionFactoryBean.setMapperLocations(
//                new PathMatchingResourcePatternResolver().getResources("classpath:/mapper/**/*.xml")); // 映射文件地址
//
//        SqlSessionFactory sessionFactoryBeanObject = sessionFactoryBean.getObject();
//        System.out.println("app会话创建成功!");
//        return sessionFactoryBeanObject;
//    }
//
//    @Bean(name = "appSqlSessionTemplate")
//    @Primary
//    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("appSqlSessionFactory") SqlSessionFactory sessionFactory) throws Exception {
//        SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sessionFactory);
//        System.out.println("app会话模板初始化完成!");
//        return sqlSessionTemplate;
//    }
//
//    @Bean(name = "appTransactionManager")
//    @Primary
//    public DataSourceTransactionManager transactionManager(@Qualifier("appDataSource") DataSource dataSource) {
//        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager(dataSource);
//        System.out.println("app事务管理器初始化完成~");
//        return dataSourceTransactionManager;
//    }
//
//}
