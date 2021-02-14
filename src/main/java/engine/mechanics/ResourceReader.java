package engine.mechanics;

import java.io.InputStream;
import java.net.URL;

public class ResourceReader {
    String path;

    public ResourceReader(String path) {
        this.path = path;
    }

    public InputStream getInputStream() {
        return this.getClass().getClassLoader().getResourceAsStream(path);
    }

    public URL getURI() {
        return this.getClass().getClassLoader().getResource(path);
    }
}
