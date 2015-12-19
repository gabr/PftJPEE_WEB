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
public interface dotProductAsync {

    public void myMethod(String sa1,String sb1,String sa2,String sb2,String sa3,String sb3, AsyncCallback<String> callback);
}
