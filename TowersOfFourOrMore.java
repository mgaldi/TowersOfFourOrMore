import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class TowersOfFourOrMore {

    private static final int MIN_TUBES = 4, MAX_TUBES = 12;
    private static final int MIN_TUBE_CAPACITY = 4, MAX_TUBE_CAPACITY = 8;
    private static final int MIN_DIFF_COLORS = 2;

    public static void main(String[] args) {
        final int tubesNum = Integer.parseInt(args[0]);
        if (tubesNum < MIN_TUBES || tubesNum > MAX_TUBES)
            throw new RuntimeException(String.format("The number of tubes must be between %d and %d",
                    MIN_TUBES, MAX_TUBES));
        final int tubeCapacity = Integer.parseInt(args[1]);
        if (tubeCapacity < MIN_TUBE_CAPACITY || tubeCapacity > MAX_TUBE_CAPACITY)
            throw new RuntimeException(String.format("The tube capacity must be between %d and %d",
                    MIN_TUBE_CAPACITY, MAX_TUBE_CAPACITY));
        final int colorsNum = Integer.parseInt(args[2]);
        if (colorsNum < 2 || colorsNum > tubesNum - 2)
            throw new RuntimeException(String.format("The number of different colors must be at least %d and not" +
                    " greater than the number of tubes - 2", MIN_DIFF_COLORS));


       TubesResolve tubesRes = new TubesResolve(tubesNum, tubeCapacity, colorsNum);


       tubesRes.printTubes();
       tubesRes.resolve();
       tubesRes.printTubes();
       tubesRes.printMoves();

    }


    private int findIndexToFill(){

        return 0;
    }
}
