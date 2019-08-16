package uk.ac.ebi.ddi.ebe.ws.dao.config;

public abstract class AbstractSolrWsConfig {

    private String hostName;
    private String protocol;
    private Integer port;
    private String basePath;

    public AbstractSolrWsConfig(String hostName, String protocol, Integer port, String basePath) {
        this.hostName = hostName;
        this.protocol = protocol;
        this.port = port;
        this.basePath = basePath;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }
}
