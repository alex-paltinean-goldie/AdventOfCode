package advent2024;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Advent11 {


    public static void main(String[] args) throws FileNotFoundException {
//        Scanner scanner = new Scanner(new File(advent2024.Advent2.class.getResource("/advent_11_example.txt").getPath()));
//        Scanner scanner = new Scanner(new File(Advent11.class.getResource("/advent_11_input.txt").getPath()));
//        List<Number> list = Arrays.stream(scanner.nextLine().split(" ")).map(Number::new).collect(Collectors.toList());
//
//        int blinkCount = 37;
//        for (int blink = 1; blink <= blinkCount; blink++) {
//            List<Number> newList = new ArrayList<>(list.size() * 2);
//            for (Number number : list) {
//                if (number.value == 0) {
//                    number.value = 1;
//                    newList.add(number);
//                } else {
//                    int length = (int) Math.log10(number.value) + 1;
//                    if (length % 2 == 0) {
//                        newList.add(new Number((long) (number.value / (Math.pow(10, length / 2)))));
//                        number.value = (long) (number.value % (Math.pow(10, length / 2)));
//                        newList.add(number);
//                    } else {
//                        number.value = number.value * 2024;
//                        newList.add(number);
//                    }
//                }
//            }
//            list = newList;
//            System.gc();
//            System.out.println("After " + blink + " blinks:");
//            System.out.println(list.size());
////            System.out.println(list);
////            System.out.println();
//        }
//        System.out.println(list.size());
    }

    private static List<Number> getList(Long inputNumber, Long blinks) {
        List<Number> list = new ArrayList<>();
        list.add(new Number(inputNumber));
        for (int blink = 1; blink <= blinks; blink++) {
            List<Number> newList = new ArrayList<>();
            for (Number number : list) {
                if (number.value == 0) {
                    number.value = 1;
                    newList.add(number);
                } else {
                    int length = (int) Math.log10(number.value) + 1;
                    if (length % 2 == 0) {
                        newList.add(new Number((long) (number.value / (Math.pow(10, length / 2)))));
                        number.value = (long) (number.value % (Math.pow(10, length / 2)));
                        newList.add(number);
                    } else {
                        number.value = number.value * 2024;
                        newList.add(number);
                    }
                }
            }
            list = newList;
            System.gc();
            System.out.println("After " + blink + " blinks:");
            System.out.println(list.size());
//            System.out.println(list);
//            System.out.println();
        }
        return list;
    }

    static class Number {
        public long value;

        public Number(String value) {
            this.value = Long.valueOf(value);
        }

        public Number(long value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "" + value;
        }
    }
}
