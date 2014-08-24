package ro.j.cache;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FilesystemFolder implements Folder {

    private boolean overwrite;
    private String folder;

    public FilesystemFolder(String folder) {
        this.folder = folder;
    }

    public FilesystemFolder overwrites(boolean overwrite) {
        this.overwrite = overwrite;
        return this;

    }

    @Override
    public OutputStream save(String filename) throws IOException  {
        File file = new File(folder, filename);
        if(file.exists())
        {
            if(overwrite)
            {
                file.delete();
            }
            else
            {
                throw new IOException(String.format("File %s already exists", file));
            }
        }
        else
        {
            file.createNewFile();
        }
        return new FileOutputStream(file);
    }

    @Override
    public InputStream load(String filename) throws IOException {
        File file = new File(folder, filename);
        if(!file.exists())
        {
            throw new IOException(String.format("File %s doesn't exist", file));
        }
        return new FileInputStream(file);
    }

}
