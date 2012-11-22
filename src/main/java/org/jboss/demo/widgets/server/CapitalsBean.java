package org.jboss.demo.widgets.server;

import org.jboss.demo.widgets.client.shared.Capital;
import org.jboss.demo.widgets.client.shared.CapitalsListService;
import org.jboss.demo.widgets.client.shared.CapitalsSelected;
import org.jboss.errai.bus.server.annotations.Service;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
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
    }

    public void observeCapitalSelection(@Observes CapitalsSelected event) {
        setSelectedCapitals(event.getSelectedCapitals());
    }
}