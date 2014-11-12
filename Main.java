import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
class Main {
    public static void main(String[] args) throws Exception {
        Path tmp = Files.createTempDirectory(Paths.get(System.getProperty("user.home")), "tmp");
        Path userDir = Paths.get(System.getProperty("user.dir"));
        for (int i = 1; i <= 2; i++) {
            Files.copy(userDir.resolve("v" + i).resolve("T.class"), tmp.resolve("T.class"), StandardCopyOption.REPLACE_EXISTING);
            URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{tmp.toUri().toURL()});
            Class clazz = classLoader.loadClass("T");
            JUnitCore runner = new JUnitCore();
            Result result = runner.run(clazz);
            System.out.println("v" + i + " runCount = " + result.getRunCount());
        }
    }
}
