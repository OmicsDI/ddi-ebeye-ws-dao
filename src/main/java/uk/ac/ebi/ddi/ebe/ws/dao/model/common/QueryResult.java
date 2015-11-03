package uk.ac.ebi.ddi.ebe.ws.dao.model.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import uk.ac.ebi.ddi.ebe.ws.dao.model.common.Entry;
import uk.ac.ebi.ddi.ebe.ws.dao.model.common.Facet;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Yasset Perez-Riverol ypriverol
 */

@JsonIgnoreProperties(ignoreUnknown = true)

public class QueryResult {

    @JsonProperty("hitCount")
    Integer count = null;

    @JsonProperty("entries")
    Entry[] entries = null;

    @JsonProperty("facets")
    Facet[] facets;

    public QueryResult(){
        count = 0;
    }
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

    public void addResults(QueryResult results) {

        Set<Entry> entries = new HashSet<>();
        Set<Facet> facets  = new HashSet<>();

        if(results != null){

            if(this.entries != null)
                for(Entry entry: this.entries)
                    entries.add(entry);
            if(results.entries != null)
                for(Entry entry: results.entries)
                    entries.add(entry);
            if(this.facets != null)
                for(Facet entry: this.facets)
                    facets.add(entry);
            if(results.facets != null)
                for(Facet entry: results.facets)
                    facets.add(entry);

            this.facets  = new Facet[facets.size()];
            this.entries = new Entry[entries.size()];

            int i = 0;
            for(Entry entry: entries){
                this.entries[i] = entry;
                i++;
            }

            i = 0;
            for(Facet entry: facets){
                this.facets[i] = entry;
                i++;
            }

            count = entries.size();
        }
    }
}
