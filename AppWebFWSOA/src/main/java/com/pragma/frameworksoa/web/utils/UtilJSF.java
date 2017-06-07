/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pragma.frameworksoa.web.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;
import org.primefaces.util.Base64;

/**
 *
 * @author andresfelipegarciaduran
 */
public class UtilJSF {

    private static UtilJSF utilJSF;

    public static UtilJSF getUtilJSF() {
        if (utilJSF == null) {
            utilJSF = new UtilJSF();
        }
        return utilJSF;
    }

    public SelectItem[] getSelectItems(List<?> entities, boolean selectOne) {
        int size = selectOne ? entities.size() + 1 : entities.size();
        SelectItem[] items = new SelectItem[size];
        int i = 0;
        if (selectOne) {
            items[0] = new SelectItem("", "---");
            i++;
        }
        for (Object x : entities) {
            items[i++] = new SelectItem(x, x.toString());
        }
        return items;
    }

    public void addErrorMessage(Exception ex, String defaultMsg) {
        String msg = ex.getLocalizedMessage();
        if (msg != null && msg.length() > 0) {
            addErrorMessage(msg, null);
        } else {
            addErrorMessage(defaultMsg, null);
        }
    }

    public void addErrorMessages(List<String> messages) {
        for (String message : messages) {
            addErrorMessage(message, null);
        }
    }

    public void addErrorMessage(String summary, String detail) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }

    public void addSuccessMessage(String summary, String detail) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }

    public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
    }

    public String getRequestParameter(String key) {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(key);
    }

    public Object getObjectFromRequestParameter(String requestParameterName, Converter converter, UIComponent component) {
        String value = getUtilJSF().getRequestParameter(requestParameterName);
        return converter.getAsObject(FacesContext.getCurrentInstance(), component, value);
    }

    public static String encodeMD5(byte[] input) {
        String outcome = null;
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
            outcome = new BigInteger(1, md.digest(input)).toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return outcome;
    }

    public static String decodeBase64(String atributo) {
        return new String(Base64.decode(atributo.getBytes()));
    }

}
