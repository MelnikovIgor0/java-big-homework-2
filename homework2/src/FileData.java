import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public final class FileData {
    private final String path;
    private final List<String> dependencies;

    public String getPath() {
        return path;
    }

    public List<String> getDependencies() {
        return dependencies;
    }

    public FileData(String filePath, List<String> fileDependencies) throws IllegalArgumentException {
        if (isFIleInvalid(filePath)) {
            throw new IllegalArgumentException("File path is not valid!");
        }
        path = filePath;
        dependencies = new ArrayList<>(fileDependencies);
    }

    public static boolean isFIleInvalid(String path) {
        File file = new File(path);
        return (!file.exists() || file.isDirectory());
    }

    public void printFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))){
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Throwable ignored) {
        }
    }

    @Override
    public int hashCode() {
        return path.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof FileData)) {
            return false;
        }
        return path.equals(((FileData)o).path);
    }
}
