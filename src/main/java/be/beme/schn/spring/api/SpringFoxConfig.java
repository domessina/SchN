package be.beme.schn.spring.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by Dotista on 22-05-16.
 */
@Configuration
@EnableSwagger2
public class SpringFoxConfig {

    /*
    * by default springfox responds to /v2/api-docs.
    *
    * spring-fow is a fork of swagger-springmvc project. it's newer
    *
    * http://springfox.github.io/springfox/docs/current/ pour la config
    * */

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.regex("/api/.*"))                      //path of or api
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo(
                "Narrative Scheme API",
                "This is a part of \"Narrative Applications Set\" project",
                "Version 1.0",
                "Terms of service",
                "dmessina@outlook.be",
                "API License",
                "API License URL"
        );
        return apiInfo;
    }
}