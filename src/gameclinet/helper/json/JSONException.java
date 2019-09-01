package gameclinet.helper.json;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

public class JSONException extends RuntimeException {
	private static final long serialVersionUID = 0L;

	public JSONException(String message) {
		super(message);
	}

	public JSONException(String message, Throwable cause) {
		super(message, cause);
	}

	public JSONException(Throwable cause) {
		super(cause.getMessage(), cause);
	}
}
