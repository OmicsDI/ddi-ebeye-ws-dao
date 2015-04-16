package uk.ac.ebi.ddi.ebe.ws.dao.client;

import org.springframework.web.client.RestTemplate;
import uk.ac.ebi.ddi.ebe.ws.dao.config.AbstractEbeyeWsConfig;

/**
 * Abstract client to query the EBI search.
 *
 * @author ypriverol
 */
public class EbeyeClient {

    protected RestTemplate restTemplate;
    protected AbstractEbeyeWsConfig config;

    /**
     * Default constructor for Archive clients
     * @param config
     */
    public EbeyeClient(AbstractEbeyeWsConfig config){
        this.config = config;
        this.restTemplate = new RestTemplate();
    }

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public AbstractEbeyeWsConfig getConfig() {
        return config;
    }

    public void setConfig(AbstractEbeyeWsConfig config) {
        this.config = config;
    }
}
