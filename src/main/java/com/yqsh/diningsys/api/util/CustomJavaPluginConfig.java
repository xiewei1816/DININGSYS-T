package com.yqsh.diningsys.api.util;
import com.yqsh.diningsys.core.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.models.dto.ApiInfo;
import com.mangofactory.swagger.paths.SwaggerPathProvider;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;


@EnableSwagger
public class CustomJavaPluginConfig {
        @Autowired
        private SpringSwaggerConfig springSwaggerConfig;

    public SpringSwaggerConfig getSpringSwaggerConfig() {
        return springSwaggerConfig;
    }

    public void setSpringSwaggerConfig(SpringSwaggerConfig springSwaggerConfig) {
        this.springSwaggerConfig = springSwaggerConfig;
    }
        
	

	/**
	 * 链式编程 来定制API样式 后续会加上分组信息
	 * 
	 * @return
	 */
	@Bean
	public SwaggerSpringMvcPlugin customImplementation() {
		return new SwaggerSpringMvcPlugin(this.springSwaggerConfig)
				.apiInfo(apiInfo()).includePatterns("/yqshapi/.*?")
				.useDefaultResponseMessages(false)
			//	.pathProvider(new GtPaths())
				.apiVersion("0.1").swaggerGroup("user");

	}

	private ApiInfo apiInfo() {
		ApiInfo apiInfo = new ApiInfo("", "", "www.yqsh.com", "", "", "www.yqsh.com");
		return apiInfo;
	}

}