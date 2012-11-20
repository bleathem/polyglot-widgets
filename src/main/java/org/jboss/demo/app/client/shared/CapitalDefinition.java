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
package org.jboss.demo.app.client.shared;

import org.jboss.demo.app.client.shared.Capital;
import org.jboss.errai.marshalling.rebind.api.CustomMapping;
import org.jboss.errai.marshalling.rebind.api.model.MappingDefinition;
import org.jboss.errai.marshalling.rebind.api.model.impl.AccessorMapping;
import org.jboss.errai.marshalling.rebind.api.model.impl.SimpleConstructorMapping;
import org.jboss.errai.marshalling.rebind.api.model.impl.WriteMapping;

/**
 * @author <a href="http://community.jboss.org/people/bleathem">Brian Leathem</a>
 */
@CustomMapping
public class CapitalDefinition extends MappingDefinition {
    public CapitalDefinition() {
        super(Capital.class);
        SimpleConstructorMapping constructorMapping = new SimpleConstructorMapping();
        constructorMapping.mapParmToIndex("name", 0, String.class);
        constructorMapping.mapParmToIndex("state", 1, String.class);
        constructorMapping.mapParmToIndex("timeZone", 2, String.class);
        setInstantiationMapping(constructorMapping);
        addMemberMapping(new AccessorMapping("name", String.class, "setName", "getName"));
        addMemberMapping(new AccessorMapping("state", String.class, "setState", "getState"));
        addMemberMapping(new AccessorMapping("timeZone", String.class, "setTimeZone", "getTimeZone"));
    }
}
