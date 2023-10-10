package com.waff.rest.demo.config;
import java.beans.Beans;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

// In dieser Klasse handelt es sich um eine Konfigurationsklasse für die Erstellung eines ModelMapper-Beans. 
// Die Methode modelMapper() wird mit @Bean annotiert und gibt eine Instanz von ModelMapper zurück. 
// Diese Instanz kann dann für das Mapping von Objekten in der Anwendung verwendet werden. 
// Die Klasse wird durch die Annotation @Configuration als Konfigurationsklasse gekennzeichnet.

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
