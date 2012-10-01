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

import hudson.Plugin;
import org.hudsonci.xpath.*;

/**
 *
 * @author Bob Foster
 */
public class XPathProviderPlugin extends Plugin implements XPathAPIFactory, StylesheetAPIFactory {
  
  public void start() {
    XPath.provideXPathService(this);
    Stylesheet.provideStylesheetService(this);
  }

  public XPathAPI newXPathAPI(String expr) {
    try {
      return new XPathJaxenImpl(expr);
    } catch (XPathException ex) {
      return null;
    }
  }

  public StylesheetAPI newStylesheetAPI() {
    return new StylesheetJaxenImpl();
  }

}
