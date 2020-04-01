/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.myfaces.tobago.internal.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import java.lang.annotation.Annotation;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* The JSF annotations are, because it has to be runnable without CDI */
@ManagedBean
@javax.faces.bean.ApplicationScoped
public class AuthorizationHelper {

  private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  public static final String AUTHORIZATION_HELPER = "authorizationHelper";

  private static final Pattern PATTERN = Pattern.compile("#\\{(\\w+(?:\\.\\w+)*)\\.(\\w+)(?:\\(.*\\))?}");

  private static final Annotation NULL_VALUE = new Annotation() {
    @Override
    public Class<? extends Annotation> annotationType() {
      return null;
    }

    @Override
    public String toString() {
      return "(NULL)";
    }
  };

  private final Map<String, Object> cache = new ConcurrentHashMap<>();

  private AuthorizationHelperCdi cdi;

  public AuthorizationHelper() {

    try {
      Class.forName("javax.enterprise.inject.spi.BeanManager");
      cdi = new AuthorizationHelperCdi();
      if (!cdi.hasBeanManager()) {
        cdi = null;
      }
    } catch (ClassNotFoundException e) {
      // no cdi available
    }
  }

  public static AuthorizationHelper getInstance(final FacesContext facesContext) {
    return (AuthorizationHelper)
        facesContext.getELContext().getELResolver().getValue(facesContext.getELContext(), null, AUTHORIZATION_HELPER);
  }

  public boolean isAuthorized(final FacesContext facesContext, final String expression) {

    final Annotation securityAnnotation = getSecurityAnnotation(facesContext, expression);
    if (securityAnnotation == null) {
      return true;
    }

    if (securityAnnotation instanceof DenyAll) {
      if (LOG.isDebugEnabled()) {
        LOG.debug("DenyAll");
      }
      return false;
    }
    if (securityAnnotation instanceof RolesAllowed) {
      final String[] roles = ((RolesAllowed) securityAnnotation).value();
      if (LOG.isDebugEnabled()) {
        LOG.debug("RolesAllowed " + Arrays.asList(((RolesAllowed) securityAnnotation).value()));
      }
      for (final String role : roles) {
        final boolean authorised = facesContext.getExternalContext().isUserInRole(role);
        if (authorised) {
          return true;
        }
      }
      return false;
    }
    if (securityAnnotation instanceof PermitAll) {
      if (LOG.isDebugEnabled()) {
        LOG.debug("PermitAll");
      }
      return true;
    }
    return true;
  }

  private Annotation getSecurityAnnotation(final FacesContext facesContext, final String expression) {
    if (cache.containsKey(expression)) {
      final Object obj = cache.get(expression);
      if (obj instanceof Annotation) {
        return (Annotation) obj;
      }
      return null;
    } else {
      Annotation securityAnnotation = null;
      final Matcher matcher = PATTERN.matcher(expression);
      if (matcher.matches()) {
        final String beanString = matcher.group(1);
        final String methodString = matcher.group(2);

        final Object bean;
        if (cdi != null) { // CDI case
          bean = cdi.getObject(beanString);
        } else { // JSF case
          bean = facesContext.getELContext().getELResolver().getValue(facesContext.getELContext(), null, beanString);
        }

        if (bean != null) {
          // try first from method
          final List<Method> methods = findMethods(bean, methodString);
          switch (methods.size()) {
            case 0:
              LOG.error("No Method '" + methodString + "' in class " + bean.getClass());
              break;
            case 1:
              securityAnnotation = getSecurityAnnotations(methods.get(0));
              break;
            default:
              LOG.warn("Method name ambiguous '" + methodString + "' in class " + bean.getClass()
                  + ". Found " + methods.size() + " but only 1 is supported, yet.");
          }
          // if not set, try from class
          if (securityAnnotation == null) {
            securityAnnotation = getSecurityAnnotations(bean.getClass());
          }
        }
      }
      if (securityAnnotation == null) {
        securityAnnotation = NULL_VALUE;
      }

      cache.put(expression, securityAnnotation);
      if (LOG.isInfoEnabled()) {
        LOG.info("Security annotation '{}' saved for expression '{}'", securityAnnotation, expression);
      }

      return securityAnnotation;
    }
  }

  private Annotation getSecurityAnnotations(final AnnotatedElement annotatedElement) {
    Annotation annotation = annotatedElement.getAnnotation(RolesAllowed.class);
    if (annotation != null) {
      return annotation;
    }
    annotation = annotatedElement.getAnnotation(DenyAll.class);
    if (annotation != null) {
      return annotation;
    }
    annotation = annotatedElement.getAnnotation(PermitAll.class);
    return annotation;
  }

  private List<Method> findMethods(final Object bean, final String name) {
    final Class clazz;
    if (cdi != null) {
      clazz = cdi.getBeanClass(bean);
    } else {
      clazz = bean.getClass();
    }

    final Method[] methods = clazz.getMethods();
    final List<Method> result = new ArrayList<>();
    for (final Method method : methods) {
      if (method.getName().equals(name)) {
        result.add(method);
      }
    }
    return result;
  }

}