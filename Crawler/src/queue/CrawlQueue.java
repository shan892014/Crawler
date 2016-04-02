package queue;

import java.util.*;


public class CrawlQueue {

	public CrawlQueue() {
	}
	/*
	 * List of Urls that have been crawled.
	 */
	private HashSet<String> crawledUrls = new HashSet<String>();
	/*
	 * List of Urls that are available for crawling
	 */
	private Queue<CrawlQueueItem> requestUrls = new LinkedList<>();

	private HashSet<CrawlQueueItem> currentQueue  = new HashSet<>();

	public synchronized boolean isKnown(String url) {
		if(crawledUrls.contains(url) || currentQueue.contains(url)) {
			return true;
		} else {
			return false;
		}
	} 
	
	public int size(){
		return requestUrls.size();
	}
	
	public synchronized CrawlQueueItem getNextRequest(){
		if(size()>0){
			return requestUrls.remove();
		}
		return null;
	}
	
	public synchronized void addToCrawlQueue(CrawlQueueItem item) {

		requestUrls.add(item);
		currentQueue.add(item);
	}

	public void addToProcessed(CrawlQueueItem url) {
		requestUrls.remove(url);
		currentQueue.remove(url);
	}

	public void removeUrlFromRequest(CrawlQueueItem url) {
		crawledUrls.add(url.getUrl());
	}
}
