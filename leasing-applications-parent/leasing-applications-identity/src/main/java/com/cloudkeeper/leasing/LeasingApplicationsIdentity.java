package com.cloudkeeper.leasing;

import com.github.tobato.fastdfs.FdfsClientConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.servlet.MultipartConfigElement;

/**
 * exclude = SecurityAutoConfiguration.class springboot2.0与activiti6.0.0集成使用，该类会报错，排除后可以正常使用
 */
@SpringBootApplication
@EnableJpaAuditing
@EnableCaching
@EnableEurekaClient
@ServletComponentScan
@EnableScheduling
@Import(FdfsClientConfig.class)
public class LeasingApplicationsIdentity {

    public static void main(String[] args) {
        SpringApplication.run(LeasingApplicationsIdentity.class, args);
    }

    /**
     * 文件上传配置
     * @return
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //文件最大
        factory.setMaxFileSize("102400KB"); //KB,MB
        /// 设置总上传数据总大小
        factory.setMaxRequestSize("204800KB");
        return factory.createMultipartConfig();
    }

}
