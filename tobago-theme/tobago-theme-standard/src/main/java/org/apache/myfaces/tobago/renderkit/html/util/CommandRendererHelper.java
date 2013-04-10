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

package org.apache.myfaces.tobago.renderkit.html.util;

import org.apache.commons.lang.StringUtils;
import org.apache.myfaces.tobago.component.Attributes;
import org.apache.myfaces.tobago.component.Facets;
import org.apache.myfaces.tobago.component.UIPopup;
import org.apache.myfaces.tobago.event.PopupFacetActionListener;
import org.apache.myfaces.tobago.internal.component.AbstractUICommandBase;
import org.apache.myfaces.tobago.renderkit.util.RenderUtils;
import org.apache.myfaces.tobago.util.ComponentUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.component.UIComponent;
import javax.faces.component.ValueHolder;
import javax.faces.context.FacesContext;
import java.util.Arrays;

/**
 * @deprecated since 2.0.0. Please use {@link org.apache.myfaces.tobago.renderkit.html.CommandMap}
 */
@Deprecated
public class CommandRendererHelper {

  private static final Logger LOG = LoggerFactory.getLogger(CommandRendererHelper.class);

  private String onclick;
  private boolean disabled;
  private String href;
  private String target;

  public CommandRendererHelper(FacesContext facesContext, AbstractUICommandBase command) {
    this(facesContext, command, null);
  }

  public CommandRendererHelper(FacesContext facesContext, AbstractUICommandBase command, Tag tag) {

    disabled = ComponentUtils.getBooleanAttribute(command, Attributes.DISABLED);

    if (disabled) {
      onclick = "";
      href = "";
    } else {
      href = "#"; // this is to make the link "active", needed for focus, cursor, etc.
      UIPopup popup = (UIPopup) command.getFacet(Facets.POPUP);
      if (popup != null) {
        if (!ComponentUtils.containsPopupActionListener(command)) {
          command.addActionListener(new PopupFacetActionListener());
        }
      }

      boolean transition = ComponentUtils.getBooleanAttribute(command, Attributes.TRANSITION);

      if (StringUtils.isNotEmpty(command.getLink()) || StringUtils.isNotEmpty(command.getResource())) {
        String url = RenderUtils.generateUrl(facesContext, command);
        if (tag == Tag.ANCHOR) {
          onclick = null;
          href = url;
          target = command.getTarget();
        } else {
          // TODO target
          onclick = "Tobago.navigateToUrl('" + url + "');";
        }
      } else if (StringUtils.isNotEmpty(command.getOnclick())) {
        onclick = prepareOnClick(facesContext, command);
      } else if (command.getRenderedPartially().length > 0) {
        String clientId = command.getClientId(facesContext);
        String[] componentIds = command.getRenderedPartially();

        // TODO find a better way
        boolean popupAction = ComponentUtils.containsPopupActionListener(command);
        if (popupAction) {
          if (componentIds.length != 1) {
            LOG.warn("more than one partially rendered component is not supported for popup! using first one: "
                + Arrays.toString(componentIds));
          }
          onclick = "Tobago.Popup.openWithAction(this, '"
              + HtmlRendererUtils.getComponentId(facesContext, command, componentIds[0]) + "', '" + clientId + "');";
        } else {
          onclick = "Tobago.reloadComponent(this, '"
              + HtmlRendererUtils.getComponentIds(facesContext, command, componentIds) + "','" + clientId + "', {});";
        }

      } else {
        String clientId = command.getClientId(facesContext);
        String target = ComponentUtils.getStringAttribute(command, Attributes.TARGET);
        onclick = HtmlRendererUtils.createSubmitAction(clientId, transition, target, null);
      }

      if (command.getAttributes().get(Attributes.POPUP_CLOSE) != null
          && ComponentUtils.isInPopup(command)) {
        String value = (String) command.getAttributes().get(Attributes.POPUP_CLOSE);
        if (value.equals("immediate")) {
          onclick = "Tobago.Popup.close(this);";
        } else if (value.equals("afterSubmit")
            && command instanceof org.apache.myfaces.tobago.component.UICommand
            && ((org.apache.myfaces.tobago.component.UICommand) command).getRenderedPartially().length > 0) {
          onclick = "Tobago.Popup.unlockBehind();" + onclick + "Tobago.Popup.close(this);";
        }

      }
      onclick = appendConfirmationScript(onclick, command);
    }
  }

  private String prepareOnClick(FacesContext facesContext, AbstractUICommandBase component) {
    String onclick = component.getOnclick();
    if (onclick.contains("@autoId")) {
      onclick = StringUtils.replace(onclick, "@autoId", component.getClientId(facesContext));
    }
    return onclick;
  }

  private String appendConfirmationScript(String onclick, UIComponent component) {
    ValueHolder confirmation = (ValueHolder) component.getFacet(Facets.CONFIRMATION);
    if (confirmation != null) {
      StringBuilder script = new StringBuilder("return confirm('");
      script.append(confirmation.getValue());
      script.append("')");
      if (onclick != null) {
        script.append(" && ");
        script.append(onclick);
      }
      onclick = script.toString();
    }
    return onclick;
  }

  public String getOnclick() {
    return onclick;
  }

  public boolean isDisabled() {
    return disabled;
  }

  public String getHref() {
    return href;
  }

  public String getTarget() {
    return target;
  }

  public static enum Tag {
    ANCHOR, BUTTON
  }
}
