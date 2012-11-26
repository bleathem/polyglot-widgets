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

import net.sf.json.JSONArray;
import org.jboss.demo.widgets.client.shared.Capital;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="http://community.jboss.org/people/bleathem">Brian Leathem</a>
 */
@Path("/capitals")
public class CapitalsRest {
    @Inject
    CapitalsBean capitalsBean;

    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    public String getCapitals() {
        JSONArray json = JSONArray.fromObject(capitalsBean.getCapitals());
        return json.toString();
    }

    @GET
    @Path("selected")
    @Produces(MediaType.APPLICATION_JSON)
    public String getSelectedCapitals() {
        JSONArray json = JSONArray.fromObject(capitalsBean.getSelectedCapitals());
        return json.toString();
    }

    @GET
    @Path("selected")
    @Produces(MediaType.APPLICATION_JSON)
    public String setSelectedCapitals() {
        JSONArray json = JSONArray.fromObject(capitalsBean.getSelectedCapitals());
        return json.toString();
    }

    @POST
    @Path("selected")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response setCapitals(List<Capital> capitals) {
        Response.ResponseBuilder builder = null;
        try {
            capitalsBean.setSelectedCapitals(capitals);
            builder = Response.ok();
        } catch (Exception e) {
            // Handle generic exceptions
            Map<String, String> responseObj = new HashMap<String, String>();
            responseObj.put("error", e.getMessage());
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        }
        return builder.build();
    }
}
