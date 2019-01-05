package com.nicok.pathguide.helpers.reader;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileReader implements IReader {

    public String readAsset(String filename, Context context) throws IOException {
        InputStreamReader stream = new InputStreamReader(context.getAssets().open(filename));

        return read(stream);
    }

    private String read(InputStreamReader stream) throws IOException {
        try {
            BufferedReader r = new BufferedReader(stream);
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
