package pl.coderslab;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;


public class TaskManager{

  public static void main(String[] args) {
   runProgram();

  }
  public static void runProgram(){
    String[][] taskDataBase;
    try{
      taskDataBase = getTaskManagerDataBase("src/main/java/pl/coderslab/tasks.csv");
      showUIOptions(taskDataBase);
    }
    catch (IOException ex)
    {
      System.out.println("Problem during dataBase reading" + ex.getMessage());
    }

  }
  public static void showUIOptions(String[][] taskDataBase){
    String[] uiMenuOptions = {"add", "remove", "list", "exit"};
    String prompt = "Please select an option: ";
    String scannerInput;
    Scanner scanner = new Scanner(System.in);
    boolean terminateProgram = false;

    while (!terminateProgram){
      System.out.println(ConsoleColors.BLUE + prompt + ConsoleColors.RESET);
      for (String option : uiMenuOptions
      ) {
        System.out.print(option + "   ");
      }
      scannerInput = scanner.nextLine().toLowerCase().trim();

      switch (scannerInput){
          case "add" :
            //AddTask()
            break;

          case "remove" :
            //removeTask();
            break;

          case "list" :
            //showTaskList();
            break;

          case "exit" :
            //exitProgram();
            terminateProgram = true;
            break;

          default: System.out.println(
              ConsoleColors.RED +
                  "No such option. Please try again." +
              ConsoleColors.RESET);
      }
      if(terminateProgram) {
        System.out.println("Exiting program!");

      }
    }
  }
  public static String[][] resizeArray(String[][] arrayToResize){
    return Arrays.copyOf(arrayToResize,arrayToResize.length*2);
  }

  public static String[][] getTaskManagerDataBase(String file) throws IOException {
    String[][] taskManagerDataBase = new String[10][2];
    int arraySizeCounter = 0;
    Path filePath = Paths.get(file);
    if(Files.exists(filePath)){
      try (Scanner fileScanner = new Scanner(filePath)){
        while(fileScanner.hasNextLine())
        {
          String inputLine = fileScanner.nextLine().trim();
          if(arraySizeCounter == taskManagerDataBase.length) {
           taskManagerDataBase = resizeArray(taskManagerDataBase);
          }
          String[] task = inputLine.split(",");
          if(task.length != 3) {
            System.out.println(
                ConsoleColors.RED +
                    "Task corrupted while reading database" +
                ConsoleColors.RESET);
          }
          else {
            taskManagerDataBase[arraySizeCounter] = task;
            arraySizeCounter++;
          }
        }
      }
    }
    else throw new IOException ("The file does not exist");
    return taskManagerDataBase;
  }

}


