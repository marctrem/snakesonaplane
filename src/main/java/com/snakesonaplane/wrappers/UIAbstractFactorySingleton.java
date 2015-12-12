package com.snakesonaplane.wrappers;

/**
 * Created by marc on 12/11/15.
 */
public class UIAbstractFactorySingleton {

    private static UIAbstractFactory instance;

    public static UIAbstractFactory get() {
        return instance;
    }

    public static synchronized void initialize(UIAbstractFactoryFactoryMethod.UIType uiType) {
        if (instance == null) {
            instance = UIAbstractFactoryFactoryMethod.get(uiType);
        } else {
            throw new RuntimeException("UIFactory already initialized.");
        }
    }
}
