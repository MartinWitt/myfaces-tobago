/*
 * Copyright (c) 2003 Atanion GmbH, Germany
 * All rights reserved. Created 30.01.2003 17:00:56.
 * $Id$
 */
package com.atanion.tobago.webapp;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class TobagoMultipartFormdataFilter implements Filter {

// ///////////////////////////////////////////// constant

  private static Log log
      = LogFactory.getLog(TobagoMultipartFormdataFilter.class);

// ///////////////////////////////////////////// attribute

// ///////////////////////////////////////////// constructor

// ///////////////////////////////////////////// code

  public void init(FilterConfig filterConfig) throws ServletException {
  }

  public void doFilter(
      ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
//    Log.debug("filter filter ....");

    ServletRequest wrapper;
    if (request instanceof HttpServletRequest) {
      if (request instanceof TobagoMultipartFormdataRequest) {
        wrapper = request;
      } else {
        String contentType = request.getContentType();
        if (contentType != null &&
            contentType.toLowerCase().startsWith("multipart/form-data")) {
          wrapper = new TobagoMultipartFormdataRequest(
              (HttpServletRequest) request);
        } else {
          wrapper = request;
        }
      }
    } else {
      log.error("Not implemented for non HttpServletRequest");
      wrapper = request;
    }

    chain.doFilter(wrapper, response);
  }

  public void destroy() {
  }

// ///////////////////////////////////////////// bean getter + setter

}
