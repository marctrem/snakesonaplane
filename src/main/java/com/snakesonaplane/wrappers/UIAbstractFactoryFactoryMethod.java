package com.snakesonaplane.wrappers;


import com.snakesonaplane.wrappers.ui.javafx.JavaFXConcreteFactory;

public class UIAbstractFactoryFactoryMethod {

    private UIAbstractFactoryFactoryMethod() {
    }

    static UIAbstractFactory get(UIType uiType) {
        switch (uiType) {
            case JAVAFX:
                return new JavaFXConcreteFactory();
            default:
                throw new RuntimeException(uiType.getClass().getName() + " is not a valid UIType");
        }
    }

    public enum UIType {JAVAFX}
}
