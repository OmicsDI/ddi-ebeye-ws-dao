package uk.ac.ebi.ddi.ebe.ws.dao.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import uk.ac.ebi.ddi.ebe.ws.dao.client.publication.PublicationWsClient;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Arrays;

/**
 * @author jadianes
 * @author ypriverol
 *
 */
public abstract class AbstractEbeyeWsConfig {

    private String hostName;
    private String protocol;
    protected String httpProxyHost;
    protected int httpProxyPort;
    final Logger LOGGER = LoggerFactory.getLogger(this.getClass().getName());

    protected AbstractEbeyeWsConfig(String protocol, String hostName) {
        this.hostName = hostName;
        this.protocol = protocol;
    }

    public String getHostName() {
        return hostName;
    }


    public String getProtocol() {
        return protocol;
    }

    public String getHttpProxyHost() {
        return httpProxyHost;
    }

    public void setHttpProxyHost(String httpProxyHost) {
        this.httpProxyHost = httpProxyHost;
    }

    public int getHttpProxyPort() {
        return httpProxyPort;
    }

    public void setHttpProxyPort(int httpProxyPort) {
        this.httpProxyPort = httpProxyPort;
    }

    public RestTemplate restTemplate() {
        //boolean isHttpProxyHostInitialised = doesConfigObjectContainField(this, "httpProxyHost");
        //boolean isHttpProxyPortInitialised = doesConfigObjectContainField(this, "httpProxyPort");
        boolean isHttpProxyHostInitialised = this.httpProxyHost != null ? true : false;
        boolean isHttpProxyPortInitialised = this.httpProxyPort != 80 ? true : false;
        RestTemplate restTemplate = new RestTemplate();
        if (isHttpProxyHostInitialised && isHttpProxyPortInitialised) {
            LOGGER.debug("Using {} and {} as HTTP Proxy Host and Port", this.httpProxyHost, this.httpProxyPort);
            SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(this.httpProxyHost, this.httpProxyPort));
            requestFactory.setProxy(proxy);
            restTemplate = new RestTemplate(requestFactory);
        } else {
            LOGGER.debug("Using the default HTTP Proxy or NoProxy");
        }
        return restTemplate;
    }

    /**
     * Checks a field existing or be initialised or not in an Object
     *
     * @param object    object in question
     * @param fieldName A String indicating the field name
     * @return  a boolean value
     */
    private boolean doesConfigObjectContainField(Object object, String fieldName) {
        return Arrays.stream(object.getClass().getFields())
                .anyMatch(f -> f.getName().equals(fieldName));
    }
}
