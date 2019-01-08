package com.nicok.pathguide.business_definitions.NodeTypes;

import android.content.Context;

import java.io.Serializable;

public abstract class NodeType implements Serializable {

    public static final String BATHROOM_TYPE = "bathroom";
    public static final String CLASSROOM_TYPE = "classroom";
    public static final String GATEWAY_TYPE = "gateway";

    public boolean isFinal() {
        return false;
    }

    public Integer getImageSource() {
        return null;
    }

}
