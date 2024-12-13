package advent2024;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Advent3 {
    public static void main(String[] args) throws FileNotFoundException {

//        Scanner scanner = new Scanner(new File(advent2024.Advent2.class.getResource("/advent_3_example.txt").getPath()));
        Scanner scanner = new Scanner(new File(Advent3.class.getResource("/advent_3_input.txt").getPath()));
        StringBuilder input = new StringBuilder();
        while (scanner.hasNext()) {
            input.append(scanner.nextLine());
        }
        String inputString = input.toString();
        Matcher matcher = Pattern.compile("mul\\(\\d{1,3},\\d{1,3}\\)").matcher(inputString);
        Matcher dontMatcher = Pattern.compile("don't\\(\\)").matcher(inputString);
        Matcher doMatcher = Pattern.compile("do\\(\\)").matcher(inputString);
        long result = 0;
        boolean hasDont = dontMatcher.find();
        boolean hasDo = doMatcher.find();
        boolean enabled = true;
        while (matcher.find()) {
            int lastDo = -1;
            int lastDont = -1;
            while (hasDo && doMatcher.start() < matcher.start()) {
                lastDo = doMatcher.start();
                hasDo = doMatcher.find();
            }
            while (hasDont && dontMatcher.start() < matcher.start()) {
                lastDont = dontMatcher.start();
                hasDont = dontMatcher.find();
            }
            if (lastDont != -1 || lastDo != -1) {
                enabled = lastDont <= lastDo;
            }
            if (enabled) {
                String mul = matcher.group();
                long mulResult = mul(mul);
                System.out.println(mul + " = " + mulResult);
                result += mulResult;
            }
        }
        System.out.println(result);

    }

    private static long mul(String mul) {
        return Arrays.stream(mul.substring(4, mul.length() - 1).split(","))
                .mapToInt(Integer::valueOf)
                .reduce(1, (a, b) -> a * b);
    }


}
