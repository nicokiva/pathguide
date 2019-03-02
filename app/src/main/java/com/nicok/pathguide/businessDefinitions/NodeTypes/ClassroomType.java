package com.nicok.pathguide.businessDefinitions.NodeTypes;

import com.nicok.pathguide.activities.R;

public class ClassroomType extends NodeType {

    @Override
    public Integer getImageSource() {
        return R.drawable.classroom;
    }

    @Override
    public boolean isFinal() {
        return true;
    }

}
