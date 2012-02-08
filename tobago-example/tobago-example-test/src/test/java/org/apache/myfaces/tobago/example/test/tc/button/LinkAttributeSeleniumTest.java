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
public class LinkAttributeSeleniumTest extends MultiSuffixSeleniumTest {

  public LinkAttributeSeleniumTest(String suffix) {
    super(suffix);
  }

  @Test
  public void testInternalLinkWithSlash() {
    open("/tc/button/link-attribute.");
    getSelenium().click("page:button-internal-link-with-slash");
    getSelenium().waitForPageToLoad("5000");
    Assert.assertEquals(
        getHtmlSource(),
        "A simple page for the resource test (static).", getSelenium().getText("//html/body"));
  }

  @Test
  public void testInternalLinkWithoutSlash() {
    open("/tc/button/link-attribute.");
    getSelenium().click("page:button-internal-link-without-slash");
    getSelenium().waitForPageToLoad("5000");
    Assert.assertEquals(
        getHtmlSource(),
        "A simple page for the resource test (static).", getSelenium().getText("//html/body"));
  }

  @Test
  public void testExternalLink() throws InterruptedException {
    open("/tc/button/link-attribute.");
    getSelenium().click("page:button-external-link");
    getSelenium().waitForPageToLoad("5000");
    // go to the apache home page
    Assert.assertTrue(
        getHtmlSource(),
        getSelenium().getText("//html/head/title").contains("Apache Software Foundation"));
  }
}
