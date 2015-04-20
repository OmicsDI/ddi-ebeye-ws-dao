package uk.ac.ebi.ddi.ebe.ws.dao.client.dataset;

import uk.ac.ebi.ddi.ebe.ws.dao.client.EbeyeClient;
import uk.ac.ebi.ddi.ebe.ws.dao.config.AbstractEbeyeWsConfig;
import uk.ac.ebi.ddi.ebe.ws.dao.model.domain.DomainList;
import uk.ac.ebi.ddi.ebe.ws.dao.model.result.QueryResult;

/**
 * @author Yasset Perez-Riverol ypriverol
 */
public class DatasetWsClient extends EbeyeClient{


    /**
     * Default constructor for Ws clients
     *
     * @param config
     */
    public DatasetWsClient(AbstractEbeyeWsConfig config) {
        super(config);
    }

    /**
     * Returns the Datasets for a domain with an specific Query
     * @param domainName Domain to retrieve the information
     * @param query Web-service query
     * @param fields fields to be query
     * @param start  number of the first entry to retrieve
     * @param size   Number of entries to be retrieve maximum 100.
     * @param facetCount Face count the number of facets by entry.
     * @return A list of entries and the facets included
     */
    public QueryResult getDatasets(String domainName, String query, String[] fields, int start, int size, int facetCount){

        String finalFields = "";
        if(fields != null && fields.length > 0){
            int count = 0;
            for(String value: fields){
                if(count == fields.length - 1)
                    finalFields = finalFields + value;
                else
                    finalFields = finalFields + value + ",";
                count++;
            }
        }

        String url = String.format("%s://%s/ebisearch/ws/rest/%s?query=%s&fields=%s&start=%s&size=%s&facetcount=%s&format=JSON",
                config.getProtocol(), config.getHostName(), domainName, query, finalFields, start, size, facetCount);


        return this.restTemplate.getForObject(url, QueryResult.class);
    }

}
