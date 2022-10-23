//package com.cjxjie.top.config.data;
//
//import cn.hutool.db.ds.pooled.DbConfig;
//import com.baomidou.mybatisplus.annotation.IdType;
//import com.baomidou.mybatisplus.core.MybatisConfiguration;
//import com.baomidou.mybatisplus.core.config.GlobalConfig;
//import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
//import com.cjxjie.top.common.handler.MyMetaObjectHandler;
//import com.cjxjie.top.config.MyBatisPlusConfig;
//import org.apache.ibatis.logging.stdout.StdOutImpl;
//import org.apache.ibatis.plugin.Interceptor;
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
// * @since 2022/09/21 18:34
// */
//@Configuration
//@MapperScan(basePackages = {"com.cjxjie.top.modules.sys.dao",
//        "com.cjxjie.top.modules.job.dao",
//        "com.cjxjie.top.modules.oss.dao"},
//        sqlSessionFactoryRef = "adminSqlSessionFactory")
//public class AdminDataSourceConfig {
//    @Primary
//    @Bean(name = "adminDataSource")
//    @ConfigurationProperties(prefix = "spring.datasource.admin")
//    public DataSource dataSource() {
//        return DataSourceBuilder.create().build();
//    }
//
//    @Bean(name = "adminSqlSessionFactory")
//    @Primary
//    public SqlSessionFactory sqlSessionFactory(@Qualifier("adminDataSource") DataSource dataSource) throws Exception {
////        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
//        //         // Mybatis 不能使用原生的会话工厂
//        MybatisSqlSessionFactoryBean sessionFactoryBean = new MybatisSqlSessionFactoryBean();
//        // MybatisPlus 的配置
//        MybatisConfiguration mybatisConfiguration = new MybatisConfiguration();
//        mybatisConfiguration.setLogImpl(StdOutImpl.class); // SQL日志打印
//        mybatisConfiguration.setMapUnderscoreToCamelCase(true); // 驼峰装换
//        GlobalConfig globalConfig = new GlobalConfig(); // MybatisPlus全局配置
//        globalConfig.setBanner(false); // 关闭MybatisPlus加载的Banner
//        globalConfig.setMetaObjectHandler(new MyMetaObjectHandler()); // 设置时间
//        globalConfig.setDbConfig(new GlobalConfig.DbConfig().setIdType(IdType.ASSIGN_UUID)); // 设置主键自增规则
//
//
//        sessionFactoryBean.setConfiguration(mybatisConfiguration);
//        sessionFactoryBean.setGlobalConfig(globalConfig);
//        // 设置插件
//        sessionFactoryBean.setPlugins(new Interceptor[]{new MyBatisPlusConfig().mybatisPlusInterceptor()});
//
//        sessionFactoryBean.setDataSource(dataSource);
//
//        // 映射文件
//        sessionFactoryBean.setMapperLocations(
//                new PathMatchingResourcePatternResolver().getResources("classpath*:/mapper/admin/**/*.xml"));
//        return sessionFactoryBean.getObject();
//    }
//
//    @Bean(name = "adminSqlSessionTemplate")
//    @Primary
//    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("adminSqlSessionFactory") SqlSessionFactory sessionFactory) throws Exception {
//        return new SqlSessionTemplate(sessionFactory);
//    }
//
//    @Bean(name = "adminTransactionManager")
//    @Primary
//    public DataSourceTransactionManager transactionManager(@Qualifier("adminDataSource") DataSource dataSource) {
//        return new DataSourceTransactionManager(dataSource);
//    }
//}
