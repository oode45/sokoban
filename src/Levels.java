import java.io.*;

public class Levels {

    public Levels() {

    }

    public int[][] nextLevel(String fileName) throws Exception {
        String content = getContentFile(new File(fileName));
        int[][] desktop = convertStrTo2DArray(content);
        return desktop;
    }

    public int[][] convertStrTo2DArray(String line) {
        System.out.println(line);
        int row = 0;
        for (int i = 0; i < line.length(); i++) {
            char symbol = line.charAt(i);
            if (symbol == '\n') {
                row = row + 1;
            }
        }

        int[][] arr = new int[row][];

        int column = 0;
        int index = 0;

        for (int i = 0; i < line.length(); i++) {
            char symbol = line.charAt(i);
            if (symbol != '\n') {
                column = column + 1;
            } else if (symbol == '\n') {
                arr[index] = new int[column];
                index++;
                column = 0;
            }
        }
        row = 0;
        column = 0;

        for (int i = 0; i < line.length(); i++) {
            char symbol = line.charAt(i);
            if (symbol != '\n') {
                arr[row][column] = Integer.parseInt("" + symbol);
                column = column + 1;
            } else if (symbol == '\n') {
                row = row + 1;
                column = 0;
            }
        }

        return arr;
    }

    private String getContentFile(File file) throws Exception {
        try (FileInputStream inputStream = new FileInputStream(file)) {
            int size = (int) file.length();
            char[] array = new char[size];
            int index = 0;
            int unicode;
            while ((unicode = inputStream.read()) != -1) {
                char symbol = (char) unicode;
                if (('0' <= symbol && symbol <= '4') || (symbol == '\n')) {
                    array[index] = symbol;
                    index = index + 1;
                }
            }
            String content = new String(array, 0, index);
            return content;
        } catch (FileNotFoundException fne) {
            throw new Exception("File Not Found Exception! " + fne);
        } catch (IOException ioe) {
            throw new Exception("Basic IO Exception: " + ioe);

        }
    }
}
