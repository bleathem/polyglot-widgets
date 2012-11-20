package org.jboss.demo.app.client.shared;

import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

public class Capital implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -1042449580199397136L;
    private static final String FILE_EXT = ".gif";
    private String name;
    private String state;
    private String timeZone;

    public Capital() {
    }

    public Capital(String name, String state, String timeZone) {
        this.name = name;
        this.state = state;
        this.timeZone = timeZone;
    }

    @XmlElement
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    private String stateNameToFileName() {
        return state.replaceAll("\\s", "").toLowerCase();
    }

    public String getStateFlag() {
        return stateNameToFileName() + FILE_EXT;
    }

    @XmlElement
    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }
}
