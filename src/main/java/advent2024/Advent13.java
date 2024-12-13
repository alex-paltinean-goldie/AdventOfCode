package advent2024;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Advent13 {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(advent2024.Advent2.class.getResource("/advent_13_example.txt").getPath()));
//        Scanner scanner = new Scanner(new File(Advent13.class.getResource("/advent_13_input.txt").getPath()));
        int totalTokens = 0;
        while (scanner.hasNext()) {
            Game game = readNextGame(scanner);
            game.play();
            if (game.minTokens != null) {
                totalTokens += game.minTokens;
            }
        }
        System.out.println(totalTokens);
    }

    private static Game readNextGame(Scanner scanner) {
        String buttonA = scanner.nextLine();
        if (buttonA.isEmpty()) {
            buttonA = scanner.nextLine();
        }
        String buttonB = scanner.nextLine();
        String prize = scanner.nextLine();
        Game game = new Game(buttonA, buttonB, prize);
        return game;
    }

    private static class Game {
        long aX, aY, bX, bY, prizeX, prizeY;
        Long minTokens = null;

        Game(String buttonA, String buttonB, String prize) {
            System.out.println(buttonA);
            System.out.println(buttonB);
            System.out.println(prize);
            String[] buttonASplit = buttonA.substring(10).split(", ");
            this.aX = Integer.parseInt(buttonASplit[0].substring(2));
            this.aY = Integer.parseInt(buttonASplit[1].substring(2));
            String[] buttonBSplit = buttonB.substring(10).split(", ");
            this.bX = Integer.parseInt(buttonBSplit[0].substring(2));
            this.bY = Integer.parseInt(buttonBSplit[1].substring(2));
            String[] prizeSplit = prize.substring(7).split(", ");
            this.prizeX = Integer.parseInt(prizeSplit[0].substring(2)) + 10000000000000L;
            this.prizeY = Integer.parseInt(prizeSplit[1].substring(2)) + 10000000000000L;
        }

        public void play() {
            for (long a = 0; a <= Math.min(prizeX / aX, prizeY / aY); a++) {
                System.out.printf("%d A %n", a);
                long remainingX = prizeX - (a * aX);
                long remainingY = prizeY - (a * aY);
                if (remainingX % bX == 0 && remainingY % bY == 0 && remainingX / bX == remainingY / bY) {
                    System.out.printf("%d A %d B %n", a, remainingX / bX);
                    long tokens = a * 3 + (remainingX / bX);
                    if (minTokens == null || tokens < minTokens) {
                        minTokens = tokens;
                    }
                }
            }
        }

    }
}
