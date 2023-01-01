import java.util.List;
import java.util.Scanner;

/**
 * The main class of program, which combines all the functionality.
 */
public class Main {
    /**
     * The main method of program, which combines all the functionality.
     * @param args Arguments of program.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String path = scanner.nextLine();
        try {
            DependencyGraph graph = new DependencyGraph(path);
            List<FileData> loops = TopologicalSorter.getLoops(graph);
            if (loops.size() > 0) {
                System.out.println("It's impossible to sort files " +
                        "topologically. There's dependency loop in files:");
                for (FileData file : loops) {
                    System.out.println(file.getPath());
                }
            } else {
                System.out.println("Merged data:");
                List<FileData> order = TopologicalSorter.sort(graph);
                for (FileData file : order) {
                    file.printFile();
                }
            }
        } catch (WrongRequireException exception) {
            System.out.println("Problem in require: " +
                    exception.getMessage());
        } catch (Throwable exception) {
            System.out.println(exception.getMessage());
        }
    }
}