package com.marcobehler;

import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;
import java.sql.Driver;

@Configuration
@PropertySources({
        @PropertySource(value = "classpath:application.properties", ignoreResourceNotFound = true),
        @PropertySource(value = "classpath:application-${spring.profiles.active}.properties", ignoreResourceNotFound = true)
})
public class DefaultConfig {

    @Bean
    @Conditional(TomcatOnClasspathCondition.class)
    public Main.TomcatLauncher tomcatLauncher() {
        return new Main.TomcatLauncher();
    }


    @Bean
    @Conditional(DataSourcePropertiesSetCondition.class)
    public DataSource dataSource(Environment environment) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Driver driver = (Driver) Class.forName(environment.getProperty("spring.jdbc.driver")).newInstance();
        String url = environment.getProperty("spring.jdbc.url");
        return new SimpleDriverDataSource(driver, url);
    }
}
