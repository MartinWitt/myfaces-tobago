/*
 * Copyright (c) 2003 Atanion GmbH, Germany
 * All rights reserved. Created 07.02.2003 16:00:00.
 * : $
 */
package com.atanion.tobago.renderkit.html.speyside.standard.tag;

import com.atanion.tobago.TobagoConstants;
import com.atanion.tobago.component.ComponentUtil;
import com.atanion.tobago.context.ClientProperties;
import com.atanion.tobago.context.TobagoResource;
import com.atanion.tobago.context.UserAgent;
import com.atanion.tobago.renderkit.DirectRenderer;
import com.atanion.tobago.renderkit.HeightLayoutRenderer;
import com.atanion.tobago.renderkit.RenderUtil;
import com.atanion.tobago.renderkit.SelectManyRendererBase;
import com.atanion.tobago.util.LayoutUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.faces.component.UIComponent;
import javax.faces.component.UISelectMany;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.model.SelectItem;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class MultiSelectRenderer extends SelectManyRendererBase
 implements HeightLayoutRenderer, DirectRenderer {

// ///////////////////////////////////////////// constant

  private static final Log LOG = LogFactory.getLog(MultiSelectRenderer.class);

// ///////////////////////////////////////////// attribute

// ///////////////////////////////////////////// constructor

// ///////////////////////////////////////////// code

  public int getComponentExtraWidth(FacesContext facesContext, UIComponent component) {
    int space = 0;

    if (component.getFacet(TobagoConstants.FACET_LABEL) != null) {
      int labelWidth = LayoutUtil.getLabelWidth(component);
      space += labelWidth != 0 ? labelWidth : getLabelWidth();
      space += 5; // space (5px)
    }

    return space;
  }

  public int getLabelWidth() {
    return 120;
  }

  public int getFixedHeight(FacesContext facesContext, UIComponent component) {
    int fixedHeight = -1;
    String height = (String) component.getAttributes().get(TobagoConstants.ATTR_HEIGHT);
    if (height != null) {
      try {
        fixedHeight = Integer.parseInt(height.replaceAll("\\D", "") );
      } catch (NumberFormatException e) {
        LOG.warn("Can't parse " + height + " to int");
      }
    }

    if (fixedHeight == -1) {
      fixedHeight = super.getFixedHeight(facesContext, component);
      UserAgent browser = ClientProperties.getInstance(facesContext).getUserAgent();
      if (browser.toString().startsWith("msie")) {
        fixedHeight = 21;
      }
    }
    return fixedHeight;
  }

  public int getHeaderHeight(FacesContext facesContext, UIComponent component) {
    return 0;
  }


  public void encodeDirectEnd(FacesContext facesContext,
      UIComponent uiComponent) throws IOException {

    UISelectMany component = (UISelectMany) uiComponent;

    List items = ComponentUtil.getSelectItems(component);

    if (LOG.isDebugEnabled()) {
      LOG.debug("items.size() = '" + items.size() + "'");
    }

    boolean isInline = ComponentUtil.isInline(component);

    UIComponent label = component.getFacet(TobagoConstants.FACET_LABEL);

    String image = TobagoResource.getImage(facesContext, "1x1.gif");

    Integer rows = (Integer) component.getAttributes().get(TobagoConstants.ATTR_ROWS);

    ResponseWriter writer = facesContext.getResponseWriter();

    if (!isInline) {
      writer.startElement("table", null);
      writer.writeAttribute("border", "0", null);
      writer.writeAttribute("cellspacing", "0", null);
      writer.writeAttribute("cellpadding", "0", null);
      writer.writeAttribute("summary", "", null);

      writer.startElement("tr", null);

      if (label != null) {
        writer.startElement("td", null);
        writer.writeAttribute("class", "tobago-label-td", null);
        writer.writeAttribute("valign", "top", null);

        writer.writeText("", null);
        RenderUtil.encode(facesContext, label);

        writer.endElement("td");
        writer.startElement("td", null);

        writer.startElement("img", null);
        writer.writeAttribute("src", image, null);
        writer.writeAttribute("border", "0", null);
        writer.writeAttribute("height", "1", null);
        writer.writeAttribute("width", "5", null);
        writer.endElement("img");

        writer.endElement("td");
      }
      writer.startElement("td", null);
      writer.writeAttribute("valign", "top", null);
      writer.writeAttribute("rowspan", "2", null);
    }

    String clientId = component.getClientId(facesContext);

    writer.startElement("select", component);
    writer.writeAttribute("name", clientId, null);
    writer.writeAttribute("id", clientId, null);
    if (ComponentUtil.isDisabled(component)) {
      writer.writeAttribute("disabled", "disabled", null);
    }
    writer.writeAttribute("style", null, "style");
    writer.writeAttribute("class", null, TobagoConstants.ATTR_STYLE_CLASS);
    writer.writeAttribute("multiple", "multiple", null);

    Object[] values = component.getSelectedValues();
    if (LOG.isDebugEnabled()) {
      LOG.debug("values = '" + values + "'");
    }
    for (Iterator i = items.iterator(); i.hasNext(); ) {
      SelectItem item = (SelectItem) i.next();

      writer.startElement("option", null);
      writer.writeAttribute("value", item.getValue(), null);
      if (RenderUtil.contains(values, item.getValue())) {
        writer.writeAttribute("selected", "selected", null);
      }
      writer.writeText(item.getLabel(), null);
      writer.endElement("option");
//    LOG.debug("item-value" + item.getValue());
    }
    writer.endElement("select");

    if (!isInline) {
      writer.endElement("td");

      writer.endElement("tr");
      writer.startElement("tr", null);

      if (label != null) {
        writer.startElement("td", null);
        writer.writeAttribute("class", "tobago-label-td-underline-label", null);

        writer.startElement("img", null);
        writer.writeAttribute("src", image, null);
        writer.writeAttribute("border", "0", null);
        writer.writeAttribute("height", "1", null);
        writer.endElement("img");

        writer.endElement("td");

        writer.startElement("td", null);
        writer.writeAttribute("class", "tobago-label-td-underline-spacer", null);

        writer.startElement("img", null);
        writer.writeAttribute("src", image, null);
        writer.writeAttribute("border", "0", null);
        writer.writeAttribute("height", "1", null);
        writer.endElement("img");

        writer.endElement("td");
      }
      writer.endElement("tr");
      writer.endElement("table");
    }
  }

// ///////////////////////////////////////////// bean getter + setter

}

