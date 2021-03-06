package com.example.demo;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

import javax.servlet.ServletContextListener;
import java.util.Arrays;
import java.util.Collections;

@SpringBootApplication
public class Main {

    @Bean
    ServletRegistrationBean<MyServlet> myServletRegistration() {
        ServletRegistrationBean<MyServlet> srb = new ServletRegistrationBean<>();
        srb.setServlet(new MyServlet());
        srb.setUrlMappings(Arrays.asList("/path2/*"));
        return srb;
    }

    @Bean
    ServletListenerRegistrationBean<ServletContextListener> myServletListener () {
        ServletListenerRegistrationBean<ServletContextListener> srb =
                new ServletListenerRegistrationBean<>();
        srb.setListener(new MyServletListener());
        return srb;
    }

    @Bean
    FilterRegistrationBean myFilterRegistration() {
        FilterRegistrationBean frb = new FilterRegistrationBean();
        frb.setFilter(new MyFilter());
        frb.setUrlPatterns(Arrays.asList("/*"));
        return frb;
    }

    public static void main(String[] args) {
        SpringApplication sa = new SpringApplication(Main.class);
        sa.setLogStartupInfo(false);
        sa.setBannerMode(Banner.Mode.OFF);
        sa.run(args);
    }


}