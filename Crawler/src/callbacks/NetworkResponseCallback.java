package callbacks;

public interface NetworkResponseCallback {
	void onComplete(byte [] responseData);
	void onError();
}
