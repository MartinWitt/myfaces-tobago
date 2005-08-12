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
 * Created on: 19.02.2002, 19:01:56
 * $Id$
 */
package org.apache.myfaces.tobago.taglib.component;

import org.apache.myfaces.tobago.component.ComponentUtil;
import org.apache.myfaces.tobago.component.UIColumnSelector;
import org.apache.myfaces.tobago.taglib.decl.HasBinding;
import org.apache.myfaces.tobago.taglib.decl.IsDisabled;
import org.apache.myfaces.tobago.taglib.decl.IsRendered;
import org.apache.myfaces.tobago.apt.annotation.BodyContent;
import org.apache.myfaces.tobago.apt.annotation.Tag;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.faces.component.UIComponent;

/**
 * Renders a column with checkboxes to mark selected row's.
 */
@Tag(name="columnSelector", bodyContent=BodyContent.EMPTY)
public class ColumnSelectorTag extends TobagoTag
    implements IsDisabled, IsRendered, HasBinding
    {

  private static final Log LOG = LogFactory.getLog(ColumnSelectorTag.class);

  private String disabled ;

  public String getComponentType() {
    return UIColumnSelector.COMPONENT_TYPE;
  }

  protected void setProperties(UIComponent component) {
    super.setProperties(component);
   ComponentUtil.setStringProperty(component, ATTR_DISABLED, disabled, getIterationHelper());
  }

  public void release() {
    super.release();
    disabled = null;
  }

  public String getDisabled() {
    return disabled;
  }

  public void setDisabled(String disabled) {
    this.disabled = disabled;
  }
}
