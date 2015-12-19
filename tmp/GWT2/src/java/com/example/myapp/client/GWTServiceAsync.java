/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.myapp.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 *
 * @author Lab
 */
public interface GWTServiceAsync {

    public void myMethod(String s, AsyncCallback<String> callback);
}
