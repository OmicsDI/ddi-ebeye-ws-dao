package uk.ac.ebi.ddi.ebe.ws.dao.solrClient.facet;

import org.springframework.web.util.UriComponentsBuilder;
import uk.ac.ebi.ddi.ebe.ws.dao.config.AbstractEbeyeWsConfig;
import uk.ac.ebi.ddi.ebe.ws.dao.config.AbstractSolrWsConfig;
import uk.ac.ebi.ddi.ebe.ws.dao.model.facet.FacetList;
import uk.ac.ebi.ddi.ebe.ws.dao.solrClient.EbeyeClient;
import uk.ac.ebi.ddi.ebe.ws.dao.utils.Constans;

import java.net.URI;
import java.util.Arrays;

/**
 * @author Yasset Perez-Riverol ypriverol
 */

public class FacetWsClient extends EbeyeClient {

    private static final int MAX_DOMAIN = 17;

    public FacetWsClient(AbstractSolrWsConfig config) {
        super(config);
    }

    /**
     * Retrieve for a parent domain and a set of subdomains the number of entries for an specific facet
     *
     * @param parentdomain parent domain
     * @param domains      subdomains
     * @param facetField   the facet field
     * @param count        number of facet
     * @return Return a facet field with the information of the term
     */
    public FacetList getFacetEntriesByDomains(String parentdomain, String[] domains, String facetField, int count) {

        String[] domainToSearch = domains;
        if (domains.length > MAX_DOMAIN) {
            domainToSearch = Arrays.copyOfRange(domains, 0, domains.length);
        }

        String domain = String.join(" " + Constans.OR + " ", domainToSearch);

        UriComponentsBuilder builder = UriComponentsBuilder.newInstance()
                .scheme(config.getProtocol())
                .host(config.getHostName())
                .port(config.getPort())
                .path(config.getBasePath())
                .path("/" + parentdomain)
                .queryParam("query", "domain_source:(" + domain + ")")
//                .queryParam("query", "domain_source:(" + domain + ")")
                .queryParam("facetfields", facetField)
                .queryParam("facetcount", count)
                .queryParam("size", "0")
                .queryParam("format", "JSON");

        URI uri = builder.build().encode().toUri();
        return restTemplate.getForObject(uri, FacetList.class);
    }

}
