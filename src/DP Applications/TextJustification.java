package DynamicProgrammingExamples;

import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class TextJustification {

    public static int lim = 50;
    static String[] word_list = null;
    static int[][] costLine = null;
    static int[] line_tracker = null;
    static int[] memoi_graph = null;
    static int line_index = 0;
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        String unformatted_text = scanner.nextLine();

        System.out.println(justifyText(unformatted_text));


    }


    public static String justifyText(String unformatted_text) {
        word_list = unformatted_text.split(" ");
        line_tracker = new int[word_list.length];
        line_tracker[0] = 0;
        memoi_graph = new int[word_list.length];
        justifier(0);
        String sol = "";
        int prev = 0;
        for(int w=0; w<line_tracker.length;){
           sol += Arrays.stream(Arrays.copyOfRange(word_list, prev, line_tracker[w])).collect(Collectors.joining(" "))+"\n";
           w = line_tracker[w];
           prev = w;
        }
        return sol;
    }

    private static int justifier(int i){

        if(memoi_graph[i] != 0){
            return memoi_graph[i];
        }
        line_index++;

        int bc=0, bcf = 0;
        int minBC = Integer.MAX_VALUE;
        for(int j=i+1; ; j++) {
            bcf = badCalc(i,j);
            if(bcf==-1)
                break;

            if(j==line_tracker.length) {
                minBC = 1;
                line_tracker[i] = j;
                memoi_graph[i] = minBC;
                break;
            }
            if(justifier(j) == Integer.MAX_VALUE)
                continue;
            bc = bcf + justifier(j);
            if(minBC > bc){
                minBC = bc;
                line_tracker[i] = j;
                memoi_graph[i] = minBC;
            }
        }
        line_index--;
        return minBC;
    }

    private static int badCalc(int from, int to){
        String elval = Arrays.stream(Arrays.copyOfRange(word_list, from, to)).collect(Collectors.joining());
        int spaces = to - from -1;
        if(elval.length() + spaces > lim)
            return -1;
        else return (lim - elval.length() + spaces)*(lim - elval.length() + spaces)*(lim - elval.length() + spaces);
    }
}
