package controller;

import callbacks.NetworkResponseCallback;
import callbacks.WebParserCallback;
import org.json.JSONObject;
import pagesaver.WebPageSaverController;
import parser.WebParser;
import queue.CrawlQueue;
import queue.CrawlQueueItem;
import util.NetworkRequestor;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Timer;
import java.util.TimerTask;


public class CrawlController {
	
	private CrawlQueue crawlQueue;
	
	public CrawlController(String url,boolean isAPlace) {
		this.crawlQueue = new CrawlQueue();
        HashSet<String> keyWords = new HashSet<>();
        CrawlQueueItem item =new CrawlQueueItem(url,keyWords,isAPlace);
		crawlQueue.addToCrawlQueue(item);
		Timer newRequestTimer =new Timer();
		newRequestTimer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				createNewUrlRequest();
			}
		},1000,1000);
	}
	
	private void createNewUrlRequest(){
		CrawlQueueItem item = null;
		synchronized(CrawlQueue.class){
			if(crawlQueue.size()>0){
				item = crawlQueue.getNextRequest();
			}
		}
		
		if(item!=null){
            final CrawlQueueItem copyItem =item;
			@SuppressWarnings("unused")
			NetworkRequestor request =new NetworkRequestor(item.getUrl(),new NetworkResponseCallback(){

				@Override
				public void onComplete(byte[] responseByte) {
					// TODO Auto-generated method stub
					String response = new String(responseByte, Charset.defaultCharset());
					handleNewResponse(copyItem,response);
				}

				@Override
				public void onError() {
					// TODO Auto-generated method stub

				}

			});
		}
	}
	
	private void handleNewResponse(final CrawlQueueItem item, String response) {

		removeFromCrawlerQueue(item);
		WebParser parser =new WebParser(response,item.getKeySet());
		parser.parse(new WebParserCallback(){

			@Override
			public void onComplete(ArrayList<CrawlQueueItem> newUrls, ArrayList<String> linkUrls, JSONObject data) {
				addNewUrls(newUrls);
                if(item.isAPlace()) {
                    WebPageSaverController controller = WebPageSaverController.getSaverController();
                    controller.addRequest(item, data, linkUrls);
                }
			}

			@Override
			public void onError() {
				// TODO Auto-generated method stub
				
			}
			
		});
	}

	private void removeFromCrawlerQueue(CrawlQueueItem url) {
		synchronized (CrawlQueue.class){
			crawlQueue.removeUrlFromRequest(url);
			crawlQueue.addToProcessed(url);
		}
	}

	private void addNewUrls(ArrayList<CrawlQueueItem> newUrls) {
		synchronized (CrawlQueue.class){
            for(CrawlQueueItem item:newUrls){
                crawlQueue.addToCrawlQueue(item);
            }
        }
	}
}
