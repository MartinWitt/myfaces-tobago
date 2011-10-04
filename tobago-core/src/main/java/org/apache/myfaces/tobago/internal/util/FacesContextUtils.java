package org.apache.myfaces.tobago.internal.util;

/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.commons.collections.list.SetUniqueList;
import org.apache.commons.collections.set.ListOrderedSet;
import org.apache.myfaces.tobago.compat.FacesUtils;
import org.apache.myfaces.tobago.internal.component.AbstractUIPopup;

import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public final class FacesContextUtils {
  private static String TOBAGO_AJAX = "org.apache.myfaces.tobago.ajax";
  private static String TOBAGO_AJAX_COMPONENT_ID = "org.apache.myfaces.tobago.ajaxComponentId";
  private static String TOBAGO_ENCTYPE = "org.apache.myfaces.tobago.enctype";
  private static String TOBAGO_SCRIPT_FILES = "org.apache.myfaces.tobago.scriptFiles";
  private static String TOBAGO_SCRIPT_BLOCKS = "org.apache.myfaces.tobago.scriptBlocks";
  private static String TOBAGO_STYLE_FILES = "org.apache.myfaces.tobago.styleFiles";
  private static String TOBAGO_STYLE_BLOCKS = "org.apache.myfaces.tobago.styleBlocks";
  private static String TOBAGO_ONLOAD_SCRIPTS = "org.apache.myfaces.tobago.onloadScripts";
  private static String TOBAGO_ONUNLOAD_SCRIPTS = "org.apache.myfaces.tobago.onunloadScripts";
  private static String TOBAGO_ONEXIT_SCRIPTS = "org.apache.myfaces.tobago.onexitScripts";
  private static String TOBAGO_ONSUBMIT_SCRIPTS = "org.apache.myfaces.tobago.onsubmitScripts";
  private static String TOBAGO_POPUPS = "org.apache.myfaces.tobago.popups";
          
  
  public static boolean isAjax(FacesContext context) {
    return FacesUtils.getFacesContextAttributes(context).containsKey(TOBAGO_AJAX);
  }

  public static void setAjax(FacesContext context, boolean ajax) {
    FacesUtils.getFacesContextAttributes(context).put(TOBAGO_AJAX, ajax);
  }

  public static String getAjaxComponentId(FacesContext context) {
    return (String) FacesUtils.getFacesContextAttributes(context).get(TOBAGO_AJAX_COMPONENT_ID);
  }

  public static void setAjaxComponentId(FacesContext context, String ajaxComponentId) {
    FacesUtils.getFacesContextAttributes(context).put(TOBAGO_AJAX_COMPONENT_ID, ajaxComponentId);
  }

  public static String getEnctype(FacesContext context) {
    return (String) FacesUtils.getFacesContextAttributes(context).get(TOBAGO_ENCTYPE);
  }

  public static void setEnctype(FacesContext context, String enctype) {
    FacesUtils.getFacesContextAttributes(context).put(TOBAGO_ENCTYPE, enctype);
  }

  @SuppressWarnings("unchecked")
  public static List<String> getScriptFiles(FacesContext context) {
    List<String> list = (List<String>) FacesUtils.getFacesContextAttributes(context).get(TOBAGO_SCRIPT_FILES);
    if (list == null) {
      return Collections.EMPTY_LIST;
    }
    return list;
  }
  
  public static void addScriptFile(FacesContext context, String file) {
    List<String> list = (List<String>) FacesUtils.getFacesContextAttributes(context).get(TOBAGO_SCRIPT_FILES);
    if (list == null) {
      list = SetUniqueList.decorate(new ArrayList());
      FacesUtils.getFacesContextAttributes(context).put(TOBAGO_SCRIPT_FILES, list);
    }
    list.add(file);

  }

  public static Set<String> getScriptBlocks(FacesContext context) {
     Set<String> set = (Set<String>) FacesUtils.getFacesContextAttributes(context).get(TOBAGO_SCRIPT_BLOCKS);
     if (set == null) {
      return Collections.EMPTY_SET;
    }
    return set;
  }

  public static void addScriptBlock(FacesContext context, String script) {
    Set<String> set = (Set<String>) FacesUtils.getFacesContextAttributes(context).get(TOBAGO_SCRIPT_BLOCKS);
    if (set == null) {
      set = new ListOrderedSet();
      FacesUtils.getFacesContextAttributes(context).put(TOBAGO_SCRIPT_BLOCKS, set);
    }
    set.add(script);
  }


  public static Set<String> getStyleFiles(FacesContext context) {
    Set<String> set = (Set<String>) FacesUtils.getFacesContextAttributes(context).get(TOBAGO_STYLE_FILES);
    if (set == null) {
      return Collections.EMPTY_SET;
    }
    return set;
  }

  public static void addStyleFile(FacesContext context, String script) {
    Set<String> set = (Set<String>) FacesUtils.getFacesContextAttributes(context).get(TOBAGO_STYLE_FILES);
    if (set == null) {
      set = new ListOrderedSet();
      FacesUtils.getFacesContextAttributes(context).put(TOBAGO_STYLE_FILES, set);
    }
    set.add(script);
  }

  public static Set<String> getStyleBlocks(FacesContext context) {
    Set<String> set = (Set<String>) FacesUtils.getFacesContextAttributes(context).get(TOBAGO_STYLE_BLOCKS);
    if (set == null) {
      return Collections.EMPTY_SET;
    }
    return set;
  }

  public static void addStyleBlock(FacesContext context, String script) {
    Set<String> set = (Set<String>) FacesUtils.getFacesContextAttributes(context).get(TOBAGO_STYLE_BLOCKS);
    if (set == null) {
      set = new ListOrderedSet();
      FacesUtils.getFacesContextAttributes(context).put(TOBAGO_STYLE_BLOCKS, set);
    }
    set.add(script);
  }

  public static List<String> getOnloadScripts(FacesContext context) {
    List<String> list = (List<String>) FacesUtils.getFacesContextAttributes(context).get(TOBAGO_ONLOAD_SCRIPTS);
    if (list == null) {
      return Collections.EMPTY_LIST;
    }
    return list;
  }


  public static void addOnloadScript(FacesContext context, String file) {
    List<String> list = (List<String>) FacesUtils.getFacesContextAttributes(context).get(TOBAGO_ONLOAD_SCRIPTS);
    if (list == null) {
      list = SetUniqueList.decorate(new ArrayList());
      FacesUtils.getFacesContextAttributes(context).put(TOBAGO_ONLOAD_SCRIPTS, list);
    }
    list.add(file);
  }

  public static void addOnloadScript(FacesContext context, int position, String file) {
    List<String> list = (List<String>) FacesUtils.getFacesContextAttributes(context).get(TOBAGO_ONLOAD_SCRIPTS);
    if (list == null) {
      list = SetUniqueList.decorate(new ArrayList());
      FacesUtils.getFacesContextAttributes(context).put(TOBAGO_ONLOAD_SCRIPTS, list);
    }
    list.add(position, file);
  }

  public static Set<String> getOnunloadScripts(FacesContext context) {
    Set<String> set = (Set<String>) FacesUtils.getFacesContextAttributes(context).get(TOBAGO_ONUNLOAD_SCRIPTS);
    if (set == null) {
      return Collections.EMPTY_SET;
    }
    return set;
  }

  public static void addOnunloadScript(FacesContext context, String script) {
    Set<String> set = (Set<String>) FacesUtils.getFacesContextAttributes(context).get(TOBAGO_ONUNLOAD_SCRIPTS);
    if (set == null) {
      set = new ListOrderedSet();
      FacesUtils.getFacesContextAttributes(context).put(TOBAGO_ONUNLOAD_SCRIPTS, set);
    }
    set.add(script);
  }

  public static Set<String> getOnexitScripts(FacesContext context) {
    Set<String> set = (Set<String>) FacesUtils.getFacesContextAttributes(context).get(TOBAGO_ONEXIT_SCRIPTS);
    if (set == null) {
      return Collections.EMPTY_SET;
    }
    return set;
  }

  public static void addOnexitScript(FacesContext context, String script) {
    Set<String> set = (Set<String>) FacesUtils.getFacesContextAttributes(context).get(TOBAGO_ONEXIT_SCRIPTS);
    if (set == null) {
      set = new ListOrderedSet();
      FacesUtils.getFacesContextAttributes(context).put(TOBAGO_ONEXIT_SCRIPTS, set);
    }
    set.add(script);
  }

  public static Set<String> getOnsubmitScripts(FacesContext context) {
    Set<String> set = (Set<String>) FacesUtils.getFacesContextAttributes(context).get(TOBAGO_ONSUBMIT_SCRIPTS);
    if (set == null) {
      return Collections.EMPTY_SET;
    }
    return set;
  }

  public static void addOnsubmitScript(FacesContext context, String script) {
    Set<String> set = (Set<String>) FacesUtils.getFacesContextAttributes(context).get(TOBAGO_ONSUBMIT_SCRIPTS);
    if (set == null) {
      set = new ListOrderedSet();
      FacesUtils.getFacesContextAttributes(context).put(TOBAGO_ONSUBMIT_SCRIPTS, set);
    }
    set.add(script);
  }

  public static Set<AbstractUIPopup> getPopups(FacesContext context) {
    Set<AbstractUIPopup> set = (Set<AbstractUIPopup>) FacesUtils.getFacesContextAttributes(context).get(TOBAGO_POPUPS);
    if (set == null) {
      return Collections.EMPTY_SET;
    }
    return set;
  }

  public static void addPopup(FacesContext context, AbstractUIPopup popup) {
    Set<AbstractUIPopup> set = (Set<AbstractUIPopup>) FacesUtils.getFacesContextAttributes(context).get(TOBAGO_POPUPS);
    if (set == null) {
      set = new ListOrderedSet();
      FacesUtils.getFacesContextAttributes(context).put(TOBAGO_POPUPS, set);
    }
    set.add(popup);
  }
}
