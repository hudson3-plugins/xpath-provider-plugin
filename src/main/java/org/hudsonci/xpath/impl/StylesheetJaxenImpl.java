/*******************************************************************************
 *
 * Copyright (c) 2012 Oracle Corporation.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: 
 *
 *    Bob Foster
 *     
 *******************************************************************************/ 

package org.hudsonci.xpath.impl;

import java.util.List;
import org.dom4j.Node;
import org.dom4j.rule.Action;
import org.dom4j.rule.Rule;
import org.hudsonci.xpath.StylesheetAPI;
import org.hudsonci.xpath.XPath;

/**
 *
 * @author Bob Foster
 */
public class StylesheetJaxenImpl implements StylesheetAPI {

  org.dom4j.rule.Stylesheet stylesheet;
  
  public StylesheetJaxenImpl() {
    stylesheet = new org.dom4j.rule.Stylesheet();
  }
  
  public void addRule(Rule rule) {
    stylesheet.addRule(rule);
  }
  
  public void applyTemplates(Object input, XPath xpath) throws Exception {
    stylesheet.applyTemplates(input, ((XPathJaxenImpl)xpath.getImpl()).xpath);
  }
  
  public void applyTemplates(Object input, XPath xpath, String mode) throws Exception {
    stylesheet.applyTemplates(input, ((XPathJaxenImpl)xpath.getImpl()).xpath, mode);
  }
  
  public void applyTemplates(Object input) throws Exception {
    stylesheet.applyTemplates(input);
  }
  
  public void applyTemplates(Object input, String mode) throws Exception {
    stylesheet.applyTemplates(input, mode);
  }
  
  public void clear() {
    stylesheet.clear();
  }
  
  public String getModeName() {
    return stylesheet.getModeName();
  }
  
  public Action getValueOfAction() {
    return stylesheet.getValueOfAction();
  }
  
  public void removeRule(Rule rule) {
    stylesheet.removeRule(rule);
  }
  
  public void run(Object input) throws Exception {
    stylesheet.run(input);
  }
  
  public void run(Object input, String mode) throws Exception {
    stylesheet.run(input, mode);
  }
  
  public void run(List list) throws Exception {
    stylesheet.run(list);
  }
  
  public void run(List list, String mode) throws Exception {
    stylesheet.run(list, mode);
  }
  
  public void run(Node node) throws Exception {
    stylesheet.run(node);
  }
  
  public void run(Node node, String mode) throws Exception {
    stylesheet.run(node, mode);
  }

  public void setModeName(String modeName) {
    stylesheet.setModeName(modeName);
  }
  
  public void setValueOfAction(Action valueOfAction) {
    stylesheet.setValueOfAction(valueOfAction);
  }

}
