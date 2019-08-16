package uk.ac.ebi.ddi.ebe.ws.dao.solrClient.domain;

import org.springframework.web.util.UriComponentsBuilder;
import uk.ac.ebi.ddi.ebe.ws.dao.config.AbstractEbeyeWsConfig;
import uk.ac.ebi.ddi.ebe.ws.dao.config.AbstractSolrWsConfig;
import uk.ac.ebi.ddi.ebe.ws.dao.model.domain.DomainList;
import uk.ac.ebi.ddi.ebe.ws.dao.solrClient.EbeyeClient;

import java.net.URI;

/**
 * This class contain all the methods related with the domain queries, including the number of entries, etc.
 *
 * @author ypriverol
 */
public class DomainWsClient extends EbeyeClient {

    public DomainWsClient(AbstractSolrWsConfig config) {
        super(config);
    }

    /**
     * Returns the domain details for an specific domainName
     *
     * @param domainName domain Name
     * @return domain information
     */
    public DomainList getDomainByName(String domainName) {

        UriComponentsBuilder builder = UriComponentsBuilder.newInstance()
                .scheme(config.getProtocol())
                .host(config.getHostName())
                .port(config.getPort())
                .path(config.getBasePath())
                .path("/" + domainName)
                .queryParam("format", "JSON");
        URI uri = builder.build().encode().toUri();
        return restTemplate.getForObject(uri, DomainList.class);
    }

    public DomainList getDomainStaticByName(String domainName) {

        UriComponentsBuilder builder = UriComponentsBuilder.newInstance()
                .scheme(config.getProtocol())
                .host(config.getHostName())
                .port(config.getPort())
                .path(config.getBasePath())
                .path("/status/omics");
//                .queryParam("format", "JSON");
        URI uri = builder.build().encode().toUri();
        Object object = restTemplate.getForObject(uri, DomainList.class);
        return (DomainList)object;
    }

}
