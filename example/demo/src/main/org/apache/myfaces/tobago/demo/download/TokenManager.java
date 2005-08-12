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
package org.apache.myfaces.tobago.demo.download;

import javax.faces.context.FacesContext;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class TokenManager {

  public static final long TOKEN_VALID_TIME = 1000 * 60 * 60 * 8;

  private Map<String,DownloadInfo> tokens = new HashMap<String, DownloadInfo>();

  public TokenManager() throws NoSuchAlgorithmException {
    // test if digester is available
    MessageDigest.getInstance("MD5");
  }

  public String registerToken(DownloadInfo info) {
    MessageDigest digester = null;
    try {
      digester = MessageDigest.getInstance("MD5");
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException("MD5 Digester not available.");
    }
    byte[] bytes = new byte[10000];
    new Random().nextBytes(bytes);
    digester.update(bytes);
    byte[] digest = digester.digest();
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < digest.length; i++) {
      byte b = digest[i];
      int x = Math.abs(b);
      sb.append(x);
    }
    String key = sb.toString();
    info.setEndTime(new Date(System.currentTimeMillis() + TOKEN_VALID_TIME));
    tokens.put(key, info);
    return key;
  }

  public DownloadInfo checkToken(String token) {
    // clean up
    Set<String> keys = tokens.keySet();
    Date now = new Date();
    for (Iterator<String> it = keys.iterator(); it.hasNext();) {
      String s = it.next();
      DownloadInfo info = tokens.get(s);
      if (info.getEndTime().before(now)) {
        tokens.remove(s);
      }
    }
    return tokens.get(token);
  }

  public static TokenManager getCurrentInstance(FacesContext facesContext) {
    return (TokenManager) facesContext.getApplication()
        .getVariableResolver().resolveVariable(facesContext, "tokenManager");
  }

}
