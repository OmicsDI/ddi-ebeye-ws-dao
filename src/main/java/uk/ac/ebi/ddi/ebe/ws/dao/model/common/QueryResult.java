package uk.ac.ebi.ddi.ebe.ws.dao.model.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import uk.ac.ebi.ddi.ebe.ws.dao.model.common.Entry;
import uk.ac.ebi.ddi.ebe.ws.dao.model.common.Facet;

/**
 * @author Yasset Perez-Riverol ypriverol
 */

@JsonIgnoreProperties(ignoreUnknown = true)

public class QueryResult {

    @JsonProperty("hitCount")
    Integer count = null;

    @JsonProperty("entries")
    Entry[] entries;

    @JsonProperty("facets")
    Facet[] facets;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Entry[] getEntries() {
        return entries;
    }

    public void setEntries(Entry[] entries) {
        this.entries = entries;
    }

    public Facet[] getFacets() {
        return facets;
    }

    public void setFacets(Facet[] facets) {
        this.facets = facets;
    }
}
