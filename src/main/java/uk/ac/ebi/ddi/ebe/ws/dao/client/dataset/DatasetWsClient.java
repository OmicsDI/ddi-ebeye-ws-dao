package uk.ac.ebi.ddi.ebe.ws.dao.client.dataset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.ddi.ebe.ws.dao.client.EbeyeClient;
import uk.ac.ebi.ddi.ebe.ws.dao.config.AbstractEbeyeWsConfig;
import uk.ac.ebi.ddi.ebe.ws.dao.model.dataset.QueryResult;
import uk.ac.ebi.ddi.ebe.ws.dao.model.dataset.SimilarResult;
import uk.ac.ebi.ddi.ebe.ws.dao.model.dataset.TermResult;

import java.util.Set;


/**
 * @author Yasset Perez-Riverol ypriverol
 */
public class DatasetWsClient extends EbeyeClient{

    private static final Logger logger = LoggerFactory.getLogger(DatasetWsClient.class);

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
    public QueryResult getDatasets(String domainName, String query, String[] fields, String sortfield, String order, int start, int size, int facetCount){

        String finalFields = getConcatenatedField(fields);

        String url = String.format("%s://%s/ebisearch/ws/rest/%s?query=%s&fields=%s&start=%s&size=%s&facetcount=%s&format=JSON",
                config.getProtocol(), config.getHostName(), domainName, query, finalFields, start, size, facetCount, sortfield,order);

        if(!(sortfield == null || sortfield.length() == 0 || order == null || order.length()  == 0))
            url = String.format("%s://%s/ebisearch/ws/rest/%s?query=%s&fields=%s&start=%s&size=%s&facetcount=%s&sortfield=%s&order=%s&format=JSON",
                    config.getProtocol(), config.getHostName(), domainName, query, finalFields, start, size, facetCount, sortfield,order);

        //Todo: Needs to be removed in the future, this is for debugging
        logger.debug(url);

        return this.restTemplate.getForObject(url, QueryResult.class);
    }

    /**
     * This query retrieve the specific entries using a set of identifiers from an specific domain
     * @param domainName domain
     * @param fields fields to be retrieved from the domain for each specific id
     * @param ids   the set of ids to be retrieved.
     * @return QueryResult
     */
    public QueryResult getDatasetsById(String domainName, String[] fields, Set<String> ids){

        String finalFields = getConcatenatedField(fields);

        String finalIds = "";
        if(ids != null && ids.size() > 0){
            int count = 0;
            for(String value: ids){
                if(count == ids.size() - 1)
                    finalIds = finalIds + value;
                else
                    finalIds = finalIds + value + ",";
                count++;
            }
        }

        String url = String.format("%s://%s/ebisearch/ws/rest/%s/entry/%s?fields=%s&format=JSON",
                config.getProtocol(), config.getHostName(), domainName, finalIds,  finalFields, finalFields);


        return this.restTemplate.getForObject(url, QueryResult.class);

    }

    /**
     * This function returns the most frequently terms for an specific field in the database or repository
     * @param domainName The domain name that will be used
     * @param field      The specific field for the most frequently terms.
     * @param exclusionTerms List of terms to be excluded
     * @param size number of terms to be retrieved
     * @return TermResult
     */
    public TermResult getFrequentlyTerms(String domainName, String field, String[] exclusionTerms, int size){

        String exclusionWord = "";
        if(exclusionTerms != null && exclusionTerms.length > 0){
            int count = 0;
            for(String value: exclusionTerms){
                if(count == exclusionTerms.length - 1)
                    exclusionWord = exclusionWord + value;
                else
                    exclusionWord = exclusionWord + value + ",";
                count++;
            }
        }

        String url = String.format("%s://%s/ebisearch/ws/rest/%s/topterms/%s?excludesets=omics_stopwords&size=%s&excludes=%s&format=JSON",
                config.getProtocol(), config.getHostName(), domainName, field, size, exclusionWord);


        return this.restTemplate.getForObject(url, TermResult.class);
    }

    public SimilarResult getSimilarProjects(String domainName, String id, String[] fields){

        String finalFields = getConcatenatedField(fields);

        String url = String.format("%s://%s/ebisearch/ws/rest/%s/entry/%s/morelikethis?mltfields=%s&excludesets=omics_stopwords&format=JSON",
                config.getProtocol(), config.getHostName(), domainName, id,  finalFields);


        return this.restTemplate.getForObject(url, SimilarResult.class);

    }

    private static String getConcatenatedField(String[] fields){

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
        return finalFields;
    }

}
