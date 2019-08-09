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

import {Listener} from "./tobago-listener";
import {Overlay} from "./tobago-overlay";
import {DomUtils} from "./tobago-utils";
import {Command} from "./tobago-command";

/**
 * @deprecated since 5.0.0
 */
export class Tobago {
  /**
   * Backward compatible listener registration. In the case of an AJAX call (phase = Phase.AFTER_UPDATE)
   * this listener will be called with a jQuery-object, the new one will get an HTMLElement.
   * @deprecated since 5.0.0
   */
  static registerListener(listener, phase, order) {

    Listener.register(function (element: HTMLElement) {
      listener(jQuery(element));
    }, phase, order);
  }
}

export class Setup {

  static transition: boolean;
  static oldTransition: boolean;

  static init = function() {
    console.time("[tobago] init");
    document.querySelector("form").addEventListener('submit', Command.onSubmit);
    window.addEventListener('unload', Setup.onUnload);
    Listener.executeDocumentReady(document.documentElement);
    console.timeEnd("[tobago] init");
  };

  static onBeforeUnload = function () {
    if (Setup.transition) {
      new Overlay(DomUtils.page());
    }
    Setup.transition = Setup.oldTransition;
  };

  /**
   * Wrapper function to call application generated onunload function
   */
  static onUnload = function () {
    console.info('on onload');
    if (Command.isSubmit) {
      if (Setup.transition) {
        new Overlay(DomUtils.page());
      }
      Setup.transition = Setup.oldTransition;
    } else {
      Listener.executeBeforeExit();
    }
  };
}

document.addEventListener('DOMContentLoaded', Setup.init);

window.addEventListener("load", Listener.executeWindowLoad);