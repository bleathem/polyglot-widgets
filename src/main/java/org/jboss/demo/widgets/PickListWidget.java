/**
 * JBoss, Home of Professional Open Source
 * Copyright 2010, Red Hat, Inc. and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 **/
package org.jboss.demo.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.ScriptInjector;
import com.google.gwt.dom.client.LIElement;
import com.google.gwt.dom.client.OListElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

import java.util.List;

/**
 * @author <a href="http://community.jboss.org/people/bleathem">Brian Leathem</a>
 */
public class PickListWidget extends Composite {

    interface MyUiBinder extends UiBinder<Panel, PickListWidget> {}
    private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

    @UiField Panel root;
    @UiField OListElement sourceList;
    @UiField OListElement targetList;

    public PickListWidget(List<LIElement> sourceItems, List<LIElement> targetItems) {
        initWidget(uiBinder.createAndBindUi(this));
        for (LIElement item : sourceItems) {
            sourceList.appendChild(item);
        }
        for (LIElement item : targetItems) {
            targetList.appendChild(item);
        }
    }

    @Override
    protected void initWidget(Widget widget) {
        super.initWidget(widget);
        String id = this.getElement().getId();
        String scriptBody = "jQuery(function () {\n" +
                "                var $pickList = $(document.getElementById('" + id + "'));\n" +
                "                $pickList.pickList();\n" +
                "            });";
        ScriptInjector.fromString(scriptBody).inject();
    }
}
