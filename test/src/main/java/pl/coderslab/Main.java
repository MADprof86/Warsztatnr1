package pl.coderslab;

public class Main {

  public static void main(String[] args) {
    String line = "This is a long string that needs to be split into multiple lines.";
    int columnWidth = 10;

    printWrappedLine(line, columnWidth);
  }

  private static void printWrappedLine(String line, int columnWidth) {
    int timesToPrint = (int) Math.ceil((double) line.length() / columnWidth);

    for (int i = 0; i < timesToPrint; i++) {
      int startIndex = i * columnWidth;
      int endIndex = Math.min((i + 1) * columnWidth, line.length());

      System.out.printf("%-" + columnWidth + "s%n", line.substring(startIndex, endIndex));
    }
  }

}