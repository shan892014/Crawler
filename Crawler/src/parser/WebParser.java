package parser;

import callbacks.WebParserCallback;
import org.json.JSONObject;
import queue.CrawlQueueItem;
import util.URLMatcher;

import java.util.ArrayList;
import java.util.HashSet;


public class WebParser {

	private String response;
	private HashSet<String> keywords;

	public WebParser(String response, HashSet<String> keywords) {
		// TODO Auto-generated constructor stub
		this.response=response;
		this.keywords = keywords;
	}

	public void parse(final WebParserCallback callback){
		//Parse and populate this arrayList
		Thread thread = new Thread(() -> {

			JSONObject data = populateJSONResponse(response);
            ArrayList<CrawlQueueItem> newUrls = new ArrayList<>();
			ArrayList<String> linkUrls = new ArrayList<>();
			URLMatcher.getURLS(response,newUrls,linkUrls,keywords);
            callback.onComplete(newUrls,linkUrls,data);
        });
		thread.start();
	}

	@SuppressWarnings("UnusedParameters")
	private JSONObject populateJSONResponse(String response) {
		return new JSONObject();
	}
}
