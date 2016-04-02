package util;

import callbacks.NetworkResponseCallback;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class NetworkRequestor {

	NetworkResponseCallback callback;
	String url;
	private final String USER_AGENT = "Mozilla/5.0";

	public NetworkRequestor(String url,NetworkResponseCallback callback) {
		// TODO Auto-generated constructor stub
		this.url=url;
		this.callback =callback;
		startRequest();
	}

	private void startRequest() {
		// TODO Auto-generated method stub
		Thread thread = new Thread(() -> {
            String webUrl = url;

            URL obj;
            try {
                obj = new URL(webUrl);

                HttpURLConnection con;
                con = (HttpURLConnection) obj.openConnection();

                // optional default is GET
                con.setRequestMethod("GET");

                //add request header
                con.setRequestProperty("User-Agent", USER_AGENT);

                int responseCode = con.getResponseCode();
                System.out.println("\nSending 'GET' request to URL : " + url);
                System.out.println("Response Code : " + responseCode);


                if(responseCode>=200 && responseCode<=300) {
                    int len;
                    InputStream in = con.getInputStream();
                    byte[] buffer = new byte[4096];
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    while (-1 != (len = in.read(buffer))) {
                        bos.write(buffer, 0, len);
                    }

                    callback.onComplete(bos.toByteArray());
                }
                else{
                    callback.onError();
                }
            }
            catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                callback.onError();
            }
        });
		thread.start();

	}


}
