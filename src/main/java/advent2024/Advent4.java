package advent2024;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Advent4 {
    private static char[] letters = new char[]{'X', 'M', 'A', 'S'};
    private static List<List<Character>> matrix = new ArrayList<>();
    private static int maxX;
    private static int maxY = -1;

    public static void main(String[] args) throws FileNotFoundException {
//        Scanner scanner = new Scanner(new File(advent2024.Advent2.class.getResource("/advent_4_example.txt").getPath()));
        Scanner scanner = new Scanner(new File(Advent4.class.getResource("/advent_4_input.txt").getPath()));
        StringBuilder input = new StringBuilder();
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            maxX = line.length() - 1;
            matrix.add(Arrays.stream(line.split("")).map(s -> s.charAt(0)).toList());
            maxY++;
        }
        int xmasCount = 0;
        for (int y = 0; y <= maxY; y++) {
            for (int x = 0; x <= maxX; x++) {
                if (matrix.get(y).get(x) == letters[0]) {
                    xmasCount += findLetter(0, x, y, -1, -1) +
                            findLetter(0, x, y, 0, -1) +
                            findLetter(0, x, y, 1, -1) +
                            findLetter(0, x, y, 1, 0) +
                            findLetter(0, x, y, 1, 1) +
                            findLetter(0, x, y, 0, 1) +
                            findLetter(0, x, y, -1, 1) +
                            findLetter(0, x, y, -1, 0);
                }
            }
        }
        System.out.println(xmasCount);

    }

    private static int findLetter(int letterIndex, int x, int y, int xIncrementor, int yIncrementor) {
        if (letterIndex == 4) {
            return 1;
        }
        if (x < 0 || y < 0 || x > maxX || y > maxY) {
            return 0;
        }
        if (matrix.get(y).get(x) != letters[letterIndex]) {
            return 0;
        }
        return findLetter(letterIndex + 1, x + xIncrementor, y + yIncrementor, xIncrementor, yIncrementor);
    }
}
