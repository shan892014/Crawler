package util;

import queue.CrawlQueueItem;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by shankar on 15/3/16.
 */
@SuppressWarnings("DefaultFileTemplate")
public class URLMatcher {

    @SuppressWarnings("UnusedParameters")
    public static Matcher getURLS(String input, ArrayList<CrawlQueueItem> newUrls, ArrayList<String> linkUrls, HashSet<String> keywords){
//        Pattern pattern = Pattern.compile(
//                "\\b(((ht|f)tp(s?)\\:\\/\\/|~\\/|\\/)|www.)" +
//                        "(\\w+:\\w+@)?(([-\\w]+\\.)+(com|org|net|gov" +
//                        "|mil|biz|info|mobi|name|aero|jobs|museum" +
//                        "|travel|[a-z]{2}))(:[\\d]{1,5})?" +
//                        "(((\\/([-\\w~!$+|.,=]|%[a-f\\d]{2})+)+|\\/)+|\\?|#)?" +
//                        "((\\?([-\\w~!$+|.,*:]|%[a-f\\d{2}])+=?" +
//                        "([-\\w~!$+|.,*:=]|%[a-f\\d]{2})*)" +
//                        "(&(?:[-\\w~!$+|.,*:]|%[a-f\\d{2}])+=?" +
//                        "([-\\w~!$+|.,*:=]|%[a-f\\d]{2})*)*)*" +
//                        "(#([-\\w~!$+|.,*:=]|%[a-f\\d]{2})*)?\\b");

        Pattern pattern = Pattern.compile("\\b(wiki/)[^\"]+");
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            String newUrl = matcher.group();

            if(CrawlerUtil.isALinkUrl(newUrl)){
                linkUrls.add(newUrl);
            }
            else if(CrawlerUtil.isAPlaceLink(newUrl,"","")) {
                CrawlQueueItem item = new CrawlQueueItem(newUrl,null,true);
                newUrls.add(item);
            }
        }
        return matcher;
    }
}
