package uk.ac.ebi.ddi.ebe.ws.dao.client.europmc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.ddi.ebe.ws.dao.client.EbeyeClient;
import uk.ac.ebi.ddi.ebe.ws.dao.config.AbstractEbeyeWsConfig;
import uk.ac.ebi.ddi.ebe.ws.dao.model.europmc.CitationResponse;

/**
 * Created by gaur on 13/07/17.
 */
public class CitationClient extends EbeyeClient {

    private static final Logger logger = LoggerFactory.getLogger(CitationClient.class);

    public CitationClient(AbstractEbeyeWsConfig config) {
        super(config);
    }

    public CitationResponse getCitations(String accession){
        String url = String.format("%s://%s/europepmc/webservices/rest/search?query=%s&format=JSON",
                config.getProtocol(), config.getHostName(), accession);

        //Todo: Needs to be removed in the future, this is for debugging
        logger.debug(url);
        return this.restTemplate.getForObject(url, CitationResponse.class);
    }
}
