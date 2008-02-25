/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transport.http;

import org.mule.api.MuleContext;
import org.mule.api.endpoint.ImmutableEndpoint;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.api.service.Service;
import org.mule.api.transport.Connector;
import org.mule.tck.testmodels.fruit.Orange;
import org.mule.transport.AbstractConnectorTestCase;
import org.mule.transport.tcp.TcpConnector;

import java.io.IOException;


public class HttpsConnectorTestCase extends AbstractConnectorTestCase
{

    // @Override
    public Connector createConnector() throws Exception
    {
        return createConnector(muleContext, false);
    }

    public static HttpsConnector createConnector(MuleContext context, boolean initialised)
        throws IOException, InitialisationException
    {
        HttpsConnector cnn = new HttpsConnector();
        cnn.setMuleContext(context);
        cnn.setName("HttpsConnector");
        cnn.setKeyStore("serverKeystore");
        cnn.setClientKeyStore("clientKeystore");
        cnn.setClientKeyStorePassword("mulepassword");
        cnn.setKeyPassword("mulepassword");
        cnn.setKeyStorePassword("mulepassword");
        cnn.setTrustStore("trustStore");
        cnn.setTrustStorePassword("mulepassword");
        cnn.getDispatcherThreadingProfile().setDoThreading(false);

        if (initialised)
        {
            cnn.initialise();
        }
        return cnn;
    }

    public String getTestEndpointURI()
    {
        return "https://localhost:60127";
    }

    public Object getValidMessage() throws Exception
    {
        return "Hello".getBytes();
    }

    public void testValidListener() throws Exception
    {
        Service service = getTestService("orange", Orange.class);
        ImmutableEndpoint endpoint = muleContext.getRegistry().lookupEndpointFactory().getInboundEndpoint(
            getTestEndpointURI());

        getConnector().registerListener(service, endpoint);
    }

    public void testProperties() throws Exception
    {
        HttpsConnector c = (HttpsConnector)getConnector();

        c.setSendBufferSize(1024);
        assertEquals(1024, c.getSendBufferSize());
        c.setSendBufferSize(0);
        assertEquals(TcpConnector.DEFAULT_BUFFER_SIZE, c.getSendBufferSize());

        // all kinds of timeouts are tested in TcpConnectorTestCase now
    }
}
