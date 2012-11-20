package org.jboss.demo.capitals;

import org.jboss.demo.app.client.shared.Capital;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class ListSelectBean {
    @Inject
    private List<Capital> capitals;
    private List<Capital> selectedCapitals;

    public List<Capital> getCapitals() {
        return capitals;
    }

    public void setCapitals(List<Capital> capitals) {
        this.capitals = capitals;
    }

    public List<Capital> getSelectedCapitals() {
        return selectedCapitals;
    }

    public void setSelectedCapitals(List<Capital> selectedCapitals) {
        this.selectedCapitals = selectedCapitals;
    }

    public String getCapitalsString() {
        StringBuilder sb = new StringBuilder();
        for (Capital capital : capitals) {
            sb.append(capital.getName()).append(" ");
        }
        return sb.toString().trim();
    }
}
