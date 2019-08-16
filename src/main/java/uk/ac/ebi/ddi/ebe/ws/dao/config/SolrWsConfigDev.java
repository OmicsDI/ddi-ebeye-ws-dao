package uk.ac.ebi.ddi.ebe.ws.dao.config;

public class SolrWsConfigDev extends AbstractSolrWsConfig {
    public SolrWsConfigDev() {
        super("http", "localhost" , 8082 , "/solrapi");
    }
}
