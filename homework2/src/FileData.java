import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * This class implements functionality of reading file and creating list of
 * dependencies, printing data of file and checking if file valid.
 */
public final class FileData {
    private final String path;
    private final List<String> dependencies;

    /**
     * This method returns path to this file.
     * @return Path to this file.
     */
    public String getPath() {
        return path;
    }

    /**
     * This method returns list of names of files
     * from which this file is dependent.
     * @return List of dependencies.
     */
    public List<String> getDependencies() {
        return dependencies;
    }

    /**
     * This is public constructor of class.
     * @param filePath Path to file.
     * @param fileDependencies List of paths of files
     *                         from which this file is dependent.
     * @throws IllegalArgumentException Exception
     */
    public FileData(String filePath, List<String> fileDependencies)
            throws IllegalArgumentException {
        if (isFIleInvalid(filePath)) {
            throw new IllegalArgumentException("File path is not valid!");
        }
        path = filePath;
        dependencies = new ArrayList<>(fileDependencies);
    }

    /**
     * This method checks is specified file invalid.
     * @param path Path to file which should be checked.
     * @return Boolean value, is file invalid or is not.
     */
    public static boolean isFIleInvalid(String path) {
        File file = new File(path);
        return (!file.exists() || file.isDirectory());
    }

    /**
     * This method prints this file in console.
     */
    public void printFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))){
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Throwable ignored) {
        }
    }

    /**
     * This is overridden method for getting hash code of object.
     * @return Hash code of object.
     */
    @Override
    public int hashCode() {
        return path.hashCode();
    }

    /**
     * This is overridden method for checking files on equality. Compares file
     * by comparing their paths.
     * @param o Object, which should be compared.
     * @return Boolean value, are objects equal or are not.
     */
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
