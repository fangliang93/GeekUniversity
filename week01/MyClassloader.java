import java.io.*;
import java.lang.reflect.Method;

public class MyClassloader extends ClassLoader {
    public static void main(String[] args)  throws Exception{
        Class<?> helloClass = new MyClassloader().findClass("Hello");
        Object obj = helloClass.newInstance();
        Method method = helloClass.getMethod("hello");
        method.invoke(obj);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] bytes = new byte[0];
        try {
            bytes = MyClassloader.toByteArray("D:\\yinhai\\JY21HL00900\\04.Implement\\lnshzz\\lnshzz-business\\lnshzz-business-common\\src\\test\\java\\Hello.xlass");
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte)(255 - bytes[i]);
        }
        return defineClass(name,bytes,0,bytes.length);
    }

    /**
     * the traditional io way
     * 读取文件的方法
     * @param filename
     * @return
     * @throws IOException
     */
    public static byte[] toByteArray(String filename) throws IOException {

        File f = new File(filename);
        if (!f.exists()) {
            throw new FileNotFoundException(filename);
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream((int) f.length());
        BufferedInputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(f));
            int buf_size = 1024;
            byte[] buffer = new byte[buf_size];
            int len = 0;
            while (-1 != (len = in.read(buffer, 0, buf_size))) {
                bos.write(buffer, 0, len);
            }
            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            bos.close();
        }
    }

}
