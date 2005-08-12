/*
 * Copyright 2002-2005 atanion GmbH.
 * 
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 * 
 *        http://www.apache.org/licenses/LICENSE-2.0
 * 
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
/*
 * Copyright (c) 2001 Atanion GmbH, Germany
 * All rights reserved.
 * Created on: 15.02.2002, 17:01:56
 * $Id$
 */
package org.apache.myfaces.tobago.taglib.component;

import org.apache.myfaces.tobago.component.ComponentUtil;

import javax.faces.component.UIComponent;
import javax.faces.component.UIOutput;

public class OutTag extends BeanTag implements
    org.apache.myfaces.tobago.taglib.decl.OutTag {
// ----------------------------------------------------------------- attributes

  private String escape = "true";
  private String markup;
  private String tip;

// ----------------------------------------------------------- business methods

  public String getComponentType() {
    return UIOutput.COMPONENT_TYPE;
  }

  public void release() {
    super.release();
    escape = "true";
    markup = null;
    tip = null;
  }

  protected void setProperties(UIComponent component) {
    super.setProperties(component);
    ComponentUtil.setBooleanProperty(component, ATTR_ESCAPE, escape, getIterationHelper());
    // TODO ???? SPAN ?
    ComponentUtil.setBooleanProperty(component, ATTR_CREATE_SPAN, "true", getIterationHelper());
    ComponentUtil.setStringProperty(component, ATTR_MARKUP, markup, getIterationHelper());
    ComponentUtil.setStringProperty(component, ATTR_TIP, tip, getIterationHelper());
  }

// ------------------------------------------------------------ getter + setter


  public String getEscape() {
    return escape;
  }


  public void setEscape(String escape) {
    this.escape = escape;
  }

  public String getMarkup() {
    return markup;
  }

  public void setMarkup(String markup) {
    this.markup = markup;
  }

  public String getTip() {
    return tip;
  }

  public void setTip(String tip) {
    this.tip = tip;
  }
}
