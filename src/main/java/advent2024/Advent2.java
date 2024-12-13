package advent2024;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Advent2 {
    public static void main(String[] args) throws FileNotFoundException {

//        Scanner scanner = new Scanner(new File(advent2024.Advent2.class.getResource("/advent_2_example.txt").getPath()));
        Scanner scanner = new Scanner(new File(Advent2.class.getResource("/advent_2_input.txt").getPath()));
        int safeReports = 0;
        while (scanner.hasNext()) {
            Report report = new Report(scanner.nextLine());
            if (report.isSafe()) {
                safeReports++;
//                System.out.println(report + " - safe");
            } else {
                for(int i=0;i<report.levels.size();i++){
                    if(new Report(report, i).isSafe()){
                        safeReports++;
                        break;
                    }
                }
                System.out.println(report + " - unsafe");
            }
        }
        System.out.println(safeReports);
    }

    static class Report {
        private final List<Integer> levels;

        Report(String levelString) {
            String[] split = levelString.split(" ");
            this.levels = Arrays.stream(split).map(Integer::valueOf).collect(Collectors.toList());
        }

        public Report(Report report, int removeIndex) {
            this.levels = new ArrayList<>();
            for (int i = 0; i < report.levels.size(); i++) {
                if (i != removeIndex) {
                    this.levels.add(report.levels.get(i));
                }
            }
        }

        public boolean isSafe() {
            return isSafe(true);
        }

        public boolean isSafe(boolean problemDamper) {
//            System.out.println("Testing "+this);
            boolean ascending = levels.get(0) <= levels.get(1);
            for (int i = 0; i < levels.size() - 1; i++) {
                if ((levels.get(i) < levels.get(i + 1)) != ascending) {
//                    if (problemDamper) {
//                        return new Report(this, i+1).isSafe(false) || new Report(this, i).isSafe(false);
//                    } else {
                        return false; // direction changed
//                    }
                }
                int diff = Math.abs(levels.get(i) - levels.get(i + 1));
                if (diff < 1 || diff > 3) {
//                    if (problemDamper) {
//                        return new Report(this, i+1).isSafe(false) || new Report(this, i).isSafe(false);
//                    } else {
                        return false; // too big diff
//                    }
                }
            }
            return true;
        }

        @Override
        public String toString() {
            return "Report{" +
                    "levels=" + levels +
                    '}';
        }
    }


}
