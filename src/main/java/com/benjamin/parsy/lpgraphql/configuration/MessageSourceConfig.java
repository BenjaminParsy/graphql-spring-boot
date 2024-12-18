package com.benjamin.parsy.lpgraphql.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.nio.charset.StandardCharsets;

@Configuration
@Slf4j
public class MessageSourceConfig {

    private static final String[] MESSAGES_BASENAMES = {"classpath:messages/error"};

    @Bean
    public MessageSource messageSource() {

        log.debug("Construction of MessageSource bean");

        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames(MESSAGES_BASENAMES);
        messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());

        return messageSource;
    }

}
