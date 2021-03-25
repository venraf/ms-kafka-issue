//package my.company.project.service.acceptance.common.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Profile;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;
//
//import javax.sql.DataSource;
//
//@Profile("DEV")
//@Configuration
//public class DatabaseConfiguration {
//    @Value("${spring.datasource.url}")
//    private String dbUrl;
//    @Value("${spring.datasource.username}")
//    private String dbUsername;
//    @Value("${spring.datasource.password}")
//    private String dbPassword;
//
//    @Bean
//    public DataSource dataSource() {
//        return new DriverManagerDataSource(dbUrl, dbUsername, dbPassword);
//    }
//
//    @Bean
//    @Autowired
//    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
//        return new JdbcTemplate(dataSource);
//    }
//}