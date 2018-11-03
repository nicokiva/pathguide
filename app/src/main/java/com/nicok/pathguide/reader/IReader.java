package com.nicok.pathguide.reader;

import android.content.Context;

import java.io.IOException;

public interface IReader {

    String readAsset(String filename, Context context) throws IOException;

}
