package pl.coderslab;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Scanner;


public class TaskManager {

  public static void main(String[] args) {
    /*String[][] inputArray = {
        {"Simple task - very important", "2020-03-09", "true"},
        {"Second task not so important", "2020-05-10", "false"},
        {"Throw away trash", "2020-03-09", "false"},
        {"Bardzo skomplikowany task który posida więcej niż kilka wyrazów", "2023-12-14", "true"}};
    inputArray = removeElementFromArray(inputArray,3);
    for (String[] row: inputArray) {
      System.out.println(Arrays.toString(row));

    }*/
    runProgram();

  }

  public static void runProgram() {
    String[][] taskDataBase;
    String filePath = "src/main/java/pl/coderslab/tasks.csv";
    try {
      taskDataBase = getTaskManagerDataBase(filePath);
      taskDataBase = showUIOptions(taskDataBase);
      exitProgram(taskDataBase,filePath);
    } catch (IOException ex) {
      System.out.println("Problem during dataBase reading" + ex.getMessage());
    }

  }

  private static String[][] showUIOptions(String[][] dataBase) {
    String[][] taskManagerDataBase = dataBase;
    String[] uiMenuOptions = {"add", "remove", "list", "exit"};
    String prompt = "\nPlease select an option: ";
    String scannerInput;
    Scanner scanner = new Scanner(System.in);
    boolean terminateProgram = false;

    while (!terminateProgram) {

      System.out.println(ConsoleColors.BLUE + prompt + ConsoleColors.RESET);
      for (String option : uiMenuOptions
      ) {
        System.out.print(option + "   ");
      }
      scannerInput = scanner.nextLine().toLowerCase().trim();

      switch (scannerInput) {
        case "add":
          taskManagerDataBase=resizeArray(taskManagerDataBase);
          taskManagerDataBase[taskManagerDataBase.length-1] = addTask(scanner);
          break;

        case "remove":
          String[][] newDatabase = removeTask(taskManagerDataBase,scanner);
          if(newDatabase!= null) taskManagerDataBase=newDatabase;
          break;

        case "list":
          showTaskList(taskManagerDataBase);
          break;

        case "exit":
          terminateProgram = true;
          break;

        default:
          System.out.println(
              ConsoleColors.RED +
                  "No such option. Please try again." +
                  ConsoleColors.RESET);
      }
      if (terminateProgram) {
        System.out.println("Exiting program!");

      }
    }
    return taskManagerDataBase;
  }

  private static String[][] resizeArray(String[][] arrayToResize) {
    return Arrays.copyOf(arrayToResize, arrayToResize.length+1);
  }

  private static String[][] getTaskManagerDataBase(String file) throws IOException {
    String[][] taskManagerDataBase = new String[0][0];
    int arraySizeCounter = 0;
    Path filePath = Paths.get(file);
    if (Files.exists(filePath)) {
      try (Scanner fileScanner = new Scanner(filePath)) {
        while (fileScanner.hasNextLine()) {
          String inputLine = fileScanner.nextLine().trim();
          if (arraySizeCounter == taskManagerDataBase.length) {
            taskManagerDataBase = resizeArray(taskManagerDataBase);
          }
          String[] task = inputLine.split(",");
          if (task.length != 3) {
            System.out.println(
                ConsoleColors.RED +
                    "Task corrupted while reading database" +
                    ConsoleColors.RESET);
          } else {
            taskManagerDataBase[arraySizeCounter] = task;
            arraySizeCounter++;
          }
        }
      }
    } else
      throw new IOException("The file does not exist");
    return taskManagerDataBase;
  }

  private static void showTaskList(String[][] dataBase) {
      int firstColumnMaxWidth = 30;
   // System.out.printf("%-" + firstColumnMaxWidth + "s", line.substring(startIndex, endIndex));
    for (int i = 0; i < dataBase.length; i++) {
      String[] task = dataBase[i];
      System.out.print(i + " : ");
      for (String element : task) {
        if (element != null) {
          printWrappedLine(element, firstColumnMaxWidth,i);
        }
      }
      System.out.println();
    }
  }

  private static void printWrappedLine(String line, int columnWidth, int lineNumber ) {
    int timesToPrint = (int) Math.ceil((double) line.length()/columnWidth);
    for(int i = 0; i< timesToPrint; i++){
      int startIndex = i*columnWidth;
      int endIndex = Math.min((i+1)*columnWidth,line.length());
      String wrapperLogic =line.substring(startIndex, endIndex);

      if(i == 0){
        System.out.printf("%-" + columnWidth + "s", wrapperLogic);
      }
      else if(i == timesToPrint-1 ){
              System.out.printf("%-" + (columnWidth+4) + "s",lineNumber + " : " + wrapperLogic);
      }
      else System.out.printf("\n"+"%-" + columnWidth + "s%n",lineNumber + " : " + wrapperLogic);
      }
    }
    private static Integer tryParse(String value){
      try { return Integer.parseInt(value);
      }
      catch (NumberFormatException e) {return null;}
    }
    private static String[][] removeElementFromArray(String[][] inputArray, int indexToRemove){
      String[][] newArray = new String[inputArray.length-1][];
      System.arraycopy(inputArray,0, newArray, 0,indexToRemove);
      System.arraycopy(inputArray,indexToRemove+1,newArray,indexToRemove, inputArray.length - indexToRemove-1);
      return newArray;
    }

  private static String[][] removeTask(String[][] dataBase, Scanner scanner){
    String prompt = "Provide the task number to delete or type quit";
      while(true)
      {
        System.out.println(ConsoleColors.BLUE + prompt + ConsoleColors.RESET);
        if(scanner.hasNextInt()){
          int taskNumber = scanner.nextInt();
          if(taskNumber< dataBase.length && taskNumber > 0){
            dataBase = removeElementFromArray(dataBase,taskNumber-1);
            System.out.printf("Task number %d has been deleted",taskNumber);
            break;
          }
          else System.out.println("No such number, try again or type quit");
        }
        else {
          String userInput = scanner.nextLine().toLowerCase().trim();
          if (userInput.equals("quit")) {
            return null;
          } else {
            System.out.println("Wrong input. Try again.");
          }
        }

      }
      return dataBase;
  }
  public static String[] addTask(Scanner scanner){
    String[] task = new String[3];
    while(true) {
      System.out.println("Describe your task");
      task[0] = scanner.nextLine();
      System.out.println("Provide the deadline for task YEAR-DAY-MONTH");
      task[1] = scanner.nextLine();
      System.out.println("Is the task important true/false");
      task[2] = scanner.nextLine();
      return task;

    }
  }
  public static void exitProgram(String[][] dataBase, String file) throws IOException{
    Path filePath = Paths.get(file);

    if(Files.exists(filePath)){
      Files.writeString(filePath,"",StandardOpenOption.TRUNCATE_EXISTING);
      for (String[] task: dataBase){
        String output = String.join(",",task)+"\n";
        Files.writeString(filePath, output, StandardOpenOption.APPEND);
      }
    }
  }
}


