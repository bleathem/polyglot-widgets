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
package org.jboss.demo.widgets.client.local;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.LIElement;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Widget;

import java.util.List;

/**
 * @author <a href="http://community.jboss.org/people/bleathem">Brian Leathem</a>
 */
public class PickListWidget extends Widget {


    public PickListWidget(List<LIElement> sourceItems, List<LIElement> targetItems) {
        super();
        Element panel = DOM.createDiv();
        String uniqueId = Document.get().createUniqueId();
        panel.setId(uniqueId);
        setElement(panel);

        Element sourceList = DOM.createElement("ol");
        sourceList.setClassName("source");
        Element targetList = DOM.createElement("ol");
        targetList.setClassName("target");

        panel.appendChild(sourceList);
        panel.appendChild(targetList);

        for (LIElement item : sourceItems) {
            sourceList.appendChild(item);
        }
        for (LIElement item : targetItems) {
            targetList.appendChild(item);
        }
    }

    @Override
    protected void onLoad() {
        String id = this.getElement().getId();
        initPickList(id);
        super.onLoad();
    }

    private static native void initPickList(String id) /*-{
        $wnd.jQuery("#" + id).pickList();
    }-*/;

}
