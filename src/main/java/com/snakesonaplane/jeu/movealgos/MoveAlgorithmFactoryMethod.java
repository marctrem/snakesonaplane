package com.snakesonaplane.jeu.movealgos;

import com.snakesonaplane.exceptions.UnknownAlgorithmException;
/**
 * Created by fred on 23/11/15.
 */
public class MoveAlgorithmFactoryMethod {
    public enum Algo {
        MOVE_ALGO_1, MOVE_ALGO_2, MOVE_ALGO_3
    }

    public MoveAlgorithm getMoveAlgorithm(Algo algo_name) throws UnknownAlgorithmException {
        switch(algo_name) {
            case MOVE_ALGO_1:
                return new MoveAlgorithm1();
            case MOVE_ALGO_2:
                return new MoveAlgorithm2();
            case MOVE_ALGO_3:
                return new MoveAlgorithm3();
            default:
                throw new UnknownAlgorithmException();
        }
    }

}
