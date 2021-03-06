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
import org.hudsonci.xpath.XFunctionFilter;
import org.hudsonci.xpath.XNamespaceContext;
import org.hudsonci.xpath.XPathAPI;
import org.hudsonci.xpath.XPathException;
import org.hudsonci.xpath.XVariableContext;
import org.jaxen.Function;
import org.jaxen.FunctionContext;
import org.jaxen.JaxenException;
import org.jaxen.NamespaceContext;
import org.jaxen.UnresolvableException;
import org.jaxen.VariableContext;
import org.jaxen.XPathFunctionContext;
import org.jaxen.dom4j.Dom4jXPath;

/**
 *
 * @author Bob Foster
 */
public class XPathJaxenImpl implements XPathAPI {

  private XVariableContext varContext;
  private XNamespaceContext nsContext;
  private XFunctionFilter funFilter;
  private String expr;
  org.jaxen.XPath xpath;
  
  public XPathJaxenImpl(String expr) throws XPathException {
    this.expr = expr;
  }

  private class FunContext implements FunctionContext {

    FunctionContext baseContext = XPathFunctionContext.getInstance();

    public Function getFunction(String namespaceURI, String prefix, String localName) throws UnresolvableException {
        if (!funFilter.accept(namespaceURI, prefix, localName)) {
            throw new UnresolvableException("Illegal function: "+localName);
        }
        return baseContext.getFunction(namespaceURI, prefix, localName);
    }
      
  }
  
  private class VarContext implements VariableContext {

    public Object getVariableValue(String namespaceURI, String prefix, String localName) throws UnresolvableException {
      try {
        Object obj = varContext.getVariableValue(namespaceURI, prefix, localName);
        if (obj instanceof Node)
          obj = Trim.trimNode((Node) obj);
        if (obj == null)
          throw new UnresolvableException(localName+" not found");
        return obj;
      } catch (UnresolvableException e) {
        throw e;
      } catch (Exception ex) {
        throw new UnresolvableException(ex.toString());
      }
    }
  }
  
  private class NSContext implements NamespaceContext {

    public String translateNamespacePrefixToUri(String prefix) {
      return nsContext.getNamespaceURI(prefix);
    }
    
  }
  
  private org.jaxen.XPath getXPath() throws XPathException {
    if (xpath == null) {
      try {
        xpath = new Dom4jXPath(expr);
        if (varContext != null)
          xpath.setVariableContext(new VarContext());
        if (nsContext != null)
          xpath.setNamespaceContext(new NSContext());
        if (funFilter != null)
          xpath.setFunctionContext(new FunContext());
      } catch (JaxenException ex) {
        throw new XPathException(ex);
      }
    }
    return xpath;
  }
  
  public void setVariableContext(XVariableContext varContext) {
    this.varContext = varContext;
  }
  
  public XVariableContext getVariableContext() {
    return varContext;
  }
  
  public void setNamespaceContext(XNamespaceContext nsContext) {
    this.nsContext = nsContext;
  }
  
  public XNamespaceContext getNamespaceContext() {
    return nsContext;
  }
  
  public void setFunctionFilter(XFunctionFilter filter) {
    funFilter = filter;
  }

  public XFunctionFilter getFunctionFilter() {
    return funFilter;
  }
  
  private Node getNode(Object xpathContext) throws XPathException {
    return null;
  }
  
  public Object evaluate(Object xpathContext) throws XPathException {
    try {
      return getXPath().evaluate(xpathContext);
    } catch (JaxenException ex) {
      throw new XPathException(ex);
    }
  }
  
  public boolean booleanValueOf(Object xpathContext) throws XPathException {
    try {
      return getXPath().booleanValueOf(xpathContext);
    } catch (JaxenException ex) {
      throw new XPathException(ex);
    }
  }

  public double numberValueOf(Object xpathContext) throws XPathException {
    try {
      Number number = getXPath().numberValueOf(xpathContext);
      return number.doubleValue();
    } catch (JaxenException ex) {
      throw new XPathException(ex);
    }
  }

  public String stringValueOf(Object xpathContext) throws XPathException {
    try {
      return getXPath().stringValueOf(xpathContext);
    } catch (JaxenException ex) {
      throw new XPathException(ex);
    }
  }

  public Node selectSingleNode(Object xpathContext) throws XPathException {
    try {
      return (Node) getXPath().selectSingleNode(xpathContext);
    } catch (JaxenException ex) {
      throw new XPathException(ex);
    }
  }
  
  public List selectNodes(Object xpathContext) throws XPathException {
    try {
      return getXPath().selectNodes(xpathContext);
    } catch (JaxenException ex) {
      throw new XPathException(ex);
    }
  }
  
  public String toString() {
    return expr;
  }

  public void setExpr(String expr) {
    this.expr = expr;
  }
}
