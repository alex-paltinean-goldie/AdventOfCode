package advent2024;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Advent1 {
    public static void main(String[] args) throws FileNotFoundException {

        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();
        Scanner scanner = new Scanner(new File(Advent1.class.getResource("/advent_1_input.txt").getPath()));
//        Scanner scanner = new Scanner(new File(advent2024.Advent1.class.getResource("/advent_1_example.txt").getPath()));
        while (scanner.hasNext()){
            String[] split = scanner.nextLine().split("   ");
            list1.add(Integer.valueOf(split[0]));
            list2.add(Integer.valueOf(split[1]));
        }
//        System.out.println(listDiff(list1, list2));
        System.out.println(similarity(list1, list2));
    }

    private static long similarity(List<Integer> list1, List<Integer> list2) {
        long similarity = 0;
        for (Integer i : list1) {
            long multiplier = 0;
            for (Integer j : list2) {
                if (Objects.equals(i, j)) {
                    multiplier++;
                }
            }
            similarity += i * multiplier;
        }
        return similarity;
    }

    private static int listDiff(List<Integer> list1, List<Integer> list2){
        List<Integer> ordered1 = list1.stream().sorted().toList();
        List<Integer> ordered2 = list2.stream().sorted().toList();
        int diff = 0;
        for(int i=0;i<ordered1.size();i++){
            diff += Math.abs(ordered1.get(i)-ordered2.get(i));
        }
        return diff;
    }
}
