/*
 * Copyright (c) 2003 Atanion GmbH, Germany
 * All rights reserved. Created 07.02.2003 16:00:00.
 * : $
 */
package com.atanion.tobago.renderkit.html.scarborough.mozilla.tag;

import com.atanion.tobago.renderkit.HeightLayoutRenderer;
import com.atanion.tobago.renderkit.RendererBase;
import com.atanion.tobago.TobagoConstants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import java.io.IOException;

public class GroupBoxRenderer extends
    com.atanion.tobago.renderkit.html.scarborough.standard.tag.GroupBoxRenderer {

  protected String getAttrStyleKey() {
    return TobagoConstants.ATTR_STYLE_BODY;
  }

  public int getComponentExtraWidth(FacesContext facesContext,
      UIComponent component) {
    return 24; // this value is find out by try and error
  }

  public int getPaddingHeight(FacesContext facesContext, UIComponent component) {
    return 14; // this value is find out by try and error
  }

  public int getHeaderHeight(FacesContext facesContext, UIComponent component) {
    return 28; // this value is find out by try and error
  }

// ///////////////////////////////////////////// bean getter + setter

}

