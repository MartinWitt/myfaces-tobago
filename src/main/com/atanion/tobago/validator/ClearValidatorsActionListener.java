/*
 * Copyright (c) 2003 Atanion GmbH, Germany
 * All rights reserved. Created 22.10.2003 16:57:20.
 * $Id$
 */
package com.atanion.tobago.validator;

import com.atanion.tobago.component.ComponentUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.faces.event.PhaseId;
import java.util.StringTokenizer;

public class ClearValidatorsActionListener implements ActionListener {

// ///////////////////////////////////////////// constant

  private static Log LOG
      = LogFactory.getLog(ClearValidatorsActionListener.class);

// ///////////////////////////////////////////// attribute

// ///////////////////////////////////////////// constructor

// ///////////////////////////////////////////// code

  public PhaseId getPhaseId() {
    return PhaseId.APPLY_REQUEST_VALUES;
  }

  public void processAction(ActionEvent actionEvent)
      throws AbortProcessingException {
    if (LOG.isDebugEnabled()) {
      LOG.debug("actionEvent = '" + actionEvent + "'");
    }
    UIComponent source = actionEvent.getComponent();
    String clearValidatorsFieldIds
        = (String) ComponentUtil.findParameter(source, "clearValidatorsFieldIds");

    if (LOG.isDebugEnabled()) {
      LOG.debug("clearValidatorsFieldIds = '" + clearValidatorsFieldIds + "'");
    }

    // fixme: finding mechanism??? JSF ???
   
    for (StringTokenizer tokenizer
        = new StringTokenizer(clearValidatorsFieldIds, ",");
         tokenizer.hasMoreTokens();) {
      String clearValidatorsFieldId = tokenizer.nextToken();

      UIComponent component = source.findComponent(clearValidatorsFieldId);
      if (LOG.isDebugEnabled()) {
        LOG.debug("component = '" + component + "'");
      }

      if (component == null) { // not found locally
        LOG.debug("Component not found locally, asking the tree.");
        FacesContext facesContext = FacesContext.getCurrentInstance();
        component = facesContext.getViewRoot().findComponent(clearValidatorsFieldId);
      }

      if (component == null) { // not found 
        LOG.warn("Component not found.");
      } else {
//        component.clearValidators();
        LOG.error("NO LONGER AVAILABLE: component.clearValidators();");
      }
    }
  }

// ///////////////////////////////////////////// bean getter + setter

}
