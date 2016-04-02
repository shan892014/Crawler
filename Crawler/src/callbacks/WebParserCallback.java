package callbacks;

import org.json.JSONObject;
import queue.CrawlQueueItem;

import java.util.ArrayList;

public interface WebParserCallback {
    void onComplete(ArrayList<CrawlQueueItem> newUrls, ArrayList<String> linkUrls, JSONObject data);
    void onError();
}
