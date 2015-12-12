package com.snakesonaplane.wrappers.ui.javafx;

import com.snakesonaplane.wrappers.ui.Color;


public class ColorWrapper implements Color {

    private javafx.scene.paint.Color color;

    public ColorWrapper(javafx.scene.paint.Color color) {
        this.color = color;
    }

    @Override
    public Object getRawColorObject() {
        return color;
    }

}
