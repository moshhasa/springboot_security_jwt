package com.moshhsa.util;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class MessageResource {

    private final MessageSource messageSource;

    public MessageResource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getMessage(final String key) {
        return getMessage(key, "");
    }

    public String getMessage(final String key, Object[] args) {
        return messageSource.getMessage(key, args, "", LocaleContextHolder.getLocale());
    }

    public String getMessage(String key, String defaultMessage) {
        return messageSource.getMessage(key, null, defaultMessage, LocaleContextHolder.getLocale());
    }
}
