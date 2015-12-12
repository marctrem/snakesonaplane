package com.snakesonaplane.wrappers;


import com.snakesonaplane.wrappers.ui.Color;

public interface UIAbstractFactory {
    Color createColor(double red, double green, double blue, double alpha);
}
