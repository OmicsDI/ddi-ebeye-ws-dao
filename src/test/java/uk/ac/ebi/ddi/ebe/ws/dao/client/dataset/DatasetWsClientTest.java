package uk.ac.ebi.ddi.ebe.ws.dao.client.dataset;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.ddi.ebe.ws.dao.config.AbstractEbeyeWsConfig;
import uk.ac.ebi.ddi.ebe.ws.dao.model.dataset.QueryResult;
import uk.ac.ebi.ddi.ebe.ws.dao.model.dataset.TermResult;

import static org.junit.Assert.*;

@ContextConfiguration(locations = {"/test-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)

public class DatasetWsClientTest {

    @Autowired
    AbstractEbeyeWsConfig ebeyeWsConfig;

    @Autowired
    AbstractEbeyeWsConfig ebeyeWsProd;

    DatasetWsClient datasetWsClient;


    @Before
    public void setUp() throws Exception {
        datasetWsClient = new DatasetWsClient(ebeyeWsConfig);
    }

    @Test
    public void testGetDatasets() throws Exception {
        String[] fields = {"name,description"};
        QueryResult pride = datasetWsClient.getDatasets("pride", "human", fields,0 , 20,10);
        assertNotNull(pride.getCount() > 1);
    }

    @Test
    public void testGetFrequentlyTerms() throws Exception {

        datasetWsClient = new DatasetWsClient(ebeyeWsProd);

        String[] exclusion_words = {"a", "an", "and", "are", "as", "at", "be", "but", "by", "for", "if", "in", "into", "is", "it",
                "no", "not", "of", "on", "or", "such", "that", "the", "their", "then", "there", "these", "they", "this", "to", "was", "will", "with",
                "protein", "proteomics", "proteomic", "proteome", "proteomes", "mass", "proteins", "lc", "ms", "based", "from", "using", "during", "LC-MS", "LC-MS/MS","reveals","1","2","as","non","c" };

        TermResult pride = datasetWsClient.getFrequentlyTerms("pride", "description", exclusion_words, 100);
        assertNotNull(pride != null);

    }
}