package org.jboss.demo.widgets.server;

import org.jboss.demo.widgets.client.shared.*;
import org.jboss.errai.bus.server.annotations.Service;
import org.richfaces.cdi.push.Push;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
@Service
public class CapitalsBean  implements CapitalsListService, Serializable {
    private static final long serialVersionUID = -1509108399715814302L;
    @Inject
    private List<Capital> capitals;
    private List<Capital> selectedCapitals;

    @Inject @GWT @Server
    private Event<ServerCapitalsSelected> gwtEvent;

    @Inject @Push(topic = "capitalsSelected")
    Event<String> jsfEvent;

    @PostConstruct
    public void init() {
        selectedCapitals = new ArrayList<Capital>();
        selectedCapitals.add(capitals.get(0));
    }

    public List<Capital> getCapitals() {
        return capitals;
    }

    public List<Capital> getSelectedCapitals() {
        return selectedCapitals;
    }

    public void setSelectedCapitals(List<Capital> selectedCapitals) {
        this.selectedCapitals = selectedCapitals;
        gwtEvent.fire(new ServerCapitalsSelected(selectedCapitals));
    }

    public void observeCapitalSelection(@Observes @GWT @Client CapitalsSelected capitalsSelected) {
        this.selectedCapitals = capitalsSelected.getSelectedCapitals();
        jsfEvent.fire("capitalsSelected");
    }
}