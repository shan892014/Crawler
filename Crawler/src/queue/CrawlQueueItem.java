package queue;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by shankar on 27/3/16.
 */
public class CrawlQueueItem {
    private String url;
    private HashSet<String> keySet;
    private boolean isAPlace;

    public CrawlQueueItem(String url, HashSet<String> keySet, boolean isAPlace) {
        this.url = url;
        this.keySet = keySet;
        this.isAPlace = isAPlace;
    }

    public HashSet<String> getKeySet() {
        return keySet;
    }

    public void setKeySet(HashSet<String> keySet) {
        this.keySet = keySet;
    }

    public boolean isAPlace() {
        return isAPlace;
    }

    public void setAPlace(boolean APlace) {
        isAPlace = APlace;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
