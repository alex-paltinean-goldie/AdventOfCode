package advent2024;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Advent12_2 {
    private static char[] letters = new char[]{'X', 'M', 'A', 'S'};
    private static List<List<Character>> matrix = new ArrayList<>();
    private static Map<Integer, Integer> area = new HashMap<>();
    private static Map<Integer, Integer> perimeter = new HashMap<>();
    private static Map<Integer, Integer> sides = new HashMap<>();
    private static List<Integer> firstX = new ArrayList<>();
    private static List<Integer> firstY = new ArrayList<>();
    private static int[][] regions;
    private static int[][] walked;
    private static int maxX;
    private static int maxY = -1;

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(advent2024.Advent2.class.getResource("/advent_12_example.txt").getPath()));
//        Scanner scanner = new Scanner(new File(Advent12_2.class.getResource("/advent_12_input.txt").getPath()));
        StringBuilder input = new StringBuilder();
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            maxX = line.length() - 1;
            matrix.add(Arrays.stream(line.split("")).map(s -> s.charAt(0)).toList());
            maxY++;
        }
        regions = new int[maxY + 1][maxX + 1];
        walked = new int[maxY + 1][maxX + 1];
        int region = 1;
        for (int y = 0; y <= maxY; y++) {
            for (int x = 0; x <= maxX; x++) {
                if (regions[y][x] == 0) {
                    System.out.println("Region " + region + " is " + matrix.get(y).get(x));
                    firstX.add(x);
                    firstY.add(y);
                    process(region++, x, y);
                }
            }
        }
        System.out.println(area);
        System.out.println(perimeter);
        printArray(regions);

        for (int i = 1; i < region; i++) {
            walk(i, firstX.get(i - 1), firstY.get(i - 1), Direction.RIGHT);
        }
        printArray(walked);
        long price = 0;
        for (Map.Entry<Integer, Integer> entry : area.entrySet()) {
            price += entry.getValue() * sides.get(entry.getKey());
        }
        System.out.println(price);

    }

    private static void walk(int region, int x, int y, Direction direction) {
        int newStraightX = x + direction.xDir;
        int newStraightY = y + direction.yDir;
        int newReturnX = x + direction.previous().xDir;
        int newReturnY = y + direction.previous().yDir;
        if (walked[y][x] != 0 && x == firstX.get(region - 1) && y == firstY.get(region - 1)) {
            if(direction==Direction.UP){
                sides.put(region, sides.getOrDefault(region, 2) - 1);
            }
            return;
        }
        if (area.get(region) < 3) {
            sides.put(region, 4);
            return;
        }
        // try to take a left turn. if not, go straight, if not, take a right turn
        if (newReturnX >= 0 && newReturnY >= 0 && newReturnX <= maxX && newReturnY <= maxY && regions[newReturnY][newReturnX] == region) {
            walked[y][x] = region;
            sides.put(region, sides.getOrDefault(region, 2) + 1);
            System.out.printf("Left turn from %d %d to %s. Sides %d %n", y, x, direction.previous().name(), sides.get(region));
            printArray(walked);
            System.out.println("**********");
            walk(region, newReturnX, newReturnY, direction.previous());
        } else if (newStraightX >= 0 && newStraightY >= 0 && newStraightX <= maxX && newStraightY <= maxY && regions[newStraightY][newStraightX] == region) {
            walked[y][x] = region;
            System.out.printf("Walk straight from %d %d to %s%n", y, x, direction.name());
            printArray(walked);
            System.out.println("**********");
            walk(region, newStraightX, newStraightY, direction);
        } else {
            sides.put(region, sides.getOrDefault(region, 2) + 1);
            System.out.printf("Try right turn from %d %d to %s. Sides %d %n", y, x, direction.next().name(), sides.get(region));
            walk(region, x, y, direction.next());
        }
    }

    private static void process(int region, int x, int y) {
        if (regions[y][x] > 0) {
            System.out.println(y + " " + x + " already processed");
            return;
        }
        System.out.println(y + " " + x + " processing");
        regions[y][x] = region;
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

    private enum Direction {
        RIGHT(1, 0), DOWN(0, 1), LEFT(-1, 0), UP(0, -1);
        public int xDir;
        public int yDir;

        Direction(int xDir, int yDir) {
            this.xDir = xDir;
            this.yDir = yDir;
        }

        public Direction next() {
            return switch (this) {
                case RIGHT -> DOWN;
                case DOWN -> LEFT;
                case LEFT -> UP;
                case UP -> RIGHT;
            };
        }

        public Direction previous() {
            return switch (this) {
                case RIGHT -> UP;
                case DOWN -> RIGHT;
                case LEFT -> DOWN;
                case UP -> LEFT;
            };
        }
    }
}
