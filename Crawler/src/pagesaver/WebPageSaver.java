package pagesaver;

import callbacks.NetworkResponseCallback;
import callbacks.WebPageSaveCallback;
import org.json.JSONArray;
import org.json.JSONObject;
import queue.CrawlQueueItem;
import util.NetworkRequestor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * Created by shankar on 27/3/16.
 */
@SuppressWarnings("DefaultFileTemplate")
public class WebPageSaver {

    private ArrayList<String> linkUrls;
    private JSONObject dataObject;
    private JSONArray links;
    private String path;
    static String savePath="/home/shankar/travel/pages/";
    int count =0;
    int size;

    public WebPageSaver(ArrayList<String> urls, JSONObject data, CrawlQueueItem url) {
        this.path = savePath+getPathFromUrl(url.getUrl());
        this.dataObject = data;
        this.linkUrls = urls;
        size = linkUrls.size();
        links = new JSONArray();
        count =0;
    }

    private String getPathFromUrl(String url) {
        int index = url.lastIndexOf("/");
        return url.substring(index+1);
    }

    public void startSaveToDisk(final WebPageSaveCallback callback){
        Thread thread = new Thread(() -> {

            for(final String url :linkUrls){
                NetworkRequestor requestor = new NetworkRequestor(url, new NetworkResponseCallback() {
                    @Override
                    public void onComplete(byte[] response) {
                        String name = getNameFromUrl(url);
                        saveDataToDisk(response,name);
                        links.put(name);
                        count++;
                        if(count == size) {
                            handleSaveJson();
                        }
                    }

                    @Override
                    public void onError() {
                        count++;
                        if(count == size) {
                            handleSaveJson();
                        }
                    }
                });
            }
        });

        thread.start();
    }

    private void handleSaveJson() {
        dataObject.put("Links",links);
        saveDataToDisk(dataObject.toString().getBytes(Charset.defaultCharset()), "info.json");
    }

    private String getNameFromUrl(String url) {
        int index = url.lastIndexOf("/");
        return url.substring(index+1);
    }

    private void saveDataToDisk(byte[] data, String name) {
        FileOutputStream file = null;
        String fullPath = path+"/"+name;
        try {
            File newFile = new File(fullPath);
            createFile(newFile);
            file = new FileOutputStream(fullPath,false);
            file.write(data);
        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    private void createFile(File file) throws IOException {
        if(file.exists())
            return;
        File parentFile = file.getParentFile();
        boolean test=false;
        if(!parentFile.exists()) {
            test=parentFile.mkdirs();
        }
        file.createNewFile();
    }
}
