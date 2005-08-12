/*
 * Copyright 2002-2005 atanion GmbH.
 * 
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 * 
 *        http://www.apache.org/licenses/LICENSE-2.0
 * 
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
/*
 * Copyright (c) 2003 Atanion GmbH, Germany
 * All rights reserved. Created 17.03.2004 11:16:26.
 * $Id:RangeParserUnitTest.java 1300 2005-08-10 16:40:23 +0200 (Mi, 10 Aug 2005) lofwyr $
 */
package org.apache.myfaces.tobago.util;

import junit.framework.TestCase;

public class RangeParserUnitTest extends TestCase {

  public void test() {

    int[] ints =  {0,5,10};
    String s = "0,5,10";
    checkEquals(ints, RangeParser.getIndices(s));
    s = "0, 5, 10";
    checkEquals(ints, RangeParser.getIndices(s));
    s = " 0 , 5 , 10 ";
    checkEquals(ints, RangeParser.getIndices(s));

    ints = new int[] {3,4,5,6,7,15,16,17};
    s = "3-7,15-17";
    checkEquals(ints, RangeParser.getIndices(s));
    s = "3-5,6,7,15,16-17";
    checkEquals(ints, RangeParser.getIndices(s));
    s = "3-5, 6, 7, 15, 16 - 17 ";
    checkEquals(ints, RangeParser.getIndices(s));

    ints = new int[] {3,4,5,6,7,15,14,13};
    s = "3-7,15-13";
    checkEquals(ints, RangeParser.getIndices(s));
    s = "3 - 7, 15 - 13";
    checkEquals(ints, RangeParser.getIndices(s));


  }

  private void checkEquals(int[] ints, int[] indices) {
    assertTrue(ints.length == indices.length);
    for (int i = 0; i < ints.length; i++) {
      assertTrue(ints[i] == indices[i]);
    }
  }
}
