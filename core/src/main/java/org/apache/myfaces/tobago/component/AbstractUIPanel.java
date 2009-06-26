package org.apache.myfaces.tobago.component;

/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.tobago.OnComponentCreated;
import org.apache.myfaces.tobago.layout.LayoutComponent;
import org.apache.myfaces.tobago.layout.LayoutContainer;
import org.apache.myfaces.tobago.layout.LayoutManager;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractUIPanel extends UIPanelBase implements LayoutContainer {

  private static final Log LOG = LogFactory.getLog(AbstractUIPanel.class);

  @Override
  public void encodeBegin(FacesContext facesContext) throws IOException {

    super.encodeBegin(facesContext);

    UILayout layoutManager = (UILayout) getLayoutManager();
    if (layoutManager != null) {
      layoutManager.encodeBegin(facesContext);
    } else {
      // todo: later: LOG.debug or remove
      LOG.warn("no layout manager found");
    }
  }

  @Override
  public void encodeChildren(FacesContext facesContext) throws IOException {

    UILayout layoutManager = (UILayout) getLayoutManager();
    if (layoutManager != null) {
      layoutManager.encodeChildren(facesContext);
    } else {
      // todo: later: LOG.debug or remove
      LOG.warn("no layout manager found");
      super.encodeChildren(facesContext);
    }
  }

  @Override
  public void encodeEnd(FacesContext facesContext) throws IOException {

    UILayout layoutManager = (UILayout) getLayoutManager();
    if (layoutManager != null) {
      layoutManager.encodeEnd(facesContext);
    } else {
      // todo: later: LOG.debug or remove
      LOG.warn("no layout manager found");
    }

    super.encodeEnd(facesContext);
  }

  public List<LayoutComponent> getComponents() {
    List<LayoutComponent> result = new ArrayList<LayoutComponent>();
    for (UIComponent uiComponent : (List<UIComponent>) getChildren()) {
      if (uiComponent instanceof LayoutComponent) {
        result.add((LayoutComponent) uiComponent);
      }
    }
    return result;
  }

  public LayoutManager getLayoutManager() {
    LayoutManager layoutManager = (LayoutManager) getFacet(Facets.LAYOUT);
    // todo: What is using as default, if nothing is defined?
    if (layoutManager == null) {
      FacesContext facesContext = FacesContext.getCurrentInstance();
      layoutManager = (LayoutManager) CreateComponentUtils.createComponent(
          facesContext, "org.apache.myfaces.tobago.GridLayout", RendererTypes.GRID_LAYOUT);
      ((OnComponentCreated) layoutManager).onComponentCreated(facesContext, (UILayout) layoutManager);
      getFacets().put(Facets.LAYOUT, (UILayout) layoutManager);
    }
    return layoutManager;
  }

}
