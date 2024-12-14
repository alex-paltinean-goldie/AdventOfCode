package advent2024;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Advent14 {
    private static int maxX = 100;
    private static int maxY = 102;

    public static void main(String[] args) throws FileNotFoundException {
//        Scanner scanner = new Scanner(new File(advent2024.Advent2.class.getResource("/advent_14_example.txt").getPath()));
        Scanner scanner = new Scanner(new File(Advent14.class.getResource("/advent_14_input.txt").getPath()));
        List<Robot> robots = new ArrayList<>();
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            robots.add(new Robot(line));
        }
        printRobotMatrix(robots, true);
        for (int second = 0; second < 10000; second++) {
            robots.forEach(Robot::move);
            if (areMostRobotsInTriangle(robots)) {
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

    // this was generated with the following prompt "a method receiving a list of Robot that checks if the most of the robots are inside a triangle with one angle in the middle of the top line and the other two on third from each bottom corner"
    public static boolean areMostRobotsInTriangle(List<Robot> robots) {
        // Define the triangle vertices
        int topX = maxX / 2, topY = 0;             // Top vertex (middle of the top line)
        int leftX = maxX / 3, leftY = maxY;        // Left bottom vertex (one-third from bottom-left corner)
        int rightX = 2 * maxX / 3, rightY = maxY;  // Right bottom vertex (two-thirds from bottom-right corner)

        // Check if a point is inside the triangle using the barycentric method
        int robotsInside = 0;
        for (Robot robot : robots) {
            if (isPointInsideTriangle(robot.getX(), robot.getY(),
                    topX, topY, leftX, leftY, rightX, rightY)) {
                robotsInside++;
            }
        }

        // Check if most robots are inside the triangle
        return robotsInside > robots.size() / 2;
    }

    private static boolean isPointInsideTriangle(int px, int py,
                                                 int ax, int ay,
                                                 int bx, int by,
                                                 int cx, int cy) {
        // Calculate the areas
        double areaOrig = triangleArea(ax, ay, bx, by, cx, cy);
        double area1 = triangleArea(px, py, bx, by, cx, cy);
        double area2 = triangleArea(ax, ay, px, py, cx, cy);
        double area3 = triangleArea(ax, ay, bx, by, px, py);

        // Check if the sum of sub-areas equals the original area
        return Math.abs(areaOrig - (area1 + area2 + area3)) < 1e-6;
    }

    private static double triangleArea(int x1, int y1, int x2, int y2, int x3, int y3) {
        return Math.abs((x1 * (y2 - y3) + x2 * (y3 - y1) + x3 * (y1 - y2)) / 2.0);
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
