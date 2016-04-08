import java.io.File;
import java.io.IOException;

/**
 * Created by cy on 2016/4/7.
 */
public class WorkDir {
    /**定位到moudle名*/
    public static final String PATH_AS_SRC="\\src\\main\\java";
    public static final String PATH_AS_RES="\\src\\main\\res";
    public static final String PATH_AS_JNILIBS="\\src\\main\\jniLibs";
    public static final String PATH_AS_LIBS="\\libs";
    public static final String PATH_AS_MANIFEST="\\src\\main\\AndroidManifest.xml";

    public static final String PATH_ECLIP_SRC="\\src";
    public static final String PATH_ECLIP_RES="\\res";
    public static final String PATH_ECLIP_JNILIBS="\\libs";
    public static final String PATH_ECLIP_LIBS="\\libs";
    public static final String PATH_ECLIP_MANIFEST="\\AndroidManifest.xml";

    static String srcProjectPath;
    static String destProjectPath;
    public static final String KEY_SRC_PATH ="KEY_SRC_PATH";
    public static final String KEY_DEST_PATH ="KEY_DEST_PATH";

    public static void main(String[] args) {
        srcProjectPath=PropertyHelper.getValue(KEY_SRC_PATH,true);
        System.out.println("取到"+ KEY_SRC_PATH +" "+srcProjectPath);
        destProjectPath=PropertyHelper.getValue(KEY_DEST_PATH,true);
        System.out.println("取到"+ KEY_DEST_PATH +" "+destProjectPath);

        try {
            copyWorkDir(srcProjectPath,destProjectPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void copyWorkDir(String srcProjectPath,String destProjectPath) throws IOException {
        if (isASProject(srcProjectPath)){
            //从studio往eclipse复制
            //复制src
            UtilFile.deleteDirectory(destProjectPath+PATH_ECLIP_SRC);
            UtilFile.copyFolder(new File(srcProjectPath+PATH_AS_SRC),new File(destProjectPath+PATH_ECLIP_SRC));
            //复制res
            UtilFile.deleteDirectory(destProjectPath+PATH_ECLIP_RES);
            UtilFile.copyFolder(new File(srcProjectPath+PATH_AS_RES),new File(destProjectPath+PATH_ECLIP_RES));
            //复制jnilibs
            UtilFile.deleteDirectory(destProjectPath+PATH_ECLIP_JNILIBS);
            UtilFile.copyFolder(new File(srcProjectPath+PATH_AS_JNILIBS),new File(destProjectPath+PATH_ECLIP_JNILIBS));
            //复制libs
            UtilFile.copyFolder(new File(srcProjectPath+PATH_AS_LIBS),new File(destProjectPath+PATH_ECLIP_LIBS));
            //复制manifest
            UtilFile.deleteDirectory(destProjectPath+PATH_ECLIP_MANIFEST);
            UtilFile.copyFolder(new File(srcProjectPath+PATH_AS_MANIFEST),new File(destProjectPath+PATH_ECLIP_MANIFEST));
        }else{
            //从eclipse往studio复制
            //复制src
            UtilFile.deleteDirectory(destProjectPath+PATH_AS_SRC);
            UtilFile.copyFolder(new File(srcProjectPath+PATH_ECLIP_SRC),new File(destProjectPath+PATH_AS_SRC));
            //复制res
            UtilFile.deleteDirectory(destProjectPath+PATH_AS_RES);
            UtilFile.copyFolder(new File(srcProjectPath+PATH_ECLIP_RES),new File(destProjectPath+PATH_AS_RES));
            //复制jnilibs
            UtilFile.deleteDirectory(destProjectPath+PATH_AS_JNILIBS);
            UtilFile.copyFolder(new File(srcProjectPath+PATH_ECLIP_JNILIBS),new File(destProjectPath+PATH_AS_JNILIBS));
            //删掉jnilibs中刚才多复制的jars
            File[] files=new File(destProjectPath+PATH_AS_JNILIBS).listFiles();
            for (int i = 0; i < files.length; i++) {
                if (!files[i].isDirectory()){
                    files[i].delete();
                }
            }
            //复制libs
            UtilFile.deleteDirectory(destProjectPath+PATH_AS_LIBS);
            UtilFile.copyFolder(new File(srcProjectPath+PATH_ECLIP_LIBS),new File(destProjectPath+PATH_AS_LIBS));
            //删掉libs中刚才多复制的jni
            File[] files2=new File(destProjectPath+PATH_AS_LIBS).listFiles();
            for (int i = 0; i < files2.length; i++) {
                if (files2[i].isDirectory()){
                    UtilFile.deleteDirectory(files2[i].getAbsolutePath());
                }
            }
            //复制manifest
            UtilFile.deleteDirectory(destProjectPath+PATH_AS_MANIFEST);
            UtilFile.copyFolder(new File(srcProjectPath+PATH_ECLIP_MANIFEST),new File(destProjectPath+PATH_AS_MANIFEST));


        }

    }

    /**是否是Android studio项目*/
    public static boolean isASProject(String path){
        if (new File(path+"/gradle").exists()){
            System.out.println(path+"/gradle 存在");
            return true;
        }else return false;
    }
}
