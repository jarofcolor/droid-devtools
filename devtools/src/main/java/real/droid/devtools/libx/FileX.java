package real.droid.devtools.libx;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class FileX {
    private final File file;

    private FileX(File file) {
        this.file = file;
    }

    public static FileX on(String path) {
        return on(new File(path));
    }

    public static FileX on(File file) {
        return new FileX(file);
    }

    public String text() {
        return text("utf-8");
    }

    public String text(String charset) {
        try {
            FileInputStream inputStream = new FileInputStream(file);
            int len;
            StringBuilder builder = new StringBuilder();
            byte[] bytes = new byte[4 * 1024];
            while ((len = inputStream.read(bytes)) != -1) {
                builder.append(new String(bytes, 0, len, charset));
            }
            return builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public void saveText(String text) {
        saveText(text, "utf-8");
    }

    public void saveText(String text, String charset) {
        try {
            FileOutputStream os = new FileOutputStream(file);
            os.write(text.getBytes(charset));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
