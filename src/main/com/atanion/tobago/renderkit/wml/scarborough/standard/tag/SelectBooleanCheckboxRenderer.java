/*
 * Copyright (c) 2003 Atanion GmbH, Germany
 * All rights reserved. Created 07.02.2003 16:00:00.
 * : $
 */
package com.atanion.tobago.renderkit.wml.scarborough.standard.tag;

import com.atanion.tobago.renderkit.RendererBase;
import com.atanion.tobago.renderkit.html.HtmlRendererUtil;
import com.atanion.tobago.webapp.TobagoResponseWriter;
import com.atanion.tobago.component.ComponentUtil;
import com.atanion.tobago.TobagoConstants;

import javax.faces.context.FacesContext;
import javax.faces.component.UIComponent;
import java.io.IOException;

public class SelectBooleanCheckboxRenderer extends RendererBase {

  public void encodeEndTobago(FacesContext facesContext, UIComponent component)
      throws IOException {

    TobagoResponseWriter writer
        = (TobagoResponseWriter) facesContext.getResponseWriter();

    boolean value = ComponentUtil.getBooleanAttribute(component, ATTR_VALUE);

    writer.startElement("select", component);
    writer.writeAttribute("name", component.getClientId(facesContext), null);
    writer.writeAttribute("id", component.getClientId(facesContext), null);
    writer.writeAttribute("multiple", true);
    writer.startElement("option", null);
    writer.writeAttribute("value", value ? "on" : "off", null);

    UIComponent label = component.getFacet(TobagoConstants.FACET_LABEL);
    if (label != null) {
      HtmlRendererUtil.encodeHtml(facesContext, label);
    }

    writer.endElement("option");
    writer.endElement("select");
  }
}
