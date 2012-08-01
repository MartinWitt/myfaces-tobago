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

package org.apache.myfaces.tobago.event;

import javax.faces.component.UIComponent;
import javax.faces.event.FacesEvent;
import javax.faces.event.FacesListener;
import javax.faces.event.PhaseId;

public class FacesEventWrapper extends FacesEvent {

  private static final long serialVersionUID = 1L;

  private FacesEvent wrappedFacesEvent;

  public FacesEventWrapper(FacesEvent facesEvent, UIComponent redirectComponent) {
    super(redirectComponent);
    wrappedFacesEvent = facesEvent;
  }

  @Override
  public PhaseId getPhaseId() {
    return wrappedFacesEvent.getPhaseId();
  }

  @Override
  public void setPhaseId(PhaseId phaseId) {
    wrappedFacesEvent.setPhaseId(phaseId);
  }

  @Override
  public void queue() {
    wrappedFacesEvent.queue();
  }

  @Override
  public String toString() {
    return wrappedFacesEvent.toString();
  }

  @Override
  public boolean isAppropriateListener(
      FacesListener faceslistener) {
    return wrappedFacesEvent.isAppropriateListener(faceslistener);
  }

  @Override
  public void processListener(FacesListener faceslistener) {
    wrappedFacesEvent.processListener(faceslistener);
  }

  public FacesEvent getWrappedFacesEvent() {
    return wrappedFacesEvent;
  }
}
