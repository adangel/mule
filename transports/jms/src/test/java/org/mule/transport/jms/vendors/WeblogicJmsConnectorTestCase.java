/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transport.jms.vendors;

import org.mule.tck.junit4.FunctionalTestCase;
import org.mule.transport.jms.JmsConnector;
import org.mule.transport.jms.JmsTopicResolver;
import org.mule.transport.jms.weblogic.WeblogicJmsTopicResolver;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class WeblogicJmsConnectorTestCase extends FunctionalTestCase
{

    @Override
    protected String getConfigResources()
    {
        return "weblogic-config.xml";
    }

    @Test
    public void testDefaultConfig() throws Exception
    {
        JmsConnector c = (JmsConnector)muleContext.getRegistry().lookupConnector("jmsConnector");
        assertNotNull(c);

        // TODO has to be confirmed for Weblogic
        assertTrue(c.isEagerConsumer());
        JmsTopicResolver resolver = c.getTopicResolver();
        assertNotNull("Topic resolver must not be null.", resolver);
        assertTrue("Wrong topic resolver configured on the connector.",
                   resolver instanceof WeblogicJmsTopicResolver);
    }
}
