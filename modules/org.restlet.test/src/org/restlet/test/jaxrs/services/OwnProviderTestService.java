/*
 * Copyright 2005-2008 Noelios Consulting.
 * 
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License (the "License"). You may not use this file except in
 * compliance with the License.
 * 
 * You can obtain a copy of the license at
 * http://www.opensource.org/licenses/cddl1.txt See the License for the specific
 * language governing permissions and limitations under the License.
 * 
 * When distributing Covered Code, include this CDDL HEADER in each file and
 * include the License file at http://www.opensource.org/licenses/cddl1.txt If
 * applicable, add the following below this CDDL HEADER, with the fields
 * enclosed by brackets "[]" replaced with your own identifying information:
 * Portions Copyright [yyyy] [name of copyright owner]
 */
package org.restlet.test.jaxrs.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.ProduceMime;
import javax.ws.rs.core.Response;

import org.restlet.test.jaxrs.services.tests.OwnProviderTest;

/**
 * @author Stephan Koops
 * @see CrazyTypeProvider
 * @see OwnProviderTest
 */
@Path("ownProviderTest")
public class OwnProviderTestService {

    @GET
    @ProduceMime("application/crazyType")
    public Response get() {
        Person person = new Person("abc", "def");
        return Response.ok(person).header("h1", "h1v").build();
    }
}
