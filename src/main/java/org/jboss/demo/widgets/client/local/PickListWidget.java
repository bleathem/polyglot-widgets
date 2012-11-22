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
import org.jboss.demo.widgets.client.shared.Capital;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="http://community.jboss.org/people/bleathem">Brian Leathem</a>
 */
public class PickListWidget extends Widget {
    private List<Capital> capitals;
    private final Element sourceList;
    private final Element targetList;

    public PickListWidget() {
        super();
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
    }

    public PickListWidget initCapitals(List<Capital> capitals, List<Capital> selectedCapitals) {
        this.capitals = capitals;
        parseCapitals(selectedCapitals);
        initPlugin();
        return this;
    }

    public PickListWidget parseCapitals(List<Capital> selectedCapitals) {
        clearChildren(sourceList);
        clearChildren(targetList);

        Document document = Document.get();
        for (Capital capital : capitals) {
            LIElement li = document.createLIElement();
            li.setInnerText(capital.getName());
            li.setAttribute("data-key", capital.getName());
            setCapital(li, capital);
            if (selectedCapitals.contains(capital)) {
                targetList.appendChild(li);
            } else {
                sourceList.appendChild(li);
            }
        }
        return this;
    }

    public PickListWidget updateSelectedCapitals(List<Capital> selectedCapitals) {
        return this; //parseCapitals(selectedCapitals);
    }

    public List<Capital> getSelectedCapitals() {
        List<Capital> selectedCapitals = new ArrayList<Capital>();
        for (int i = 0; i < targetList.getChildCount(); i++ ) {
            Node node = targetList.getChild(i);
            if (node instanceof LIElement) {
                LIElement li = (LIElement) node;
                Capital capital = getCapital(li);
                if (capital != null) {
                    selectedCapitals.add(capital);
                }
            }
        }
        return selectedCapitals;
    }

    private Element clearChildren(Element element) {
        if (element.hasChildNodes()) {
            while ( element.hasChildNodes()) {
                element.removeChild(element.getLastChild());
            }
        }
        return element;
    }

    @Override
    protected void onLoad() {
//        initPlugin();
        super.onLoad();
    }

    private void initPlugin() {
        String id = this.getElement().getId();
        initPickList(id);
    }

    private static native void initPickList(String id) /*-{
        $wnd.jQuery("#" + id).pickList();
    }-*/;

    private native void setCapital(LIElement li, Capital capital) /*-{
        $wnd.jQuery(li).data('domainobject', capital);
    }-*/;

    private native Capital getCapital(LIElement li) /*-{
        return $wnd.jQuery(li).data('domainobject');
    }-*/;


}
