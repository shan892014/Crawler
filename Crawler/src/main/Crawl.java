package main;

import controller.CrawlController;

public class Crawl {

	public Crawl() {
		// TODO Auto-generated constructor stub
	}

	public static void main(final String[] args) {
		System.out.println("Hello");
		String mainUrl = "https://en.wikipedia.org/wiki/Tourism_in_Karnataka";
		CrawlController crawlController = new CrawlController(mainUrl,false);
	}

}
