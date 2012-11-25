/**
 * JBoss, Home of Professional Open Source
 * Copyright 2010, Red Hat, Inc. and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 **/
package org.jboss.demo.widgets.server;

import org.atmosphere.cpr.*;
import org.atmosphere.handler.AbstractReflectorAtmosphereHandler;
import org.atmosphere.websocket.WebSocketEventListenerAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author <a href="http://community.jboss.org/people/bleathem">Brian Leathem</a>
 */
public class AtmosphereHandler extends AbstractReflectorAtmosphereHandler {

    @Override
    public void onRequest(AtmosphereResource event) throws IOException {

        HttpServletRequest request = event.getRequest();
        HttpServletResponse response = event.getResponse();
        String method = request.getMethod();

        // Suspend the response.
        if ("GET".equalsIgnoreCase(method)) {

            // Log all events on the console, including WebSocket events.
            event.addEventListener(new WebSocketEventListenerAdapter());

            response.setContentType("text/html;charset=ISO-8859-1");

            Broadcaster b = lookupBroadcaster();
            event.setBroadcaster(b);

            String transport = request.getHeader(HeaderConfig.X_ATMOSPHERE_TRANSPORT);

            if (transport != null && !transport.isEmpty() &&
                    transport.equalsIgnoreCase(HeaderConfig.LONG_POLLING_TRANSPORT)) {

                request.setAttribute(ApplicationConfig.RESUME_ON_BROADCAST, Boolean.TRUE);
                event.suspend(-1, false);

            } else {
                event.suspend(-1);
            }
        }
    }

    @Override
    public void destroy() {

    }

    static Broadcaster lookupBroadcaster() {
        Broadcaster b = BroadcasterFactory.getDefault().lookup("selectedCapitals", true);
        return b;
    }

}