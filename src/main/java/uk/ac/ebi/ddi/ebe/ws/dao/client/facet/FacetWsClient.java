package uk.ac.ebi.ddi.ebe.ws.dao.client.facet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.ddi.ebe.ws.dao.client.EbeyeClient;
import uk.ac.ebi.ddi.ebe.ws.dao.config.AbstractEbeyeWsConfig;
import uk.ac.ebi.ddi.ebe.ws.dao.model.domain.DomainList;
import uk.ac.ebi.ddi.ebe.ws.dao.model.facet.FacetList;
import uk.ac.ebi.ddi.ebe.ws.dao.utils.Constans;

/**
 * @author Yasset Perez-Riverol ypriverol
 */

public class FacetWsClient extends EbeyeClient{

    private static final Logger logger = LoggerFactory.getLogger(FacetWsClient.class);
    /**
     * Default constructor for Ws clients
     *
     * @param config
     */
    public FacetWsClient(AbstractEbeyeWsConfig config) {
        super(config);
    }

    /**
     * Retrieve for a parent domain and a set of subdomains the number of entries for an specific facet
     * @param parentdomain parent domain
     * @param domains      subdomains
     * @param facetField   the facet field
     * @return Return a facet field with the information of the term
     */
    public FacetList getFacetEntriesByDomains(String parentdomain, String[] domains, String facetField, int count){

        String domain = "";
        if(domains != null && domains.length > 0){
            int i = 0;
            for (String domainValue: domains){
                domain = (i == 0)? domainValue: domain + " " + Constans.OR + " " + domainValue;
                i++;
            }
        }

        String url = String.format("%s://%s/ebisearch/ws/rest/%s?query=domain_source:(%s)&facetfields=%s&facetcount=%s&size=0&format=JSON",
                config.getProtocol(), config.getHostName(), parentdomain,domain,facetField,count);

        //Todo: Needs to be removed in the future, this is for debugging
        logger.debug(url);

        return this.restTemplate.getForObject(url, FacetList.class);
    }

}
