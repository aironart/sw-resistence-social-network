package br.com.airon.challenges.swresistencesocialnetwork.config;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.ApiSelector;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static springfox.documentation.builders.PathSelectors.ant;

@Configuration
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {

    @Bean
    public Docket apiItems() {

        Predicate<String> pathSelector = ApiSelector.DEFAULT.getPathSelector();

        return new Docket(DocumentationType.SWAGGER_2)
//                .groupName("apiItems")
                .select()
                .apis(RequestHandlerSelectors.any())
//                .apis(RequestHandlerSelectors.basePackage("br.com.airon.challenges.swresistencesocialnetwork.controller"))
                .paths(
                        pathSelector.and(ant("/items"))
                                    .or(ant("/items/**"))
                                    .or(ant("/neg**/**"))
                                    .or(ant("/rebelde/**"))
                )
                .build()
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, responseMessageForGetMethods());
    }

//    @Bean
    public Docket apiController() {

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Controllers")
                .select()
                .apis(RequestHandlerSelectors.basePackage("br.com.airon.challenges.swresistencesocialnetwork.controller"))
                .build()
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, responseMessageForGetMethods());
    }


    private List<ResponseMessage> responseMessageForGetMethods() {
        return new ArrayList<ResponseMessage>() {{
            add(new ResponseMessageBuilder()
                    .code(500)
                    .message("Deu um erro interno")
                    .responseModel(new ModelRef("Error"))
                    .build());
            add(new ResponseMessageBuilder()
                    .code(403)
                    .message("Acesso Negado")
                    .build());
        }};
    }

}
