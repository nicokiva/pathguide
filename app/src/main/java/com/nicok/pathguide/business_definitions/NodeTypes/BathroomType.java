package com.nicok.pathguide.business_definitions.NodeTypes;

import android.content.Context;

import com.nicok.pathguide.activities.R;

public class BathroomType extends NodeType {

    @Override
    public Integer getImageSource() {
        return R.drawable.man;
    }

    @Override
    public boolean isFinal() {
        return true;
    }

}
