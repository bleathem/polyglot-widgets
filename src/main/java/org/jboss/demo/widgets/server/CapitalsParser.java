package org.jboss.demo.widgets.server;

import org.jboss.demo.widgets.client.shared.Capital;
import org.jboss.demo.widgets.client.shared.CapitalsListService;
import org.jboss.errai.bus.server.annotations.Service;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.faces.FacesException;
import javax.inject.Named;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.net.URL;
import java.util.List;

@ApplicationScoped
public class CapitalsParser {
    private List<Capital> capitalsList;

    @XmlRootElement(name = "capitals")
    private static final class CapitalsHolder {
        private List<Capital> capitals;

        @XmlElement(name = "capital")
        public List<Capital> getCapitals() {
            return capitals;
        }

        @SuppressWarnings("unused")
        public void setCapitals(List<Capital> capitals) {
            this.capitals = capitals;
        }
    }

    @PostConstruct
    public void init() {
        ClassLoader ccl = Thread.currentThread().getContextClassLoader();
        URL resource = ccl.getResource("org/richfaces/demo/data/capitals/capitals.xml");
        JAXBContext context;
        try {
            context = JAXBContext.newInstance(CapitalsHolder.class);
            CapitalsHolder capitalsHolder = (CapitalsHolder) context.createUnmarshaller().unmarshal(resource);
            capitalsList = capitalsHolder.getCapitals();
        } catch (JAXBException e) {
            throw new FacesException(e.getMessage(), e);
        }
    }

    @Produces
    @Named
    public List<Capital> getCapitals() {
        return capitalsList;
    }
}
