package pagesaver;

import callbacks.WebPageSaveCallback;
import org.json.JSONObject;
import queue.CrawlQueueItem;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by shankar on 27/3/16.
 */
public class WebPageSaverController {

    private static WebPageSaverController saverController;
    private final HashMap<String,WebPageSaver> pageSaveRequest;

    protected WebPageSaverController(){
        pageSaveRequest = new HashMap<>();
    }

    public static WebPageSaverController getSaverController(){

        synchronized (WebPageSaverController.class) {
            if (saverController == null) {
                saverController = new WebPageSaverController();
            }
        }

        return saverController;
    }


    public void addRequest(CrawlQueueItem url, JSONObject data, ArrayList<String> urls){

        synchronized (pageSaveRequest) {
            if (!pageSaveRequest.containsKey(url.getUrl())) {
                WebPageSaver saver = new WebPageSaver(urls, data, url);
                pageSaveRequest.put(url.getUrl(),saver);
                saver.startSaveToDisk(new WebPageSaveCallback() {
                    @Override
                    public void onSuccess() {
                        pageSaveRequest.remove(url);
                    }

                    @Override
                    public void onError() {
                        pageSaveRequest.remove(url);
                    }
                });
            }
        }
    }
}
