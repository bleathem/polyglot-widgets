package org.jboss.demo.widgets.server;

import net.sf.json.JSONArray;
import org.atmosphere.cpr.Broadcaster;
import org.atmosphere.cpr.BroadcasterFactory;
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
import java.util.concurrent.Callable;

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
    private Event<CapitalsSelected> gwtEvent;

    @Inject @Push(topic = "capitalsSelected")
    private Event<String> jsfEvent;

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
        broadcastRest(selectedCapitals);
        gwtEvent.fire(new CapitalsSelected(selectedCapitals));
    }

    public void observeCapitalSelection(@Observes @Client CapitalsSelected capitalsSelected) {
        this.selectedCapitals = capitalsSelected.getSelectedCapitals();
        jsfEvent.fire("capitalsSelected");
    }

    public void broadcastRest(final List<Capital> selectedCapitals) {
        AtmosphereHandler.lookupBroadcaster().broadcast(JSONArray.fromObject(selectedCapitals).toString());
    }
}