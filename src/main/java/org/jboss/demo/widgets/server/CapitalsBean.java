package org.jboss.demo.widgets.server;

import org.jboss.demo.widgets.client.shared.Capital;
import org.jboss.demo.widgets.client.shared.CapitalSelections;
import org.jboss.demo.widgets.client.shared.CapitalsListService;
import org.jboss.errai.bus.server.annotations.Service;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.*;

@Named
@SessionScoped
@Service
public class CapitalsBean  implements CapitalsListService, Serializable {
    private static final long serialVersionUID = -1509108399715814302L;
    private List<Capital> selectedCapitals;
    @Inject
    private List<Capital> capitals;
    private String test;

    public List<Capital> getSelectedCapitals() {
        return selectedCapitals;
    }

    @PostConstruct
    public void init() {
        selectedCapitals = new ArrayList<Capital>();
        selectedCapitals.add(capitals.get(0));
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public void setSelectedCapitals(List<Capital> selectedCapitals) {
        this.selectedCapitals = selectedCapitals;
    }

    public CapitalSelections buildSelectionLists() {
        List<Capital> unselectedCapitals = new ArrayList<Capital>();
        if (selectedCapitals == null) {
            selectedCapitals = new ArrayList<Capital>();
        }
        for (Capital capital : capitals) {
            if (! selectedCapitals.contains(capital)) {
                unselectedCapitals.add(capital);
            }

        }
        List<Capital> selected = Collections.unmodifiableList(selectedCapitals);
        List<Capital> unselected = Collections.unmodifiableList(unselectedCapitals);

        return new CapitalSelections(selected, unselected);
    }
}
