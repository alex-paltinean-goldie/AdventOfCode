package advent2024;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Advent11_2 {


    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(advent2024.Advent2.class.getResource("/advent_11_example.txt").getPath()));
//        Scanner scanner = new Scanner(new File(Advent11_2.class.getResource("/advent_11_input.txt").getPath()));
        long max = 0;
        List<Long> list = Arrays.stream(scanner.nextLine().split(" ")).map(Long::valueOf).toList();
        List<Long> finalList = new LinkedList<>(list);
        int blinkCount = 75;
        for (int blink = 1; blink <= blinkCount; blink++) {
            ListIterator<Long> iterator = finalList.listIterator();
            while (iterator.hasNext()) {
                Long number = iterator.next();
                if (number == 0) {
                    iterator.remove();
                    finalList.add(iterator.nextIndex(), 1L);
                    iterator.next();
                } else {
                    int length = (int) Math.log10(number) + 1;
                    if (length % 2 == 0) {
                        iterator.remove();
                        finalList.add(iterator.nextIndex(), (long) (number / (Math.pow(10, length / 2))));
                        iterator.next();
                        finalList.add(iterator.nextIndex(), (long) (number % (Math.pow(10, length / 2))));
                        iterator.next();
                    } else {
                        long e = number * 2024;
                        iterator.remove();
                        finalList.add(iterator.nextIndex(), e);
                        iterator.next();
                        if (e > max) {
                            max = e;
                        }
                    }
                }
            }
            System.out.println("After " + blink + " blinks:");
            System.out.println(list.size());
            System.out.println("Max: " + max);
//            System.out.println(list);
//            System.out.println();
        }
        System.out.println(list.size());
    }
}
