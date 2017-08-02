package uk.ac.ebi.ddi.ebe.ws.dao.client.citation;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.ddi.ebe.ws.dao.client.europmc.CitationClient;
import uk.ac.ebi.ddi.ebe.ws.dao.config.AbstractEbeyeWsConfig;
import uk.ac.ebi.ddi.ebe.ws.dao.model.europmc.CitationResponse;

import static org.junit.Assert.assertNotNull;

/**
 * Created by gaur on 13/07/17.
 */
@ContextConfiguration(locations = {"/test-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)

public class CitationClientTest {

    @Autowired
    AbstractEbeyeWsConfig ebeyeWsConfig;

    CitationClient citationClient;


    @Before
    public void setUp() throws Exception {
        citationClient = new CitationClient(ebeyeWsConfig);
    }

    @Test
    public void testGetDomainByName() throws Exception {
        CitationResponse citationResponse = citationClient.getCitations("PXD000865");
        assertNotNull(citationResponse.count > 0);
    }
}
