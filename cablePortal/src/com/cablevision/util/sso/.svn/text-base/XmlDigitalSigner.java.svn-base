/*
 * Copyright (C) 2006 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.cablevision.util.sso;

import jwm.jdom.Document;
import jwm.jdom.Element;
import jwm.jdom.output.XMLOutputter;

import com.cablevision.exception.SamlException;

import java.security.AccessControlException;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.interfaces.DSAPublicKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Collections;
import java.util.List;

import javax.xml.crypto.MarshalException;
import javax.xml.crypto.dsig.CanonicalizationMethod;
import javax.xml.crypto.dsig.DigestMethod;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.crypto.dsig.SignedInfo;
import javax.xml.crypto.dsig.Transform;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureException;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.KeyValue;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;

/**
 * This helper class, part of the SAML-based Single Sign-On Reference Tool,
 * serves to digitally sign XML files, given the contents of the XML file, and a
 * pair of public and private keys. The file is signed as per the specifications
 * defined by SAML 2.0.
 * 
 */
public class XmlDigitalSigner {

  private static final String JSR_105_PROVIDER = 
    "org.jcp.xml.dsig.internal.dom.XMLDSigRI";
  private static final String SAML_PROTOCOL_NS_URI_V20 = 
    "urn:oasis:names:tc:SAML:2.0:protocol";

  /*
   * Determines location to insert the XML <Signature> element into the SAML
   * response.
   */
  private static org.w3c.dom.Node getXmlSignatureInsertLocation(
      org.w3c.dom.Element elem) {
    org.w3c.dom.Node insertLocation = null;
    org.w3c.dom.NodeList nodeList = elem.getElementsByTagNameNS(
      SAML_PROTOCOL_NS_URI_V20, "Extensions");
    if (nodeList.getLength() != 0) {
      insertLocation = nodeList.item(nodeList.getLength() - 1);
    } else {
      nodeList = elem
        .getElementsByTagNameNS(SAML_PROTOCOL_NS_URI_V20, "Status");
      insertLocation = nodeList.item(nodeList.getLength() - 1);
    }
    return insertLocation;
  }

  /*
   * Signs the provided element with the specified private key and embeds the
   * public key.
   */
  private static Element signSamlElement(Element element, PrivateKey privKey,
      PublicKey pubKey) throws SamlException {

    try {

      // Create a DOM XMLSignatureFactory that will be used to generate the
      // enveloped signature.
      String providerName = System.getProperty("jsr105Provider",
        JSR_105_PROVIDER);
      XMLSignatureFactory sigFactory = XMLSignatureFactory.getInstance("DOM",
        (Provider) Class.forName(providerName).newInstance());

      // Create a Reference to the enveloped document (we are
      // signing the whole document, so a URI of "" signifies that) and
      // also specify the SHA1 digest algorithm and the ENVELOPED Transform.
      List envelopedTransform = Collections.singletonList(sigFactory
        .newTransform(Transform.ENVELOPED, (TransformParameterSpec) null));
      
      Reference ref = sigFactory.newReference("", sigFactory.newDigestMethod(
        DigestMethod.SHA1, null), envelopedTransform, null, null);

      // Create the SignatureMethod based on the type of key
      SignatureMethod signatureMethod;
      if (pubKey instanceof DSAPublicKey) {
        signatureMethod = sigFactory.newSignatureMethod(
          SignatureMethod.DSA_SHA1, null);
      } else if (pubKey instanceof RSAPublicKey) {
        signatureMethod = sigFactory.newSignatureMethod(
          SignatureMethod.RSA_SHA1, null);
      } else {
        throw new SamlException(
          "Error signing SAML element: Unsupported type of key");
      }

      CanonicalizationMethod canonicalizationMethod = sigFactory
        .newCanonicalizationMethod(
          CanonicalizationMethod.INCLUSIVE_WITH_COMMENTS,
          (C14NMethodParameterSpec) null);

      // Create the SignedInfo
      SignedInfo signedInfo = sigFactory.newSignedInfo(canonicalizationMethod,
        signatureMethod, Collections.singletonList(ref));

      // Create a KeyValue containing the DSA or RSA PublicKey
      KeyInfoFactory keyInfoFactory = sigFactory.getKeyInfoFactory();
      KeyValue keyValuePair = keyInfoFactory.newKeyValue(pubKey);

      // Create a KeyInfo and add the KeyValue to it
      KeyInfo keyInfo = keyInfoFactory.newKeyInfo(Collections
        .singletonList(keyValuePair));

      // Convert the JDOM document to w3c (Java XML signature API requires w3c
      // representation)
      org.w3c.dom.Element w3cElement = UtilSSO.toDom(element);

      // Create a DOMSignContext and specify the DSA/RSA PrivateKey and
      // location of the resulting XMLSignature's parent element
      DOMSignContext dsc = new DOMSignContext(privKey, w3cElement);

      // compute the correct location to insert the signature xml (location
      // is important because the SAML xsd's enforce sequence on signed info.
      // see "StatusResponseType" definition in saml-schema-protocol-2.0.xsd
      // for instance.)
      org.w3c.dom.Node xmlSigInsertionPoint = 
        getXmlSignatureInsertLocation(w3cElement);
      dsc.setNextSibling(xmlSigInsertionPoint);

      // Marshal, generate (and sign) the enveloped signature
      XMLSignature signature = sigFactory.newXMLSignature(signedInfo, keyInfo);
      signature.sign(dsc);

      return UtilSSO.toJdom(w3cElement);

    } catch (ClassNotFoundException e) {
      throw new SamlException("Error signing SAML element: " + e.getMessage());
    } catch (InvalidAlgorithmParameterException e) {
      throw new SamlException("Error signing SAML element: " + e.getMessage());
    } catch (NoSuchAlgorithmException e) {
      throw new SamlException("Error signing SAML element: " + e.getMessage());
    } catch (AccessControlException e) {
      throw new SamlException("Error signing SAML element: " + e.getMessage());
    } catch (XMLSignatureException e) {
      throw new SamlException("Error signing SAML element: " + e.getMessage());
    } catch (KeyException e) {
      throw new SamlException("Error signing SAML element: " + e.getMessage());
    } catch (MarshalException e) {
      throw new SamlException("Error signing SAML element: " + e.getMessage());
    } catch (InstantiationException e) {
      throw new SamlException("Error signing SAML element: " + e.getMessage());
    } catch (IllegalAccessException e) {
      throw new SamlException("Error signing SAML element: " + e.getMessage());
    }
  }

  /**
   * Signs the specified xmlString with the pair of provided keys, as per the
   * SAML 2.0 specifications. Returns String format of signed XML if
   * successfully signed, returns null otherwise.
   * 
   * @param samlResponse SAML Response XML file to be signed
   * @param publicKey public key to read the signed XML
   * @param privateKey private key to sign the XML
   * @return String format of signed XML if signed correctly, null otherwise
   */
  public static String signXML(String samlResponse, PublicKey publicKey,
      PrivateKey privateKey) throws SamlException {
    Document doc = UtilSSO.createJdomDoc(samlResponse);
    if (doc != null) {
      // create a new root element by signing it with the supplied keys
      Element signedElement = signSamlElement(doc.getRootElement(), privateKey,
        publicKey);
      doc.setRootElement((Element) signedElement.detach());
      XMLOutputter xmlOutputter = new XMLOutputter();
      return (xmlOutputter.outputString(doc));
    } else {
      throw new SamlException("Error signing SAML Response: Null document");
    }
  }

}
