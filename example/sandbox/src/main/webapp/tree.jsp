<%--
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
--%>

<%@ page import="javax.swing.tree.DefaultMutableTreeNode" %>
<%@ page import="org.apache.myfaces.tobago.model.TreeState" %>

<%
  DefaultMutableTreeNode tree;
  TreeState treeState;

  tree = new DefaultMutableTreeNode("Category");
  tree.insert(new DefaultMutableTreeNode("Sports"), 0);
  tree.insert(new DefaultMutableTreeNode("Movies"), 0);
  DefaultMutableTreeNode music = new DefaultMutableTreeNode("Music");
  tree.insert(music, 0);
  tree.insert(new DefaultMutableTreeNode("Games"), 0);
  DefaultMutableTreeNode temp = new DefaultMutableTreeNode("Science");
  temp.insert(
      new DefaultMutableTreeNode("Geography"), 0);
  temp.insert(
      new DefaultMutableTreeNode("Mathematics"), 0);
  DefaultMutableTreeNode temp2 = new DefaultMutableTreeNode("Astronomy");
  temp2.insert(new DefaultMutableTreeNode("Education"), 0);
  temp2.insert(new DefaultMutableTreeNode("Pictures"), 0);
  temp.insert(temp2, 2);
  tree.insert(temp, 2);
  treeState = new TreeState();
  treeState.addExpandState(tree);
  treeState.addExpandState(temp);
  treeState.addSelection(temp2);
  treeState.setMarker(music);
  session.setAttribute("tree", tree);
  session.setAttribute("treeState", treeState);
%>

<%@ taglib uri="http://myfaces.apache.org/tobago/sandbox" prefix="tcs" %>
<%@ taglib uri="http://myfaces.apache.org/tobago/component" prefix="tc" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>

<f:view>
  <tc:loadBundle basename="demo" var="bundle"/>

  <tc:page label="Sandbox - Tree" id="page"
           width="500px" height="800px">
    <f:facet name="layout">
      <tc:gridLayout margin="10px" rows="300px;300px;*"/>
    </f:facet>

    <tcs:tree state="#{treeState}" id="menu"
              showIcons="false"
              showJunctions="false"
              showRootJunction="false"
              showRoot="true"
              mode="menu">
      <tcs:treeNode label="Root">
        <tcs:treeNodes value="#{tree}" var="node">
          <tcs:treeNode label="#{node.userObject}"/>
        </tcs:treeNodes>
        <tcs:treeNode label="Sub 1"/>
        <tcs:treeNode label="Sub 2"/>
        <tcs:treeNode label="Sub 3">
          <tcs:treeNode label="Sub 3.1"/>
          <tcs:treeNode label="Sub 3.2"/>
        </tcs:treeNode>
        <tcs:treeNode label="Sub 4"/>
      </tcs:treeNode>
    </tcs:tree>

    <tcs:tree state="#{treeState}" id="tree"
              showIcons="true"
              showJunctions="true"
              showRootJunction="true"
              showRoot="true"
              selectable="single">
      <tcs:treeNode label="Root">
        <tcs:treeNodes value="#{tree}" var="node">
          <tcs:treeNode label="#{node.userObject}"/>
        </tcs:treeNodes>
        <tcs:treeNode label="Sub 1"/>
        <tcs:treeNode label="Sub 2"/>
        <tcs:treeNode label="Sub 3">
          <tcs:treeNode label="Sub 3.1"/>
          <tcs:treeNode label="Sub 3.2"/>
        </tcs:treeNode>
        <tcs:treeNode label="Sub 4"/>
      </tcs:treeNode>
    </tcs:tree>

    <tc:cell/>

  </tc:page>
</f:view>
