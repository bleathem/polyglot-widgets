package org.jboss.demo.widgets.client.shared;

import org.jboss.errai.common.client.api.annotations.Portable;
import org.jboss.errai.marshalling.client.api.annotations.MapsTo;

import java.io.Serializable;

/**
 * A POJO representing a state capital.
 *
 * @author <a href="http://community.jboss.org/people/bleathem">Brian Leathem</a>
 */
@Portable
public class Capital implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String FILE_EXT = ".gif";
    private String name;
    private String state;
    private String timeZone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public void setStateFlag(String stateFlag) {
        // no-op, present for JSON de-marshaling
    }

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
