package uk.ac.ebi.ddi.ebe.ws.dao.client.publication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestClientException;
import org.springframework.web.util.UriComponentsBuilder;
import uk.ac.ebi.ddi.ebe.ws.dao.client.EbeyeClient;
import uk.ac.ebi.ddi.ebe.ws.dao.config.AbstractEbeyeWsConfig;
import uk.ac.ebi.ddi.ebe.ws.dao.model.common.QueryResult;
import uk.ac.ebi.ddi.ebe.ws.dao.utils.DDIUtils;

import java.net.URI;
import java.util.Set;

/**
 * @author Yasset Perez-Riverol (ypriverol@gmail.com)
 * @date 11/06/2015
 */
public class PublicationWsClient extends EbeyeClient{

    private static final Logger logger = LoggerFactory.getLogger(PublicationWsClient.class);

    /**
     * Default constructor for Ws clients
     *
     * @param config
     */
    public PublicationWsClient(AbstractEbeyeWsConfig config) {
        super(config);
    }

    /**
     * This function retrieve a set of publications by Ids and the corresponding fields
     * @param fields The fields to be retrieved
     * @param ids The pubmed ids
     * @return A set of publications
     */
    public QueryResult getPublications(String[] fields, Set<String> ids) throws RestClientException{

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
        UriComponentsBuilder builder = UriComponentsBuilder.newInstance()
                .scheme(config.getProtocol())
                .host(config.getHostName())
                .path("/ebisearch/ws/rest/pubmed/entry")
                .path("/" + finalIds)
                .queryParam("fields", DDIUtils.getConcatenatedField(fields))
                .queryParam("format", "JSON");

        URI uri = builder.build().encode().toUri();

        return getRetryTemplate().execute(context -> restTemplate.getForObject(uri, QueryResult.class));
    }
}
