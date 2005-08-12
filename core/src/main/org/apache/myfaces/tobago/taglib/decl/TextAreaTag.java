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
package org.apache.myfaces.tobago.taglib.decl;

import org.apache.myfaces.tobago.apt.annotation.BodyContent;
import org.apache.myfaces.tobago.apt.annotation.Tag;
import org.apache.myfaces.tobago.apt.annotation.TagAttribute;
import org.apache.myfaces.tobago.apt.annotation.UIComponentTagAttribute;

/*
 * Copyright (c) 2003 Atanion GmbH, Germany. All rights reserved.
 * Created: Aug 5, 2005 5:00:41 PM
 * User: bommel
 * $Id: $
 */

/**
 * Renders a multiline text input control.
 */
@Tag(name="textarea", bodyContent= BodyContent.EMPTY)
public interface TextAreaTag extends TextInputTag, HasIdBindingAndRendered, HasValue, IsReadonly, IsDisabled, HasDimension, HasOnchangeListener, IsFocus, IsRequired, HasLabelAndAccessKey, HasTip {

  /**
   *  The row count for this component.
   */
  @TagAttribute
  @UIComponentTagAttribute()
  void setRows(String rows);
}
