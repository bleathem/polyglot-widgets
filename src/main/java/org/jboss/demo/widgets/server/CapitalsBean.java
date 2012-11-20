package org.jboss.demo.widgets.server;

import org.jboss.demo.widgets.client.shared.Capital;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class CapitalsBean implements Serializable {
    private static final long serialVersionUID = -1509108399715814302L;
    private List<Capital> selectedCapitals;

    public List<Capital> getSelectedCapitals() {
        return selectedCapitals;
    }

    public void setSelectedCapitals(List<Capital> selectedCapitals) {
        this.selectedCapitals = selectedCapitals;
    }
}
