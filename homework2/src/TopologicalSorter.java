import java.security.KeyPair;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public final class TopologicalSorter {
    private static HashMap<String, FileData> filesDict;
    private static HashMap<String, Integer> color;
    private static List<FileData> order;
    private static HashMap<String, String> parent;
    private static String loop = "";

    private static void DFS(String path) {
        color.replace(path, 1);
        for (String file: filesDict.get(path).getDependencies()) {
            if (color.get(file) == 0) {
                parent.replace(file, path);
                DFS(file);
            } else if (color.get(file) == 1) {
                loop = file;
                parent.replace(file, path);
            }
        }
        color.replace(path, 2);
        order.add(filesDict.get(path));
    }

    private static List<FileData> getLoop(String start) {
        List<FileData> theLoop = new ArrayList<>();
        String current = parent.get(start);
        while (!current.equals(start)) {
            theLoop.add(filesDict.get(current));
            current = parent.get(current);
        }
        theLoop.add(filesDict.get(start));
        return theLoop;
    }

    private static void prepareData(DependencyGraph graph) {
        order = new ArrayList<>();
        filesDict = new HashMap<>();
        color = new HashMap<>();
        parent = new HashMap<>();
        for (FileData file: graph.getFiles()) {
            color.put(file.getPath(), 0);
            filesDict.put(file.getPath(), file);
            parent.put(file.getPath(), "");
        }
        loop = "";
    }

    public static List<FileData> sort(DependencyGraph graph) {
        prepareData(graph);
        for (FileData file: graph.getFiles()) {
            if (color.get(file.getPath()) == 0) {
                DFS(file.getPath());
            }
        }
        return order;
    }

    public static List<FileData> getLoops(DependencyGraph graph) {
        prepareData(graph);
        for (FileData file: graph.getFiles()) {
            if (color.get(file.getPath()) == 0) {
                DFS(file.getPath());
            }
            if (!loop.equals("")) {
                return getLoop(loop);
            }
        }
        return new ArrayList<>();
    }
}