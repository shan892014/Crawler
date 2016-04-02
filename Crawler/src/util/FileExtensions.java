package util;

/**
 * Created by shankar on 27/3/16.
 */
public enum FileExtensions {
    FILE_EXTENSIONS_PNG,
    FILE_EXTENSIONS_JPG,
    FILE_EXTENSIONS_JPEG,
    FILE_EXTENSIONS_PDF;

    @Override
    public String toString() {
        switch (this){
            case FILE_EXTENSIONS_JPEG:
                return "jpeg";
            case FILE_EXTENSIONS_JPG:
                return "jpg";
            case FILE_EXTENSIONS_PNG:
                return "png";
        }

        return "";
    }


    public static FileExtensions getValue(String extension) {

        if(extension == null || extension.length()==0)
            return null;

        if(extension.equalsIgnoreCase("jpg"))
            return FILE_EXTENSIONS_JPG;
        if(extension.equalsIgnoreCase("jpeg"))
            return FILE_EXTENSIONS_JPEG;
        if(extension.equalsIgnoreCase("png"))
            return FILE_EXTENSIONS_PNG;
        return null;
    }
}
