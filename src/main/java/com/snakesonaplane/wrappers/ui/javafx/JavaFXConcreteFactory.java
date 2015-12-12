package com.snakesonaplane.wrappers.ui.javafx;

import com.snakesonaplane.wrappers.UIAbstractFactory;
import com.snakesonaplane.wrappers.ui.Color;

public class JavaFXConcreteFactory implements UIAbstractFactory {

    @Override
    public Color createColor(double red, double green, double blue, double alpha) {
        return new ColorWrapper(new javafx.scene.paint.Color(red, green, blue, alpha));
    }
}
