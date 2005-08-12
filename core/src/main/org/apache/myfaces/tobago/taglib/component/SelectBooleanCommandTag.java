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
package org.apache.myfaces.tobago.taglib.component;

import org.apache.myfaces.tobago.component.ComponentUtil;
import org.apache.myfaces.tobago.component.UICommand;

import javax.faces.component.UIComponent;

/**
 * Created by IntelliJ IDEA.
 * User: weber
 * Date: Apr 26, 2005
 * Time: 3:01:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class SelectBooleanCommandTag extends CommandTag {
  public static final String COMMAND_TYPE = "commandSelectBoolean";
  private String label;
  private String accessKey;
  private String labelWithAccessKey;
  private String value;

  public String getComponentType() {
    return UICommand.COMPONENT_TYPE;
  }

  protected void setProperties(UIComponent component) {
    super.setProperties(component);

    component.setRendererType(RENDERER_TYPE_MENUCOMMAND);

    ComponentUtil.setStringProperty(component, ATTR_VALUE, value, getIterationHelper());
    ComponentUtil.setStringProperty(component, ATTR_COMMAND_TYPE, COMMAND_TYPE, getIterationHelper());
    ComponentUtil.setStringProperty(component, ATTR_LABEL, label, getIterationHelper());
    ComponentUtil.setStringProperty(component, ATTR_ACCESS_KEY, accessKey, getIterationHelper());
    ComponentUtil.setStringProperty(component, ATTR_LABEL_WITH_ACCESS_KEY, labelWithAccessKey, getIterationHelper());
  }

  public void release() {
    super.release();
    value = null;
    label = null;
    accessKey = null;
    labelWithAccessKey = null;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public void setAccessKey(String accessKey) {
    this.accessKey = accessKey;
  }

  public void setLabelWithAccessKey(String labelWithAccessKey) {
    this.labelWithAccessKey = labelWithAccessKey;
  }
}
