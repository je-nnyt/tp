package voyatrip;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.FileVisitResult;
import java.nio.file.attribute.BasicFileAttributes;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class StorageTest {

    @AfterEach
    public void deleteDirectory() throws IOException {
        String pathStr = "data";
        Path path = Paths.get(pathStr);
        if (Files.exists(path)) {
            Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Files.delete(file);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    Files.delete(dir);
                    return FileVisitResult.CONTINUE;
                }
            });
        }
    }

    @Test
    public void load_emptyFile_success() {
        assertTrue(Storage.load().isEmpty());
    }

    @Test
    public void save_emptyFile_success() {
        TripList testTripList = new EmptyTripListStub();
        Storage.save(testTripList);
        assertTrue(Storage.load().isEmpty());
    }
}
