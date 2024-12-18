package advent2024;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Advent17 {
    public static void main(String[] args) throws FileNotFoundException {
//        Scanner scanner = new Scanner(new File(advent2024.Advent2.class.getResource("/advent_17_example_2.txt").getPath()));
        Scanner scanner = new Scanner(new File(Advent15.class.getResource("/advent_17_input.txt").getPath()));

        int A = Integer.parseInt(scanner.nextLine().substring(12));
        int B = Integer.parseInt(scanner.nextLine().substring(12));
        int C = Integer.parseInt(scanner.nextLine().substring(12));
        scanner.nextLine();
        String inputInstructions = scanner.nextLine().substring(9);
        int[] program = Arrays.stream(inputInstructions.split(",")).mapToInt(Integer::parseInt).toArray();

        int percentage = 0;
        long iterations = 100_000_000_000L;
        long startingFrom = 20_000_000_000L;
        for (long i = startingFrom; i < iterations; i++) {
            if ((i * 100 / iterations) > percentage) {
                System.out.println(++percentage + "%");
            }
            ThreeBitComputer computer = new ThreeBitComputer((int) i, B, C, program);
            computer.run();
            if (computer.getOutput().equals(inputInstructions)) {
                System.out.println(computer.getOutput());
                break;
            }
        }
    }

    public static class ThreeBitComputer {
        private int registerA;
        private int registerB;
        private int registerC;
        private int instructionPointer = 0;
        private int[] instructions;
        private List<Integer> outputValues = new ArrayList<>();

        public ThreeBitComputer(int registerA, int registerB, int registerC, int[] instructions) {
            this.registerA = registerA;
            this.registerB = registerB;
            this.registerC = registerC;
            this.instructions = instructions;
        }

        private int comboValue(int operand) {
            // combo operand:
            // 0-3: literal values 0..3
            // 4: value in A
            // 5: value in B
            // 6: value in C
            // 7: reserved (won't appear)
            switch (operand) {
                case 0:
                    return 0;
                case 1:
                    return 1;
                case 2:
                    return 2;
                case 3:
                    return 3;
                case 4:
                    return registerA;
                case 5:
                    return registerB;
                case 6:
                    return registerC;
                default:
                    // operand =7 should not appear in valid program
                    throw new IllegalStateException("Invalid combo operand: 7");
            }
        }

        private int divisionResult(int operand) {
            // calculate denominator as 2^(comboValue)
            int val = comboValue(operand);
            long denominator = 1L << val; // if we shift to left, it same as 2^val
            long res = registerA / denominator;
            return (int) res;

        }

        public void run() {
            while (true) {
                if (instructionPointer >= instructions.length) {
                    break;
                }
                int opcode = instructions[instructionPointer];
                // Check if we have an operand
                if (instructionPointer + 1 >= instructions.length) {
                    // No operand means we should halt as well
                    break;
                }
                int operand = instructions[instructionPointer + 1];

                boolean hasJumped = false;

                switch (opcode) {
                    case 0: // adv: A = A / 2^(comboVal(operand))
                        registerA = divisionResult(operand);
                        break;
                    case 1: // bxl: B = B XOR literal operand
                        registerB = registerB ^ operand;
                        break;
                    case 2: // bst: B = comboVal(operand) % 8
                        registerB = comboValue(operand) & 0x7;  // %8 is same as &0x7
                        break;
                    case 3: // jnz: if A != 0, IP = literal operand
                        if (registerA != 0) {
                            instructionPointer = operand;
                            hasJumped = true;
                        }
                        break;
                    case 4: // bxc: B = B XOR C (operand ignored)
                        registerB = registerB ^ registerC;
                        break;
                    case 5: // out: output comboVal(operand)%8
                        int valueToOutput = comboValue(operand) & 0x7;
                        if (outputValues.size() == instructions.length || instructions[outputValues.size()] != valueToOutput) {
                            return;
                        }
                        outputValues.add(valueToOutput);
                        break;
                    case 6: // bdv: B = A / 2^(comboVal(operand))
                        registerB = divisionResult(operand);
                        break;
                    case 7: // cdv: C = A / 2^(comboVal(operand))
                        registerC = divisionResult(operand);
                        break;
                    default:
                        // Invalid opcode - halt
                        return;
                }

                if (!hasJumped) {
                    instructionPointer += 2; // Move to next instruction
                }
            }
        }

        public String getOutput() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < outputValues.size(); i++) {
                if (i > 0) sb.append(",");
                sb.append(outputValues.get(i));
            }
            return sb.toString();
        }


    }

}
