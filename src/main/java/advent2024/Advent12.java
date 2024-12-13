package advent2024;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Advent12 {
    private static char[] letters = new char[]{'X', 'M', 'A', 'S'};
    private static List<List<Character>> matrix = new ArrayList<>();
    private static Map<Integer, Integer> area = new HashMap<>();
    private static Map<Integer, Integer> perimeter = new HashMap<>();
    private static int[][] processed;
    private static int maxX;
    private static int maxY = -1;

    public static void main(String[] args) throws FileNotFoundException {
//        Scanner scanner = new Scanner(new File(advent2024.Advent2.class.getResource("/advent_12_example.txt").getPath()));
        Scanner scanner = new Scanner(new File(Advent12.class.getResource("/advent_12_input.txt").getPath()));
        StringBuilder input = new StringBuilder();
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            maxX = line.length() - 1;
            matrix.add(Arrays.stream(line.split("")).map(s -> s.charAt(0)).toList());
            maxY++;
        }
        processed = new int[maxY + 1][maxX + 1];
        int region = 1;
        for (int y = 0; y <= maxY; y++) {
            for (int x = 0; x <= maxX; x++) {
                if (processed[y][x] == 0) {
                    System.out.println("Region " + region + " is " + matrix.get(y).get(x));
                    process(region++, x, y);
                }
            }
        }
        System.out.println(area);
        System.out.println(perimeter);
        printArray(processed);
        long price = 0;
        for (Map.Entry<Integer, Integer> entry : area.entrySet()) {
            price += entry.getValue() * perimeter.get(entry.getKey());
        }
        System.out.println(price);

    }

    private static void process(int region, int x, int y) {
        if (processed[y][x] > 0) {
            System.out.println(y + " " + x + " already processed");
            return;
        }
        System.out.println(y + " " + x + " processing");
        processed[y][x] = region;
        Character character = matrix.get(y).get(x);
        area.put(region, area.getOrDefault(region, 0) + 1);
        // up
        if (y - 1 < 0 || !matrix.get(y - 1).get(x).equals(character)) {
            perimeter.put(region, perimeter.getOrDefault(region, 0) + 1);
        } else if (matrix.get(y - 1).get(x).equals(character)) {
            process(region, x, y - 1);
        }
        // down
        if (y + 1 > maxY || !matrix.get(y + 1).get(x).equals(character)) {
            perimeter.put(region, perimeter.getOrDefault(region, 0) + 1);
        } else if (matrix.get(y + 1).get(x).equals(character)) {
            process(region, x, y + 1);
        }
        // right
        if (x + 1 > maxX || !matrix.get(y).get(x + 1).equals(character)) {
            perimeter.put(region, perimeter.getOrDefault(region, 0) + 1);
        } else if (matrix.get(y).get(x + 1).equals(character)) {
            process(region, x + 1, y);
        }
        // up
        if (x - 1 < 0 || !matrix.get(y).get(x - 1).equals(character)) {
            perimeter.put(region, perimeter.getOrDefault(region, 0) + 1);
        } else if (matrix.get(y).get(x - 1).equals(character)) {
            process(region, x - 1, y);
        }
    }

    public static void printArray(int[][] array) {
        for (int[] subArray : array) {
            System.out.print("[");
            for (int i = 0; i < subArray.length; i++) {
                System.out.print(subArray[i]);
                if (i < subArray.length - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println("]");
        }
    }
}
