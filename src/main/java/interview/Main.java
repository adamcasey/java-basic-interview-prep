package interview;

import java.io.IOException;
import java.util.*;

/**
 * Interactive command-line application demonstrating Java fundamentals
 *
 * Run with: java interview.Main [command] [args...]
 *
 * Commands:
 *   add <name> <age> <gpa>              - Add a student
 *   addgrad <name> <age> <gpa> ...      - Add a graduate student
 *   list                                - List all students
 *   find <name>                         - Find student by name
 *   honors                              - List honor roll students
 *   average                             - Show average GPA
 *   sort                                - Show students sorted by GPA
 *   remove <name>                       - Remove a student
 *   count                               - Show student count
 *   grade <name>                        - Show grade info for student
 *   calc <subcommand>                   - Grade calculator operations
 *   save [filename]                     - Save students to file
 *   load [filename]                     - Load students from file
 *   interactive                         - Enter interactive mode
 */
public class Main {
    private static StudentManager manager = new StudentManager();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Try to load existing data, or add sample data if none exists
        try {
            manager.load();
            System.out.println("Loaded existing student data.");
        } catch (Exception e) {
            // No saved data, add sample data
            manager.addStudent(new Student("Alice", 20, 3.8));
            manager.addStudent(new Student("Bob", 21, 3.2));
            manager.addStudent(new Student("Charlie", 19, 3.9));
            manager.addStudent(new GraduateStudent("Diana", 26, 3.85,
                "Machine Learning in Healthcare", "Dr. Johnson", true));
        }

        if (args.length == 0) {
            System.out.println("No command provided. Starting interactive mode...");
            interactiveMode();
        } else {
            processCommand(args);
        }
    }

    private static void processCommand(String[] args) {
        String command = args[0].toLowerCase();

        try {
            switch (command) {
                case "add":
                    if (args.length != 4) {
                        System.out.println("Usage: add <name> <age> <gpa>");
                        return;
                    }
                    String name = args[1];
                    int age = Integer.parseInt(args[2]);
                    double gpa = Double.parseDouble(args[3]);
                    manager.addStudent(new Student(name, age, gpa));
                    System.out.println("Added: " + name);
                    break;

                case "list":
                    System.out.println("\n=== All Students ===");
                    for (Student s : manager.getAllStudents()) {
                        System.out.println(s);
                    }
                    break;

                case "find":
                    if (args.length != 2) {
                        System.out.println("Usage: find <name>");
                        return;
                    }
                    Student found = manager.findByName(args[1]);
                    if (found != null) {
                        System.out.println("Found: " + found);
                    } else {
                        System.out.println("Student not found: " + args[1]);
                    }
                    break;

                case "honors":
                    System.out.println("\n=== Honor Roll Students (GPA >= 3.5) ===");
                    for (Student s : manager.getHonorRollStudents()) {
                        System.out.println(s);
                    }
                    break;

                case "average":
                    double avg = manager.getAverageGpa();
                    System.out.printf("Average GPA: %.2f\n", avg);
                    break;

                case "sort":
                    System.out.println("\n=== Students Sorted by GPA ===");
                    for (Student s : manager.sortByGpa()) {
                        System.out.println(s);
                    }
                    break;

                case "remove":
                    if (args.length != 2) {
                        System.out.println("Usage: remove <name>");
                        return;
                    }
                    boolean removed = manager.removeStudent(args[1]);
                    if (removed) {
                        System.out.println("Removed: " + args[1]);
                    } else {
                        System.out.println("Student not found: " + args[1]);
                    }
                    break;

                case "count":
                    System.out.println("Total students: " + manager.getCount());
                    break;

                case "addgrad":
                    if (args.length != 7) {
                        System.out.println("Usage: addgrad <name> <age> <gpa> <thesis> <advisor> <isPhD>");
                        return;
                    }
                    String gradName = args[1];
                    int gradAge = Integer.parseInt(args[2]);
                    double gradGpa = Double.parseDouble(args[3]);
                    String thesis = args[4];
                    String advisor = args[5];
                    boolean isPhD = Boolean.parseBoolean(args[6]);
                    manager.addStudent(new GraduateStudent(gradName, gradAge, gradGpa, thesis, advisor, isPhD));
                    System.out.println("Added graduate student: " + gradName);
                    break;

                case "grade":
                    if (args.length != 2) {
                        System.out.println("Usage: grade <name>");
                        return;
                    }
                    Student gradeStudent = manager.findByName(args[1]);
                    if (gradeStudent != null) {
                        System.out.println("\n=== Grade Information for " + gradeStudent.getName() + " ===");
                        System.out.println("GPA: " + gradeStudent.getGpa());
                        System.out.println("Letter Grade: " + gradeStudent.getLetterGrade());
                        System.out.println("Passing: " + (gradeStudent.isPassing() ? "Yes" : "No"));
                        System.out.println("Honor Roll: " + (gradeStudent.isHonorRoll() ? "Yes" : "No"));
                        System.out.println("Academic Standing: " + gradeStudent.getAcademicStanding());
                    } else {
                        System.out.println("Student not found: " + args[1]);
                    }
                    break;

                case "calc":
                    handleCalculatorCommand(args);
                    break;

                case "save":
                    try {
                        if (args.length > 1) {
                            manager.saveToFile(args[1]);
                            System.out.println("Saved to: " + args[1]);
                        } else {
                            manager.save();
                            System.out.println("Saved student data.");
                        }
                    } catch (IOException e) {
                        System.out.println("Error saving: " + e.getMessage());
                    }
                    break;

                case "load":
                    try {
                        if (args.length > 1) {
                            manager.loadFromFile(args[1]);
                            System.out.println("Loaded from: " + args[1]);
                        } else {
                            manager.load();
                            System.out.println("Loaded student data.");
                        }
                    } catch (IOException | ClassNotFoundException e) {
                        System.out.println("Error loading: " + e.getMessage());
                    }
                    break;

                case "interactive":
                    interactiveMode();
                    break;

                default:
                    printHelp();
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid number format");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void interactiveMode() {
        System.out.println("\n=== Student Management System ===");
        System.out.println("Type 'help' for commands, 'exit' to quit\n");

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                continue;
            }

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Goodbye!");
                break;
            }

            if (input.equalsIgnoreCase("help")) {
                printHelp();
                continue;
            }

            String[] args = input.split("\\s+");
            processCommand(args);
        }
    }

    private static void handleCalculatorCommand(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: calc <subcommand> [args...]");
            System.out.println("Subcommands:");
            System.out.println("  letter <percentage>              - Convert percentage to letter grade");
            System.out.println("  gpa <letterGrade>                - Convert letter grade to GPA");
            System.out.println("  required <currentGPA> <currentCredits> <targetGPA> <remainingCredits>");
            return;
        }

        String subcommand = args[1].toLowerCase();

        try {
            switch (subcommand) {
                case "letter":
                    if (args.length != 3) {
                        System.out.println("Usage: calc letter <percentage>");
                        return;
                    }
                    double percentage = Double.parseDouble(args[2]);
                    String letter = GradeCalculator.percentageToLetterGrade(percentage);
                    System.out.println(percentage + "% = " + letter);
                    break;

                case "gpa":
                    if (args.length != 3) {
                        System.out.println("Usage: calc gpa <letterGrade>");
                        return;
                    }
                    double gpaValue = GradeCalculator.letterGradeToGPA(args[2]);
                    System.out.println(args[2] + " = " + gpaValue + " GPA");
                    break;

                case "required":
                    if (args.length != 6) {
                        System.out.println("Usage: calc required <currentGPA> <currentCredits> <targetGPA> <remainingCredits>");
                        return;
                    }
                    double currentGPA = Double.parseDouble(args[2]);
                    int currentCredits = Integer.parseInt(args[3]);
                    double targetGPA = Double.parseDouble(args[4]);
                    int remainingCredits = Integer.parseInt(args[5]);

                    double requiredGPA = GradeCalculator.calculateRequiredGPA(
                        currentGPA, currentCredits, targetGPA, remainingCredits);

                    System.out.printf("To reach %.2f GPA, you need %.2f GPA in remaining %d credits\n",
                        targetGPA, requiredGPA, remainingCredits);

                    if (requiredGPA > 4.0) {
                        System.out.println("WARNING: This is impossible (requires GPA > 4.0)");
                    } else if (requiredGPA < 0.0) {
                        System.out.println("Good news: You can achieve this even with 0.0 in remaining courses!");
                    }
                    break;

                default:
                    System.out.println("Unknown calculator subcommand: " + subcommand);
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid number format");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void printHelp() {
        System.out.println("\n=== Available Commands ===");
        System.out.println("add <name> <age> <gpa>                 - Add a student");
        System.out.println("addgrad <name> <age> <gpa> <thesis> <advisor> <isPhD>");
        System.out.println("                                       - Add a graduate student");
        System.out.println("list                                   - List all students");
        System.out.println("find <name>                            - Find student by name");
        System.out.println("honors                                 - List honor roll students");
        System.out.println("average                                - Show average GPA");
        System.out.println("sort                                   - Show students sorted by GPA");
        System.out.println("remove <name>                          - Remove a student");
        System.out.println("count                                  - Show student count");
        System.out.println("grade <name>                           - Show grade details for student");
        System.out.println("calc <subcommand> [args...]            - Grade calculator operations");
        System.out.println("save [filename]                        - Save students to file");
        System.out.println("load [filename]                        - Load students from file");
        System.out.println("help                                   - Show this help");
        System.out.println("exit                                   - Exit program\n");
    }
}
