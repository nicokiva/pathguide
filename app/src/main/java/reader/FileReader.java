package reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileReader implements IReader {

    public String read(InputStream stream) throws IOException {

        // TODO: Remove InputStream as input, Activities should only know filenames.

        try {
            BufferedReader r = new BufferedReader(new InputStreamReader(stream));
            StringBuilder file = new StringBuilder();
            for (String line; (line = r.readLine()) != null; ) {
                file.append(line).append('\n');
            }

            return file.toString();
        } catch (IOException e) {
            // TODO: Do something

            throw e;
        }
    }

}
