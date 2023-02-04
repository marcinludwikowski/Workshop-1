package pl.coderslab;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TaskManager {

    static final String FILE_NAME = "tasks.csv";
    static final String[] OPTIONS = {"add", "remove", "list", "exit"};
    static String[][] tasks;

    public static void printOptions(String[] array) {
        System.out.println(ConsoleColors.BLUE);
        System.out.println("Please select an option: " + ConsoleColors.RESET);
        for (String option : array) {
            System.out.println(option);
        }
    }

    public static void main(String[] args) {
        try {
            tasks = loadDataToArray(FILE_NAME);
        } catch (FileNotFoundException e) {
            System.out.println("File not exist.");
            System.exit(0);
        }
        printOptions(OPTIONS);
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();

            switch (input) {
                case "list":
                    printArray(tasks);
                    break;
                case "add":
                    addtask();
                    break;
                case "remove":
                    removeTask(tasks, getTheNumber());
                    break;
                case "exit":
                    saveArrayToFile(FILE_NAME, tasks);
                    System.out.println(ConsoleColors.RED + "Bye, bye.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Please select a correct option.");
            }

            printOptions(OPTIONS);
        }
    }

    public static boolean isNumberGreaterEqualZero(String input) {
        if (NumberUtils.isParsable(input)) {
            return Integer.parseInt(input) >= 0;
        }
        return false;
    }

    public static int getTheNumber() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please select number to remove");
        String n = scanner.nextLine();
        while (!isNumberGreaterEqualZero(n)) {
            System.out.println("Incorrect argument passed. Please give number greater or equal 0");
            n = scanner.nextLine();
        }
        return Integer.parseInt(n);
    }

    private static void removeTask(String[][] array, int index) {
        try {
            tasks = ArrayUtils.remove(array, index);
            System.out.println("Value was successfully deleted.");
        } catch (Exception ex) {
            System.out.println("Element not exist in tab.");
            removeTask(tasks, getTheNumber());
        }
    }

    public static void printArray(String[][] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.print(i + " : ");
            for (int j = 0; j < array[i].length; j++) {
                System.out.print(array[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static void addtask() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please add task description");
        String description = scanner.nextLine();
        System.out.println("Please add task due date");
        String dueDate = scanner.nextLine();
        System.out.println("Is your task important: true/false");
        String isImportant = scanner.nextLine();
        tasks = Arrays.copyOf(tasks, tasks.length + 1);
        tasks[tasks.length - 1] = new String[3];
        tasks[tasks.length - 1][0] = description;
        tasks[tasks.length - 1][1] = dueDate;
        tasks[tasks.length - 1][2] = isImportant;
    }

    public static String[][] loadDataToArray(String fileName) throws FileNotFoundException {
        String[][] array = new String[0][0];
        File file = new File(fileName);
        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()) {
            array = Arrays.copyOf(array, array.length + 1);
            String line = scanner.nextLine();
            array[array.length - 1] = line.split(",");
        }
        return array;
    }

    public static void saveArrayToFile(String fileName, String[][] array) {
        StringBuilder sb = new StringBuilder();

        for (String[] strings : array) {
            sb.append(strings[0]);
            for (int j = 1; j < strings.length; j++) {
                sb.append(", ").append(strings[j]);
            }
            sb.append("\n");
        }
        File file = new File(fileName);
        try {
            Files.writeString(file.toPath(), sb.toString());
        } catch (IOException e) {
            System.out.println("File saving problem.");
        }
    }
}


