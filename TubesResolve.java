import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class TubesResolve {
    private int tubesNum, tubeCapacity,  colorsNum;
    private List<Stack<Integer>> tubes;
    private List<String> moves;

    public TubesResolve(int tubesNum, int tubeCapacity,  int colorsNum ){
        this.tubesNum = tubesNum;
        this.tubeCapacity = tubeCapacity;
        this.colorsNum = colorsNum;
        moves = new ArrayList<>();
        tubes = new ArrayList<>();
        for (int i = 0; i < tubesNum; i++)
            tubes.add(new Stack<>());

        final Map<Integer, Integer> colorsAvailabilities = new HashMap<>();
        for (int i = 0; i < colorsNum; i++)
            colorsAvailabilities.put(i, tubeCapacity);

        for (int i = 0; i < colorsNum; i++) {
           Stack<Integer> currentTube = tubes.get(i);
            while (currentTube.size() < tubeCapacity) {
                int pickedColor = List.copyOf(colorsAvailabilities.keySet())
                        .get(ThreadLocalRandom.current().nextInt(colorsAvailabilities.keySet().size()));
                currentTube.push(pickedColor);
                int colorAvailability = colorsAvailabilities.get(pickedColor) - 1;
                if (colorAvailability > 0)
                    colorsAvailabilities.put(pickedColor, colorAvailability);
                else
                    colorsAvailabilities.remove(pickedColor);
            }
        }

    }

    public void resolve(){
        boolean done[] = new boolean[colorsNum];
        List<Integer> indexes = new ArrayList<>();
        for(int i = 0; i < tubesNum; i++){
            indexes.add(i);
        }
        for(int i = 0; i < colorsNum; i++){
            int indexToColor = findIndexToColor();
            indexes.remove((Integer)indexToColor);
            int indexWithColor = indexWithColor(i, indexToColor);

            while(!done[i]){

                if(tubes.get(indexWithColor).peek() == i){
                    tubes.get(indexToColor).push(tubes.get(indexWithColor).pop());
                    moves.add("t" + indexWithColor + " - t" + indexToColor );
                    indexWithColor = indexWithColor(i, indexToColor);
                }else{
                    loop:
                    for(int t : indexes){
                        if(t != indexWithColor && tubes.get(t).size() != tubeCapacity){
                            tubes.get(t).push(tubes.get(indexWithColor).pop());
                            moves.add("t" + indexWithColor + " - t" + t);
                            break loop;
                        }

                    }

                }

                if(tubes.get(indexToColor).size() == tubeCapacity)
                    done[i] = true;
            }
        }

    }

    private int findIndexToColor(){
        int tubeIndex = -1;
        forLoop:
        for(int i = 0; i < tubesNum; i++){
            if(tubes.get(i).empty()){
                tubeIndex = i;
                break forLoop;
            }

        }
        if(tubeIndex == -1){
            tubeIndex = emptyColumn();
        }
        return tubeIndex;
    }
    private int indexWithColor(int color, int indexToColor){
        int index = -1;
        int min = -1;
        for(int i = 0; i < tubesNum; i++){
            if(tubes.get(i).contains(color) && min == -1 && i != indexToColor) {
                index = i;
                min = tubes.get(i).size();
            }else if(tubes.get(i).contains(color) && min > tubes.get(i).size() && i != indexToColor){
                index = i;
                min = tubes.get(i).size();
            }
        }

        return index;
    }
    private int emptyColumn(){
        int min = tubes.get(0).size();
        int index = 0;
        for(int i = 1; i < tubesNum; i++){
            if(min > tubes.get(i).size()){
                min = tubes.get(i).size();
                index = i ;
            }
        }
        for(int i = 0; i < min; i ++){
            int j = 0;
            boolean cycle = false;
            while(!cycle){
                if(j != index && tubes.get(j).size() != tubeCapacity){
                    tubes.get(j).push(tubes.get(index).pop());
                    moves.add("t" + index + " - t" + j);
                    cycle = true;
                }
                j++;
            }
        }
        return index;
    }

    public void printMoves(){
        for(String move : moves)
            System.out.println(move);
    }
    public void printTubes() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("_".repeat(tubes.size()))
                .append("\n");
        for (int i = tubeCapacity - 1; i >= 0; i--) {
            for (int j = 0; j < tubesNum; j++) {
                final Stack<Integer> currentTube = tubes.get(j);
                stringBuilder.append(i >= currentTube.size() ? " " :  currentTube.get(i));
            }
            stringBuilder.append("\n");
        }
        stringBuilder
                .append("_".repeat(tubes.size()))
                .append("\n");
        for (int i = 0; i < tubesNum; i++)
            stringBuilder.append(i);
        stringBuilder.append("\n");
        System.out.println(stringBuilder.toString());
    }
}
