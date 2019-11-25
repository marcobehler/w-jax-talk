package com.marcobehler;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class TomcatOnClasspathCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {

        try {
            Class.forName("org.apache.catalina.startup.Tomcat");
            return true;
        } catch (ClassNotFoundException e) {
           return false;
        }
    }
}
