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

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.LIElement;
import com.google.gwt.user.client.ui.RootPanel;
import org.jboss.demo.widgets.client.shared.Capital;
import org.jboss.demo.widgets.client.shared.CapitalsListService;
import org.jboss.errai.bus.client.api.RemoteCallback;
import org.jboss.errai.bus.client.api.base.MessageBuilder;
import org.jboss.errai.ioc.client.api.AfterInitialization;
import org.jboss.errai.ioc.client.api.EntryPoint;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="http://community.jboss.org/people/bleathem">Brian Leathem</a>
 */
@EntryPoint
public class PickListApp {

    @AfterInitialization
    public void init() {
        MessageBuilder.createCall(new RemoteCallback<List<Capital>>() {
            public void callback(List<Capital> capitals) {
                final PickListWidget pickList = new PickListWidget(buildList(capitals));
                MessageBuilder.createCall(new RemoteCallback<List<Capital>>() {
                    public void callback(List<Capital> selectedCapitals) {
                        pickList.selectItems(buildList(selectedCapitals));
                        RootPanel.get("myPickList").add(pickList);
                    }
                }, CapitalsListService.class).getSelectedCapitals();

            }
        }, CapitalsListService.class).getCapitals();
    }

    private List<LIElement> buildList(List<Capital> capitals) {
        Document document = Document.get();

        List<LIElement> list = new ArrayList<LIElement>();
        for (Capital capital : capitals) {
            LIElement li = document.createLIElement();
            li.setInnerText(capital.getName());
            li.setAttribute("data-key", capital.getName());
            list.add(li);
        }
        return list;
    }
}
