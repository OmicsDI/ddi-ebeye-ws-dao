package uk.ac.ebi.ddi.ebe.ws.dao.model.dataset;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import uk.ac.ebi.ddi.ebe.ws.dao.model.common.Term;

/**
 * @author  @ypriverol Yasset Perez-Riverol
 */

@JsonIgnoreProperties(ignoreUnknown = true)

public class TermResult {

    @JsonProperty("topTerms")
    Term[] terms;

    public Term[] getTerms() {
        return terms;
    }

    public void setTerms(Term[] terms) {
        this.terms = terms;
    }
}
