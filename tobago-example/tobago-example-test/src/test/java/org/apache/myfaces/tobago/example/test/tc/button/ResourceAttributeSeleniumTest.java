package org.apache.myfaces.tobago.example.test.tc.button;

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

import org.apache.myfaces.tobago.example.test.MultiSuffixSeleniumTest;
import org.apache.myfaces.tobago.util.Parameterized;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Parameterized.class)
public class ResourceAttributeSeleniumTest extends MultiSuffixSeleniumTest {

  public ResourceAttributeSeleniumTest(String suffix) {
    super(suffix);
  }

  @Test
  public void testHtmlResource() throws InterruptedException {
    open("/tc/button/resource-attribute.");
    getSelenium().click("page:button-html-resource");
    getSelenium().waitForPageToLoad("5000");
    Assert.assertEquals(getHtmlSource(),
        "Eine einfache Seite für den Resourcen-Test (statisch).", getSelenium().getText("//html/body"));
  }

  @Test
  public void testXhtmlResource() throws InterruptedException {
    open("/tc/button/resource-attribute.");
    getSelenium().click("page:button-xhtml-resource");
    getSelenium().waitForPageToLoad("5000");
    Assert.assertTrue(getHtmlSource(),
        getSelenium().getText("//html/body").contains("Eine einfache Seite für den Resourcen-Test (dynamisch)."));
  }
}
