package reader;

import java.io.IOException;
import java.io.InputStream;

public interface IReader {

    String read(InputStream reader) throws IOException;

}
