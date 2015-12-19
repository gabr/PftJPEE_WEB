/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.myapp.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import com.example.myapp.client.dotProduct;

/**
 *
 * @author Lab
 */
public class dotProductImpl extends RemoteServiceServlet implements dotProduct {

    public String myMethod(String sa1,String sb1,String sa2,String sb2,String sa3,String sb3) {
        Double a1,a2,a3,b1,b2,b3;
        a1 = Double.parseDouble(sa1);
        a2 = Double.parseDouble(sa2);
        a3 = Double.parseDouble(sa3);
        b1 = Double.parseDouble(sb1);
        b2 = Double.parseDouble(sb2);
        b3 = Double.parseDouble(sb3);
        // Do something interesting with 's' here on the server.
        Double dot = a1 * b1 + a2 * b2 + a3 * b3;
        return "Server says: " + dot;
    }
}
