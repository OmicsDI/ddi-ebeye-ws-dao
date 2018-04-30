package uk.ac.ebi.ddi.ebe.ws.dao.client.dataset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.ddi.ebe.ws.dao.client.EbeyeClient;
import uk.ac.ebi.ddi.ebe.ws.dao.config.AbstractEbeyeWsConfig;
import uk.ac.ebi.ddi.ebe.ws.dao.model.common.QueryResult;
import uk.ac.ebi.ddi.ebe.ws.dao.model.dataset.SimilarResult;
import uk.ac.ebi.ddi.ebe.ws.dao.model.dataset.TermResult;
import uk.ac.ebi.ddi.ebe.ws.dao.utils.Constans;
import uk.ac.ebi.ddi.ebe.ws.dao.utils.DDIUtils;

import java.util.Set;


/**
 * @author Yasset Perez-Riverol ypriverol
 */
public class DatasetWsClient extends EbeyeClient {

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
     *
     * @param domainName Domain to retrieve the information
     * @param query      Web-service query
     * @param fields     fields to be query
     * @param start      number of the first entry to retrieve
     * @param size       Number of entries to be retrieve maximum 100.
     * @param facetCount Face count the number of facets by entry.
     * @return A list of entries and the facets included
     */
    public QueryResult getDatasets(String domainName, String query, String[] fields,
                                   String sortField, String order, int start, int size, int facetCount) {

        String finalFields = DDIUtils.getConcatenatedField(fields);

        if ((sortField != null && sortField.length() > 0) && (order == null || order.length() == 0))
            order = Constans.ASCENDING;

        String url = String.format("%s://%s/ebisearch/ws/rest/%s?query=%s&fields=%s&start=%s&size=%s&facetcount=%s&format=JSON",
                config.getProtocol(), config.getHostName(), domainName, query,
                finalFields, start, size, facetCount, sortField, order);

        if (!(sortField == null || sortField.length() == 0 || order == null || order.length() == 0))
            url = String.format("%s://%s/ebisearch/ws/rest/%s?query=%s&fields=%s&start=%s&size=%s&facetcount=%s&sortfield=%s&order=%s&format=JSON",
                    config.getProtocol(), config.getHostName(), domainName, query,
                    finalFields, start, size, facetCount, sortField, order);

        //Todo: Needs to be removed in the future, this is for debugging
        logger.debug(url);

        return this.restTemplate.getForObject(url, QueryResult.class);
    }

    /**
     * Returns the Datasets for a domain with a specific query with the optional parameters.
     * This method overrides @getDatasets one but implements another way to sort search
     * results as described in EBI Search documentation.
     *
     * @param domainName Domain to retrieve the information
     * @param query      Web-service query
     * @param fields     the set of the fields to be queried
     * @param start      number of the first entry to retrieve
     * @param size       Number of entries to be retrieve maximum 100.
     * @param facetCount Face count the number of facets by entry.
     * @param sort       Sortable fields and how to sort data.
     * @return           A list of entries and the facets included
     */
    public QueryResult getDatasets(String domainName, String query, String[] fields,
                                   int start, int size, int facetCount, String sort) {
        String finalFields = DDIUtils.getConcatenatedField(fields);
        String prefix = String.format("%s://%s/ebisearch/ws/rest/%s",
                config.getProtocol(), config.getHostName(), domainName);
        String url = String.format("%s?query=%s&fields=%s&start=%s&size=%s&facetcount=%s&sort=%s&format=JSON",
                prefix, query, finalFields, start, size, facetCount, sort);
        return this.restTemplate.getForObject(url, QueryResult.class);
    }

    /**
     * This query retrieve the specific entries using a set of identifiers from an specific domain
     *
     * @param domainName domain
     * @param fields     fields to be retrieved from the domain for each specific id
     * @param ids        the set of ids to be retrieved.
     * @return QueryResult
     */
    public QueryResult getDatasetsById(String domainName, String[] fields, Set<String> ids) {

        String finalFields = DDIUtils.getConcatenatedField(fields);
        String[] myIds = ids.toArray(new String[ids.size()]);
        String finalIds = DDIUtils.getConcatenatedField(myIds);

        String database = Constans.Database.retriveSorlName(domainName);

        String url = String.format("%s://%s/ebisearch/ws/rest/%s/entry/%s?fields=%s&format=JSON",
                config.getProtocol(), config.getHostName(), database, finalIds, finalFields, finalFields);

        return this.restTemplate.getForObject(url, QueryResult.class);

    }

    /**
     * This function returns the most frequently terms for an specific field in the database or repository
     *
     * @param domainName     The domain name that will be used
     * @param field          The specific field for the most frequently terms.
     * @param exclusionTerms List of terms to be excluded
     * @param size           number of terms to be retrieved
     * @return TermResult
     */
    public TermResult getFrequentlyTerms(String domainName, String field, String[] exclusionTerms, int size) {

        String exclusionWord = DDIUtils.getConcatenatedField(exclusionTerms);

        String url = String.format("%s://%s/ebisearch/ws/rest/%s/topterms/%s?excludesets=omics_stopwords&size=%s&excludes=%s&format=JSON",
                config.getProtocol(), config.getHostName(), domainName, field, size, exclusionWord);

        return this.restTemplate.getForObject(url, TermResult.class);
    }

    public SimilarResult getSimilarProjects(String domainName, String id, String[] fields) {

        String finalFields = DDIUtils.getConcatenatedField(fields);

        String url = String.format("%s://%s/ebisearch/ws/rest/%s/entry/%s/morelikethis/omics?mltfields=%s&excludesets=omics_stopwords&entryattrs=score&format=JSON",
                config.getProtocol(), config.getHostName(), domainName, id, finalFields);

        return this.restTemplate.getForObject(url, SimilarResult.class);

    }
}
