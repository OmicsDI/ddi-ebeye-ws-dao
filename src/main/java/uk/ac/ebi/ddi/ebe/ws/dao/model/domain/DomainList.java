package uk.ac.ebi.ddi.ebe.ws.dao.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author ypriverol
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class DomainList {

    @JsonProperty("domains")
    public Domain[] list;
}
