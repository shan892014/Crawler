package util;

/**
 * Created by shankar on 27/3/16.
 */
public class CrawlerUtil {

    public static boolean isAPlaceLink(String url,String preText,String postText){
        if(!url.contains("en.wikipedia.org"))
            return false;
        return false;
    }

    public static boolean isALinkUrl(String url){
        if(url==null || url.length()==0)
            return false;
        int fileExtension = url.lastIndexOf(".");
        if(fileExtension == -1 || fileExtension<(url.length()-4))
            return false;
        String extension = url.substring(fileExtension+1);
        return fileExtensionAllowed(extension);
    }

    private static boolean fileExtensionAllowed(String extension) {

        FileExtensions extensions = FileExtensions.getValue(extension);
        return extensions!=null;
    }
}
