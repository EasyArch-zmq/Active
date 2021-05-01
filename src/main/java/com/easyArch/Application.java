package com.easyArch;



import com.easyArch.net.Server;
import org.apache.log4j.Logger;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;


@SpringBootApplication
@MapperScan(value = "com.example.demo.mapper")
@Configuration
public class Application extends WebMvcConfigurationSupport
implements CommandLineRunner {
    private static final Logger logger=Logger.getLogger(Application.class);
    public static void main(String[] args) {
        logger.info("项目启动了");
        SpringApplication.run(Application.class,args);
    }
    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*"); // 1 允许任何域名使用
        corsConfiguration.addAllowedHeader("*"); // 2 允许任何头
        corsConfiguration.addAllowedMethod("*"); // 3 允许任何方法（post、get等）
        return corsConfiguration;
    }

    /**
     * 跨域过滤器
     *
     * @return
     */
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig()); // 4
        CorsFilter corsFilter = new CorsFilter(source);
        return corsFilter;
    }
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        //因为有这一句，templates目录下的静态资源才能被加载，适用非标准目录
        registry.addResourceHandler("/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/templates/");
        //因为有这一句，static目录下的静态资源才能被加载，适用标准目录
        registry.addResourceHandler("/static/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/static/");
        super.addResourceHandlers(registry);
    }

    @Value("${netty.server.port}")
    private int port;
    @Autowired
    private Server server;


    @Override
    public void run(String... args) {
        server.run(port);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> server.stop()));
    }
}
