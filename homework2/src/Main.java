import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) throws Throwable{
        Scanner scanner = new Scanner(System.in);
        String path = scanner.nextLine();
        try {
            DependencyGraph d = new DependencyGraph(path);
            List<FileData> loops = TopologicalSorter.getLoops(d);
            if (loops.size() > 0) {
                System.out.println("It's impossible to sort files topologically. There's dependency loop in files:");
                for (FileData file: loops) {
                    System.out.println(file.getPath());
                }
            } else {
                System.out.println("Merged data:");
                List<FileData> order = TopologicalSorter.sort(d);
                for (FileData file : order) {
                    file.printFile();
                }
            }
        } catch (WrongRequireException e) {
            System.out.println("Problem in require: " + e.getMessage());
        } catch (Throwable e) {
            System.out.println(e.getMessage());
        }
    }
}