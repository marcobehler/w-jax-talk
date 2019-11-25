package com.marcobehler;

import com.sun.xml.internal.ws.api.streaming.XMLStreamReaderFactory;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {

    public static class TomcatLauncher {

        @PostConstruct
        public void launch() throws LifecycleException {
            Tomcat tomcat = new Tomcat();
            tomcat.setPort(8080);

            // localhost:8080//

            Context context = tomcat.addContext("", null);
            Tomcat.addServlet(context, "hello", HelloWorldServlet.class.getName());
            context.addServletMappingDecoded("/", "hello");

            tomcat.start();

            new Thread(() -> tomcat.getServer().await()).start();
        }
    }


    @Configuration
    @Import(DefaultConfig.class) // == @SpringBootApplication
    public static class MyConfig {
        //...
    }

    public static void main(String[] args) {

        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(MyConfig.class);
        DataSource ds = ctx.getBean(DataSource.class);

        try (Connection connection = ds.getConnection()) {
            System.out.println("Habe ich eine validate db anbindung? " + connection.isValid(0));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
