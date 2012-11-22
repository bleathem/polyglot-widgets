package org.jboss.demo.widgets.client.shared;

import org.jboss.errai.common.client.api.annotations.Portable;
import org.jboss.errai.marshalling.client.api.annotations.MapsTo;

import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

@Portable
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

    public Capital(
            @MapsTo("name") String name,
            @MapsTo("state") String state,
            @MapsTo("timeZone") String timeZone) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Capital capital = (Capital) o;

        if (name != null ? !name.equals(capital.name) : capital.name != null) return false;
        if (state != null ? !state.equals(capital.state) : capital.state != null) return false;
        if (timeZone != null ? !timeZone.equals(capital.timeZone) : capital.timeZone != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (timeZone != null ? timeZone.hashCode() : 0);
        return result;
    }
}
