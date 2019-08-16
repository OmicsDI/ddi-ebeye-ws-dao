package uk.ac.ebi.ddi.ebe.ws.dao.solrClient;

import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.client.RestTemplate;
import uk.ac.ebi.ddi.ebe.ws.dao.config.AbstractEbeyeWsConfig;
import uk.ac.ebi.ddi.ebe.ws.dao.config.AbstractSolrWsConfig;

import java.util.Collections;

/**
 * Abstract client to query the EBI search.
 *
 * @author ypriverol
 */
public class EbeyeClient {

    private static final int RETRIES = 5;
    protected RestTemplate restTemplate;
    protected AbstractSolrWsConfig config;
    private RetryTemplate retryTemplate = new RetryTemplate();

    public EbeyeClient(AbstractSolrWsConfig config) {
        this.config = config;
        this.restTemplate = new RestTemplate();
        SimpleRetryPolicy policy =
                new SimpleRetryPolicy(RETRIES, Collections.singletonMap(Exception.class, true));
        retryTemplate.setRetryPolicy(policy);
        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        backOffPolicy.setInitialInterval(2000);
        backOffPolicy.setMultiplier(1.6);
        retryTemplate.setBackOffPolicy(backOffPolicy);
    }

    protected RetryTemplate getRetryTemplate() {
        return retryTemplate;
    }

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public AbstractSolrWsConfig getConfig() {
        return config;
    }

    public void setConfig(AbstractSolrWsConfig config) {
        this.config = config;
    }
}
