package uk.ac.ebi.ddi.ebe.ws.dao.client.dictionary;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.ddi.ebe.ws.dao.client.EbeyeClient;
import uk.ac.ebi.ddi.ebe.ws.dao.config.AbstractEbeyeWsConfig;
import uk.ac.ebi.ddi.ebe.ws.dao.model.dictionary.DictWord;
import uk.ac.ebi.ddi.ebe.ws.dao.model.dictionary.Item;
import uk.ac.ebi.ddi.ebe.ws.dao.model.dictionary.Suggestion;
import uk.ac.ebi.ddi.ebe.ws.dao.model.dictionary.Suggestions;

import java.util.*;

/**
 * @author Yasset Perez-Riverol (ypriverol@gmail.com)
 * @date 26/06/2015
 */

public class DictionaryClient extends EbeyeClient{

    private static final Logger logger = LoggerFactory.getLogger(DictionaryClient.class);

    /**
     * Default constructor for Ws clients
     *
     * @param config
     */
    public DictionaryClient(AbstractEbeyeWsConfig config) {
        super(config);
    }

    /**
     * Returns the Datasets for a domain with an specific Query
     * @param domainName Domain to retrieve the information
     * @return A list of entries and the facets included
     */
    public DictWord getWordsDomains(String[] domainName, String pattern, int size){

        Map<String, Integer> resultWords = new TreeMap<String, Integer>();

        for(String domain: domainName){
            String url = String.format("%s://%s/ebisearch/ws/rest/%s/autocomplete?term=%s&format=JSON",
                    config.getProtocol(), config.getHostName(), domain, pattern);
            Suggestions results = this.restTemplate.getForObject(url, Suggestions.class);
            if(results != null && results.getEntries() != null && results.getEntries().length > 0){
                for(Suggestion word: results.getEntries()){
                    Integer count = 1;
                    if(resultWords.containsKey(word.getSuggestion()))
                      count = resultWords.get(word.getSuggestion()) + 1;
                    resultWords.put(word.getSuggestion(), count);
                }

            }
        }
        resultWords = sortByValues(resultWords);

        List<Item> items = new ArrayList<>();

        int count = 0;
        Iterator<String> word = resultWords.keySet().iterator();
        while(count < size && word.hasNext())
            items.add(new Item(word.next()));

        return new DictWord(items.size(), items);
    }

    private static <K, V extends Comparable<V>> Map<K, V> sortByValues(final Map<K, V> map) {
        Comparator<K> valueComparator =  new Comparator<K>() {
            public int compare(K k1, K k2) {
                int compare = map.get(k2).compareTo(map.get(k1));
                if (compare == 0) return 1;
                else return compare;
            }
        };
        Map<K, V> sortedByValues = new TreeMap<K, V>(valueComparator);
        sortedByValues.putAll(map);
        return sortedByValues;
    }


}

