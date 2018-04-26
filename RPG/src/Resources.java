import java.io.InputStream;

public class Resources {

	public static InputStream load(String path) {
		InputStream input = Resources.class.getResourceAsStream(path);
		if (input == null) {
			input = Resources.class.getResourceAsStream("/" + path);
		}
		return input;
	}

}