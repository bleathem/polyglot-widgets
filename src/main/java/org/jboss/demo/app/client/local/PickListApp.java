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
package org.jboss.demo.app.client.local;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.LIElement;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.RootPanel;
import org.jboss.demo.app.client.shared.CapitalsListService;
import org.jboss.demo.app.client.local.widgets.PickListWidget;
import org.jboss.errai.bus.client.api.RemoteCallback;
import org.jboss.errai.bus.client.api.base.MessageBuilder;
import org.jboss.errai.common.client.util.LogUtil;
import org.jboss.errai.ioc.client.api.AfterInitialization;
import org.jboss.errai.ioc.client.api.EntryPoint;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="http://community.jboss.org/people/bleathem">Brian Leathem</a>
 */
@EntryPoint
public class PickListApp {

    @AfterInitialization
    public void init() {
        LogUtil.log("****************** this is a test *************");

        MessageBuilder.createCall(new RemoteCallback<List<String>>() {

            public void callback(List<String> capitalsList) {
                List<LIElement> sourceList = new ArrayList<LIElement>(capitalsList.size());
                for (String capital : capitalsList) {
                    LIElement li = Document.get().createLIElement();
                    li.setInnerText(capital);
                    li.setAttribute("data-key", capital);
                    sourceList.add(li);
                }
                List<LIElement> targetList = new ArrayList<LIElement>();

                RootPanel.get("myPickList").add(new PickListWidget(sourceList, targetList));
            }

        }, CapitalsListService.class).getCapitalNames();
        RootPanel.get().add(new InlineHTML("Some text"));
        System.out.println("UI Constructed!");
    }

}
