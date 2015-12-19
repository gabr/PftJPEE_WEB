/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.myapp.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import com.example.myapp.client.GWTService;
import com.google.gwt.user.client.ui.RootPanel;

/**
 *
 * @author Lab
 */
public class GWTServiceImpl extends RemoteServiceServlet implements GWTService {

    public String myMethod(String s) {
        RootPanel.get().add(null);
        // Do something interesting with 's' here on the server.
        return "Server says: " + s;
    }
}
