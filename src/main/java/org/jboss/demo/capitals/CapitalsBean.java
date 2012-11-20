package org.jboss.demo.capitals;

import org.jboss.demo.app.client.shared.Capital;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class CapitalsBean implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -1509108399715814302L;
    @Inject
    private List<Capital> capitals;

    public CapitalsBean() {
        // TODO Auto-generated constructor stub
    }

    public List<Capital> getCapitals() {
        return capitals;
    }

    public void setCapitals(List<Capital> capitals) {
        this.capitals = capitals;
    }
}
