import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class DependencyGraph {
    private final String rootPath;
    private final List<FileData> files;

    public DependencyGraph(String root) throws Throwable {
        rootPath = root;
        files = new ArrayList<>();
        recursiveBuild(rootPath);
    }

    private String parseDependency(String require) throws WrongRequireException {
        if (require.split("'").length > 2) {
            throw new WrongRequireException("Wrong require format (" + require + ")");
        }
        String fileName = rootPath + File.separator + require.split("'")[1];
        fileName = fileName.replace("\\", File.separator);
        fileName = fileName.replace("/", File.separator);
        if (FileData.isFIleInvalid(fileName)) {
            throw new WrongRequireException("Non-existent file in require");
        }
        return fileName;
    }

    private List<String> getDependencies(String path) throws Throwable {
        ArrayList<String> answer = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(path));
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.startsWith("require")) {
                answer.add(parseDependency(line));
            }
        }
        return answer;
    }

    private void recursiveBuild(String path) throws Throwable {
        File dir = new File(path);
        if (dir.isFile()) {
            List<String> dependencies = getDependencies(path);
            FileData f = new FileData(path, dependencies);
            files.add(f);
            return;
        }
        for (File file : Objects.requireNonNull(dir.listFiles())) {
            recursiveBuild(file.getPath());
        }
    }

    public List<FileData> getFiles() {
        return files;
    }
}
