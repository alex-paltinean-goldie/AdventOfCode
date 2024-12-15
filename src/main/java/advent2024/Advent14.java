package advent2024;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Advent14 {
    private static int maxX = 100;
    private static int maxY = 102;
    private static double distanceDiff = Double.MAX_VALUE;

    public static void main(String[] args) throws FileNotFoundException {
//        Scanner scanner = new Scanner(new File(advent2024.Advent2.class.getResource("/advent_14_example.txt").getPath()));
        Scanner scanner = new Scanner(new File(Advent14.class.getResource("/advent_14_alternate_input.txt").getPath()));
        List<Robot> robots = new ArrayList<>();
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            robots.add(new Robot(line));
        }
        printRobotMatrix(robots, true);
        for (int second = 0; second < 10000; second++) {
            robots.forEach(Robot::move);
            if (isAverageToThreePointsSmall(robots)) {
                System.out.println("Second " + (second + 1));
                printRobotMatrix(robots, true);
            }
        }
//        this is for part 1
//        printRobotMatrix(robots, false);
//        System.out.println("Risk: " + computeRisk(robots))
    }

    public static int computeRisk(List<Robot> robots) {
        int leftTop = 0;
        int rightTop = 0;
        int leftBottom = 0;
        int rightBottom = 0;
        for (Robot robot : robots) {
            if (robot.x == (maxX / 2) || robot.y == (maxY / 2)) {
                continue;
            }
            if (robot.x < (maxX / 2)) {
                if (robot.y < (maxY / 2)) {
                    leftTop++;
                } else {
                    leftBottom++;
                }
            } else {
                if (robot.y < (maxY / 2)) {
                    rightTop++;
                } else {
                    rightBottom++;
                }
            }
        }
        return leftTop * leftBottom * rightTop * rightBottom;
    }

    public static void printRobotMatrix(List<Robot> robots, boolean printMiddle) {
        // Create the matrix and populate it
        int[][] grid = new int[maxY + 1][maxX + 1];
        for (Robot robot : robots) {
            grid[robot.getY()][robot.getX()]++;
        }

        // Print the matrix
        System.out.println("Robot Matrix:");
        for (int y = 0; y <= maxY; y++) {
            for (int x = 0; x <= maxX; x++) {
                if (!printMiddle && (x == (maxX / 2) || y == (maxY / 2))) {
                    System.out.print(" ");
                    continue;
                }
                System.out.print(grid[y][x] != 0 ? grid[y][x] : ".");
            }
            System.out.println();
        }
    }

    public static boolean isAverageToThreePointsSmall(List<Robot> robots) {
        // Pick three points to measure distance to
        int topX = maxX / 2, topY = 0;             // Top vertex (middle of the top line)
        int leftX = maxX / 3, leftY = maxY;        // Left bottom vertex (one-third from bottom-left corner)
        int rightX = 2 * maxX / 3, rightY = maxY;  // Right bottom vertex (two-thirds from bottom-right corner)
        List<Integer> distanceTop = new ArrayList<>();
        List<Integer> distanceLeft = new ArrayList<>();
        List<Integer> distanceRight = new ArrayList<>();

        for (Robot robot : robots) {
            distanceTop.add(distance(robot.x, robot.y, topX, topY));
            distanceLeft.add(distance(robot.x, robot.y, leftX, leftY));
            distanceRight.add(distance(robot.x, robot.y, rightX, rightY));
        }
        double leftAverage = distanceLeft.stream().mapToInt(Integer::intValue).average().getAsDouble();
        double rightAverage = distanceRight.stream().mapToInt(Integer::intValue).average().getAsDouble();
        double topAverage = distanceTop.stream().mapToInt(Integer::intValue).average().getAsDouble();
        double leftDiff = 0;
        double rightDiff = 0;
        double topDiff = 0;
        for (int i = 0; i < distanceTop.size(); i++) {
            leftDiff += Math.abs(distanceLeft.get(i) - leftAverage);
            rightDiff += Math.abs(distanceRight.get(i) - rightAverage);
            topDiff += Math.abs(distanceTop.get(i) - topAverage);
        }
        double totalDistance = leftDiff + rightDiff + topDiff;
        boolean b = totalDistance < distanceDiff;
        if (b) {
            distanceDiff = totalDistance;
            System.out.println(totalDistance);
        }
        return b;
    }

    public static int distance(int x, int y, int destX, int destY) {
        return Math.abs(x - destX) + Math.abs(y - destY);
    }

    public static class Robot {
        int x, y, vX, vY;

        public Robot(String line) {
            String[] split1 = line.split(" ");
            String[] positionString = split1[0].substring(2).split(",");
            x = Integer.parseInt(positionString[0]);
            y = Integer.parseInt(positionString[1]);
            String[] velocityString = split1[1].substring(2).split(",");
            vX = Integer.parseInt(velocityString[0]);
            vY = Integer.parseInt(velocityString[1]);
        }

        public void move() {
            x = (x + vX + maxX + 1) % (maxX + 1);
            y = (y + vY + maxY + 1) % (maxY + 1);
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }
}
