package com.gmail.puhovashablinskaya.repository.feign;

import com.gmail.puhovashablinskaya.security.util.ConfigTokenConstants;
import com.gmail.puhovashablinskaya.security.util.SecurityUtilConfig;
import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@Configuration
@EnableFeignClients(basePackages = "com.gmail.puhovashablinskaya")
public class FeignConfig {
    @Autowired
    private SecurityUtilConfig securityUtilConfig;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            requestTemplate.header(HttpHeaders.AUTHORIZATION, ConfigTokenConstants.TOKEN_TYPE + securityUtilConfig.getJwtSystem());
            requestTemplate.header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        };
    }
}
