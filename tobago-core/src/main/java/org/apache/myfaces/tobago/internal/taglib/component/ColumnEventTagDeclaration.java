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

package org.apache.myfaces.tobago.internal.taglib.component;

import org.apache.myfaces.tobago.apt.annotation.Tag;
import org.apache.myfaces.tobago.apt.annotation.TagAttribute;
import org.apache.myfaces.tobago.apt.annotation.UIComponentTag;
import org.apache.myfaces.tobago.apt.annotation.UIComponentTagAttribute;
import org.apache.myfaces.tobago.internal.taglib.declaration.HasIdBindingAndRendered;

/**
 * This component creates an event in a UISheet for each row.
 */
@Tag(name = "columnEvent")
@UIComponentTag(uiComponent = "org.apache.myfaces.tobago.component.UIColumnEvent",
    uiComponentBaseClass = "javax.faces.component.UIColumn",
    interfaces = "org.apache.myfaces.tobago.component.ColumnEvent",
    componentType = "org.apache.myfaces.tobago.ColumnEvent")
public interface ColumnEventTagDeclaration extends HasIdBindingAndRendered {

  @TagAttribute
  @UIComponentTagAttribute(type = "java.lang.String", allowedValues = {"click", "dblclick"})
  void setEvent(String event);
}
