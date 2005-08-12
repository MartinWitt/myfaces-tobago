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
 * Copyright (c) 2003 Atanion GmbH, Germany
 * All rights reserved. Created 07.02.2003 16:00:00.
 * : $
 */
package org.apache.myfaces.tobago.renderkit.wml.standard.standard.tag;

import org.apache.myfaces.tobago.TobagoConstants;
import org.apache.myfaces.tobago.component.ComponentUtil;
import org.apache.myfaces.tobago.component.UIPage;
import org.apache.myfaces.tobago.renderkit.RenderUtil;
import org.apache.myfaces.tobago.renderkit.RendererBase;
import org.apache.myfaces.tobago.webapp.TobagoResponseWriter;
import org.apache.commons.collections.keyvalue.DefaultKeyValue;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import java.io.IOException;

public class InRenderer extends RendererBase {
// ----------------------------------------------------------------- interfaces


// ---------------------------- interface TobagoRenderer

  public void encodeEndTobago(FacesContext facesContext, UIComponent component)
      throws IOException {

    TobagoResponseWriter writer
        = (TobagoResponseWriter) facesContext.getResponseWriter();

    String clientId = component.getClientId(facesContext);

    UIPage uiPage = ComponentUtil.findPage(component);

    if (uiPage != null){
      uiPage.getPostfields().add(new DefaultKeyValue(clientId, clientId));
    }

    UIComponent label = component.getFacet(TobagoConstants.FACET_LABEL);
    if (label != null) {
      RenderUtil.encode(facesContext, label);
    }

    String currentValue = ComponentUtil.currentValue(component);

    String type = ComponentUtil.getBooleanAttribute(
        component, TobagoConstants.ATTR_PASSWORD) ? "password" : "text";

    writer.startElement("input", component);
    writer.writeAttribute("name", clientId, null);
    writer.writeAttribute("id", clientId, null);
    writer.writeAttribute("value", currentValue, null);
    writer.writeAttribute("type", type, null);
    writer.endElement("input");


  }
}

