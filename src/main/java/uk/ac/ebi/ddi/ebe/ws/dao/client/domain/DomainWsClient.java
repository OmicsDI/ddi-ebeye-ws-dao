package uk.ac.ebi.ddi.ebe.ws.dao.client.domain;

import uk.ac.ebi.ddi.ebe.ws.dao.client.EbeyeClient;
import uk.ac.ebi.ddi.ebe.ws.dao.config.AbstractEbeyeWsConfig;
import uk.ac.ebi.ddi.ebe.ws.dao.model.domain.DomainList;

/**
 * @author ypriverol
 */
public class DomainWsClient extends EbeyeClient {


    /**
     * Default constructor for Archive clients
     *
     * @param config
     */
    public DomainWsClient(AbstractEbeyeWsConfig config) {
        super(config);
    }

    /**
     * Returns the domain details for an specific domainName
     * @param domainName domain Name
     * @return domain information
     */
    public DomainList getDomainByName(String domainName){

        String url = String.format("%s://%s/ebisearch/ws/rest/%s?format=JSON",
                config.getProtocol(), config.getHostName(), domainName);


        return this.restTemplate.getForObject(url, DomainList.class);
    }

}
