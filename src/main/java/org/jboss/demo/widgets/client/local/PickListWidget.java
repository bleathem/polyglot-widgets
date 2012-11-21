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
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Widget;

import java.util.List;

/**
 * @author <a href="http://community.jboss.org/people/bleathem">Brian Leathem</a>
 */
public class PickListWidget extends Widget {
    private final List<LIElement> allItems;
    private final Element sourceList;
    private final Element targetList;

    public PickListWidget(List<LIElement> allItems) {
        super();
        this.allItems = allItems;
        Element panel = DOM.createDiv();
        String uniqueId = Document.get().createUniqueId();
        panel.setId(uniqueId);
        setElement(panel);

        sourceList = DOM.createElement("ol");
        sourceList.setClassName("source");
        targetList = DOM.createElement("ol");
        targetList.setClassName("target");

        panel.appendChild(sourceList);
        panel.appendChild(targetList);

        for (LIElement item : allItems) {
            sourceList.appendChild(item);
        }
    }

    public void selectItems(List<LIElement> selectedItems) {
        for (LIElement selectedItem : selectedItems) {
            NodeList nodeList = sourceList.getChildNodes();
            for (int i = 0; i <= nodeList.getLength(); i++ ) {
                Node node = nodeList.getItem(i);
                if (node instanceof LIElement) {
                    LIElement li = (LIElement) node;
                    String nodeKey = li.getAttribute("data-key");
                    String selectedKey = selectedItem.getAttribute("data-key");
                    if (nodeKey.equals(selectedKey)) {
                        sourceList.removeChild(node);
                        targetList.appendChild(selectedItem);
                    }
                }
            }

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
