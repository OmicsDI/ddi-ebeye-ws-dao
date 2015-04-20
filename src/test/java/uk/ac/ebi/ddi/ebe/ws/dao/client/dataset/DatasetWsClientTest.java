package uk.ac.ebi.ddi.ebe.ws.dao.client.dataset;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.ddi.ebe.ws.dao.client.domain.DomainWsClient;
import uk.ac.ebi.ddi.ebe.ws.dao.config.AbstractEbeyeWsConfig;
import uk.ac.ebi.ddi.ebe.ws.dao.model.domain.DomainList;
import uk.ac.ebi.ddi.ebe.ws.dao.model.result.QueryResult;

import static org.junit.Assert.*;

@ContextConfiguration(locations = {"/test-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)

public class DatasetWsClientTest {

    @Autowired
    AbstractEbeyeWsConfig ebeyeWsConfig;

    DatasetWsClient datasetWsClient;


    @Before
    public void setUp() throws Exception {
        datasetWsClient = new DatasetWsClient(ebeyeWsConfig);
    }

    @Test
    public void testGetDatasets() throws Exception {
        String[] fields = {"name,description"};
        QueryResult pride = datasetWsClient.getDatasets("omics", "human", fields,0, 20,10);
        assertNotNull(pride.getCount() > 1);
    }
}