package com.nicok.pathguide.helpers.reader;

import android.content.Context;

import java.io.IOException;

public interface IReader {

    String readAsset(String filename, Context context) throws IOException;

}