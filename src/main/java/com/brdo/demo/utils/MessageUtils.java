package com.brdo.demo.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@SuppressWarnings("java:S2696")
public class MessageUtils {

  private static MessageSource messageSource;

  @Autowired
  public void setMessageSource(MessageSource messageSource) {
    MessageUtils.messageSource = messageSource;
  }

  public static String getMessage(String key, Object... params) {
    return messageSource.getMessage(key, params, Locale.getDefault());
  }
}
