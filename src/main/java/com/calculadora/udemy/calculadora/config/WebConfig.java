package com.calculadora.udemy.calculadora.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.calculadora.udemy.calculadora.serializationConverter.YamlJackson2HttpMesageConverter;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  private static final MediaType MEDIA_TYPE_APPLICATION_YML = MediaType.valueOf("application/x-yaml");

  @Override
  public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
    converters.add(new YamlJackson2HttpMesageConverter());
  }

  @Override
  public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {

    // query paramets http://localhost:8080/api/person/v1/3?mediaType=json

    // configurer.favorParameter(true)
    // .parameterName("mediaType")
    // .ignoreAcceptHeader(true)
    // .useRegisteredExtensionsOnly(false)
    // .defaultContentType(MediaType.APPLICATION_JSON)
    //   .mediaType("json", MediaType.APPLICATION_JSON)
    //   .mediaType("xml", MediaType.APPLICATION_XML);


    // Via header
    configurer.favorParameter(false)
    .ignoreAcceptHeader(false)
    .useRegisteredExtensionsOnly(false)
    .defaultContentType(MediaType.APPLICATION_JSON)
      .mediaType("json", MediaType.APPLICATION_JSON)
      .mediaType("xml", MediaType.APPLICATION_XML)
      .mediaType("x-yaml", MEDIA_TYPE_APPLICATION_YML);
  }
}
  
