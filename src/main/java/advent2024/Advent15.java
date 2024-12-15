package advent2024;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Advent15 {
    static char[][] grid;
    static int x, y;

    public static void main(String[] args) throws FileNotFoundException {
//        Scanner scanner = new Scanner(new File(advent2024.Advent2.class.getResource("/advent_15_example.txt").getPath()));
        Scanner scanner = new Scanner(new File(Advent15.class.getResource("/advent_15_input.txt").getPath()));
        List<String> gridLines = new ArrayList<>();
        while (true) {
            String line = scanner.nextLine();
            if (line.isEmpty()) {
                break;
            }
            if (line.indexOf("@") != -1) {
                y = gridLines.size();
                x = line.indexOf("@");
            }
            gridLines.add(line);
        }

        // Parse grid into a 2D char array
        grid = new char[gridLines.size()][];
        for (int i = 0; i < gridLines.size(); i++) {
            grid[i] = gridLines.get(i).toCharArray();
        }

        // Read the direction string
        String directions = "";
        while (scanner.hasNext()) {
            directions += scanner.nextLine();
        }

        // Parse directions into a List of Enums
        List<Direction> directionList = new ArrayList<>();
        for (char c : directions.toCharArray()) {
            directionList.add(Direction.fromChar(c));
        }
        printArray(grid);
        for (Direction direction : directionList) {
            move(direction);
        }
        printArray(grid);
        System.out.println(distance());
    }

    public static int distance() {
        int distance = 0;
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[0].length; x++) {
                if (grid[y][x] == 'O') {
                    distance += 100 * y + x;
                }
            }
        }
        return distance;
    }

    public static void move(Direction direction) {
        boolean canMove = false;
        List<Integer> oldX = new ArrayList<>();
        List<Integer> newX = new ArrayList<>();
        List<Integer> oldY = new ArrayList<>();
        List<Integer> newY = new ArrayList<>();
        grid[y][x] = '.';
        switch (direction) {
            case UP -> {
                for (int i = y - 1; i > 0; i--) {
                    if (grid[i][x] == '#') {
                        break;
                    }
                    if (grid[i][x] == '.') {
                        canMove = true;
                        break;
                    }
                    oldX.add(x);
                    oldY.add(i);
                    newX.add(x);
                    newY.add(i - 1);
                }
                if (canMove) {
                    y--;
                }
            }
            case RIGHT -> {
                for (int i = x + 1; i < grid.length; i++) {
                    if (grid[y][i] == '#') {
                        break;
                    }
                    if (grid[y][i] == '.') {
                        canMove = true;
                        break;
                    }
                    oldX.add(i);
                    oldY.add(y);
                    newX.add(i + 1);
                    newY.add(y);
                }
                if (canMove) {
                    x++;
                }
            }
            case DOWN -> {
                for (int i = y + 1; i < grid.length; i++) {
                    if (grid[i][x] == '#') {
                        break;
                    }
                    if (grid[i][x] == '.') {
                        canMove = true;
                        break;
                    }
                    oldX.add(x);
                    oldY.add(i);
                    newX.add(x);
                    newY.add(i + 1);
                }
                if (canMove) {
                    y++;
                }
            }
            case LEFT -> {
                for (int i = x - 1; i > 0; i--) {
                    if (grid[y][i] == '#') {
                        break;
                    }
                    if (grid[y][i] == '.') {
                        canMove = true;
                        break;
                    }
                    oldX.add(i);
                    oldY.add(y);
                    newX.add(i - 1);
                    newY.add(y);
                }
                if (canMove) {
                    x--;
                }
            }
        }
        if (canMove) {
            for (int i = 0; i < oldX.size(); i++) {
                grid[oldY.get(i)][oldX.get(i)] = '.';
            }
            for (int i = 0; i < newX.size(); i++) {
                grid[newY.get(i)][newX.get(i)] = 'O';
            }
        }
        grid[y][x] = '@';
    }

    public static void printArray(char[][] array) {
        for (char[] subArray : array) {
            for (int i = 0; i < subArray.length; i++) {
                System.out.print(subArray[i]);
            }
            System.out.println();
        }
    }

    enum Direction {
        LEFT('<'),
        RIGHT('>'),
        UP('^'),
        DOWN('v');

        private final char symbol;

        Direction(char symbol) {
            this.symbol = symbol;
        }

        public static Direction fromChar(char c) {
            for (Direction dir : values()) {
                if (dir.symbol == c) {
                    return dir;
                }
            }
            throw new IllegalArgumentException("Invalid direction character: " + c);
        }
    }
}
