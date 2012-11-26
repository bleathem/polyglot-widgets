package org.jboss.demo.widgets.server;

import org.jboss.demo.widgets.client.shared.*;
import org.jboss.errai.bus.server.annotations.Service;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
@Service
/**
 * The CDI bean that holds the list of selected Capitals in the user session.
 *
 * @author <a href="http://community.jboss.org/people/bleathem">Brian Leathem</a>
 */
public class CapitalsBean  implements CapitalsListService, Serializable {
    private static final long serialVersionUID = -1509108399715814302L;
    @Inject
    private List<Capital> capitals;
    private List<Capital> selectedCapitals;
    @Inject @Server
    private Event<CapitalsSelected> serverEvent;

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
        serverEvent.fire(new CapitalsSelected(selectedCapitals));
    }


}