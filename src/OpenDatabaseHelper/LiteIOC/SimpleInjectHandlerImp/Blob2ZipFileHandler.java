package OpenDatabaseHelper.LiteIOC.SimpleInjectHandlerImp;

import OpenDatabaseHelper.LiteIOC.IInjectHandler;

import java.io.*;

/**
 * Created by WangYH on 2017/2/6.
 */
public class Blob2ZipFileHandler implements IInjectHandler<File>{
    @Override
    public File excute(Object x) {
        byte[] buff = (byte[])x;
        File tempFile = null;
        try {
            tempFile = new File(System.getProperty("user.dir")+"\\Models\\"+System.currentTimeMillis()+".zip");
            OutputStream out=new BufferedOutputStream(new FileOutputStream(tempFile));
            out.write(buff);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tempFile;
    }
}
