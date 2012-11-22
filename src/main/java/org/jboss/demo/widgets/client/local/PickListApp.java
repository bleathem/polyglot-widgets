/*
 * JBoss, Home of Professional Open Source
 * Copyright 2012, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the 
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,  
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.demo.widgets.client.local;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
import org.jboss.demo.widgets.client.shared.*;
import org.jboss.errai.bus.client.api.RemoteCallback;
import org.jboss.errai.bus.client.api.base.MessageBuilder;
import org.jboss.errai.ioc.client.api.AfterInitialization;
import org.jboss.errai.ioc.client.api.Caller;
import org.jboss.errai.ioc.client.api.EntryPoint;

import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.List;

/**
 * @author <a href="http://community.jboss.org/people/bleathem">Brian Leathem</a>
 */
@EntryPoint
public class PickListApp {
    private PickListWidget pickList;

    @Inject @Client
    private Event<CapitalsSelected> event;

    @Inject
    Caller<CapitalsListService> rpcCaller;

    public PickListApp() {
        pickList = new PickListWidget();

        Button button = new Button("Submit");
        button.removeStyleName("gwt-Button");
        button.getElement().addClassName("btn");
        button.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                submit();
            }
        });

        RootPanel.get("myPickList").add(pickList);
        RootPanel.get("myPickList").add(button);
    }

    @AfterInitialization
    public void remoteCall() {
        rpcCaller.call(new RemoteCallback<List<Capital>>() {
            public void callback(final List<Capital> capitals) {
                rpcCaller.call(new RemoteCallback<List<Capital>>() {
                    public void callback(final List<Capital> selectedCapitals) {
                        pickList.initCapitals(capitals, selectedCapitals);
                    }
                }).getSelectedCapitals();

            }
        }).getCapitals();
    }

    public void capitalsSelected(@Observes @Server CapitalsSelected event) {
        List<Capital> selectedCapitals = event.getSelectedCapitals();
        pickList.updateSelectedCapitals(selectedCapitals);
    }

    private void submit() {
        List<Capital> capitals = pickList.getSelectedCapitals();
        event.fire(new CapitalsSelected(capitals));
    }
}