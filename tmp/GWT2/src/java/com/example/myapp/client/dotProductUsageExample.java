/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.myapp.client;

import com.google.gwt.core.client.GWT;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

/**
 * Example class using the dotProduct service.
 *
 * @author Lab
 */
public class dotProductUsageExample extends VerticalPanel {

    private Label lblServerReply = new Label();
    private TextBox a1 = new TextBox();
    private TextBox b1 = new TextBox();
    private TextBox a2 = new TextBox();
    private TextBox b2 = new TextBox();
    private TextBox a3 = new TextBox();
    private TextBox b3 = new TextBox();
    private Button btnSend = new Button("Send to server");
    
    public dotProductUsageExample() {
        add(new Label("Input your text: "));
        add(a1);
        add(b1);
        add(a2);
        add(b2);
        add(a3);
        add(b3);
        add(btnSend);
        add(lblServerReply);

        // Create an asynchronous callback to handle the result.
        final AsyncCallback<String> callback = new AsyncCallback<String>() {
            public void onSuccess(String result) {
                lblServerReply.setText(result);
            }
            
            public void onFailure(Throwable caught) {
                lblServerReply.setText("Communication failed");
            }
        };

        // Listen for the button clicks
        btnSend.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                // Make remote call. Control flow will continue immediately and later
                // 'callback' will be invoked when the RPC completes.
                getService().myMethod(a1.getText(),b1.getText(),a2.getText(),b2.getText(),a3.getText(),
                        b3.getText(), callback);
            }
        });
    }
    
    public static dotProductAsync getService() {
        // Create the client proxy. Note that although you are creating the
        // service interface proper, you cast the result to the asynchronous
        // version of the interface. The cast is always safe because the
        // generated proxy implements the asynchronous interface automatically.

        return GWT.create(dotProduct.class);
    }
}
