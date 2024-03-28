package com.hdpt.device.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 写文件工具
 */
public class FileUtils {
    private static Log logger = LogFactory.getLog(FileUtils.class);
    private static String fileSplit = "/";

    public static void test() {
        scanAllFile(new File("E:/ACD2.0"), "svn", ".dat", ".vvb");
        System.out.println("delete success!");
    }
    public static String fileToString(String filePath) throws Exception{
        String input = null;
        Scanner sc = new Scanner(new File(filePath));
        StringBuffer sb = new StringBuffer();
        while (sc.hasNextLine()) {
            input = sc.nextLine();
            sb.append(input);
        }
        return sb.toString();
    }

    /**
     * 删除文件中的字符串
     * @param fileName 文件
     * @param path 路径
     * @param delText 删除内容
     * @param charset UTF-8等编码
     */
    public static boolean delTextByFile(String fileName,String path,String delText,String charset) {
        // TODO Auto-generated method stub
        boolean b=false;
        try {
            File file = new File(path+"/"+fileName);
            //File temp = File.createTempFile("file1",".txt", file.getParentFile());
            //String charset ="UTF-8";
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), charset));
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file), charset));
            for (String line; (line = reader.readLine()) != null;) {
                line = line.replace(delText,"");
                writer.println(line);
            }
            reader.close();
            writer.close();
            b=true;
        }
        catch (Exception e) {
            logger.info("delTextByFile error:"+e);
            b=false;
        }
        return b;
    }


    /**     *
     * @function 扫描待删除的目录或文件
     * @root 待扫描的根目录
     * @suffix 待删除的目录或文件(扩展名匹配)
     */
    public static void scanAllFile(File root, String... suffix) {
        for (File file : root.listFiles()) {
            if (isMatch(file.getName(), suffix)) {
                delAllFile(file);
            } else if (file.isDirectory()) {
                scanAllFile(file, suffix);
            }
        }
    }

    // 判断是否为待删除的目录或文件
    public static boolean isMatch(String name, String... suffix) {
        for (String s : suffix) {
            if (name.endsWith(s)) {
                return true;
            }
        }
        return false;
    }

    // 删除指定的目录或文件
    public static void delAllFile(File file) {
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                if (!f.delete()) {
                    delAllFile(f);
                }
            }
        }
        file.delete();
        System.out.println("delete : " + file.getAbsolutePath());
    }
    /**
     * 删除文件，可以是文件或文件夹
     *
     * @param fileName 要删除的文件名
     * @return 删除成功返回true，否则返回false
     */
    public static boolean delete(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("删除文件失败:" + fileName + "不存在！");
            return false;
        } else {
            if (file.isFile())
                return deleteFile(fileName);
            else
                return deleteDirectory(fileName);
        }
    }

    /**
     * 删除单个文件
     *
     * @param fileName 要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                System.out.println("删除单个文件" + fileName + "成功！");
                return true;
            } else {
                System.out.println("删除单个文件" + fileName + "失败！");
                return false;
            }
        } else {
            System.out.println("删除单个文件失败：" + fileName + "不存在！");
            return false;
        }
    }

    /**
     * 删除目录及目录下的文件
     *
     * @param dir 要删除的目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    public static boolean deleteDirectory(String dir) {
        // 如果dir不以文件分隔符结尾，自动添加文件分隔符
        if (!dir.endsWith(File.separator))
            dir = dir + File.separator;
        File dirFile = new File(dir);
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
            System.out.println("删除目录失败：" + dir + "不存在！");
            return false;
        }
        boolean flag = true;
        // 删除文件夹中的所有文件包括子目录
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            // 删除子文件
            if (files[i].isFile()) {
                flag = FileUtils.deleteFile(files[i].getAbsolutePath());
                if (!flag)
                    break;
            }
            // 删除子目录
            else if (files[i].isDirectory()) {
                flag = FileUtils.deleteDirectory(files[i]
                        .getAbsolutePath());
                if (!flag)
                    break;
            }
        }
        if (!flag) {
            System.out.println("删除目录失败！");
            return false;
        }
        // 删除当前目录
        if (dirFile.delete()) {
            System.out.println("删除目录" + dir + "成功！");
            return true;
        } else {
            return false;
        }
    }
    /**
     * 写文件
     *
     * @param in  读取流
     * @param out 写入流
     * @return
     * @throws Exception
     */
    public static boolean writeFile(InputStream in, OutputStream out) {
        boolean b = false;
        try {
            int bathSize = 200;
            int i = 0;
            long outSize = 0L;
            byte[] bytes = new byte[10240];//10k,根据硬盘缓存大小调节
            int c;
            while ((c = in.read(bytes)) != -1) {
                out.write(bytes, 0, c);
                outSize += c;
                i++;
                if ((i > 0 && (i % bathSize) == 0) || c <= 0) {
                    logger.info("正在写文件本次写入: " + c + " btyes, 总计已写:" + outSize);
                }
            }
            in.close();
            out.close();
        } catch (Exception e) {
            logger.error("写入文件时失败，可能磁盘已满，或者其它原因.", e);
        }

        return b;
    }

    /**
     * 写文件
     *
     * @param srcName    源文件名称,不包含路径
     * @param destName   目标文件名称,不包含路径
     * @param srcDir     源目录
     * @param destDir    目标目录
     * @param srcEncode  源字符集
     * @param destEncode 目标字符集
     * @return
     */
    public static boolean writeFile(String srcName, String destName, String srcDir, String destDir, String srcEncode, String destEncode) {
        boolean b = false;
        List<String> srcList = readFileData(srcName, srcDir, srcEncode);
        if (srcList == null || srcList.size() == 0) {
            return b;
        }
        b = writeFile(srcList, destName, destDir, destEncode);
        return b;
    }

    /**
     * 将list文本文件写入文件
     * @param srcList  内容
     * @param destName 文件名
     * @param destDir 文件存放路径
     * @param destEncode  utf-8 编码
     * @return
     */
    public static boolean writeFile(List<String> srcList, String destName, String destDir, String destEncode) {
        boolean b = false;
        FileOutputStream out = null;
        try {
            if (srcList == null || srcList.size() == 0) {
                return true;
            }
            String outPathFileName = destDir + fileSplit + destName;
            out = getFileOutStream(destName, destDir);
            if (out == null) {
                return false;
            }
            StringBuffer bf = new StringBuffer(128);
            int bathSize = 200;
            long outSize = 0L;
            byte[] bytes = new byte[20480];//20k,根据硬盘缓存大小调节
            byte[] inbytes = null;
            for (int i = 0; i < srcList.size(); i++) {
                bf.append(srcList.get(i));
                //if ((i > 0 && (i % bathSize) == 0) || (i + 1) == srcList.size()) {
                    //不管原来什么编码, 把字符按目标编码换
                    inbytes = bf.toString().getBytes(destEncode);
                    ByteArrayInputStream in = new ByteArrayInputStream(inbytes);
                    int c;
                    while ((c = in.read(bytes)) != -1) {
                        out.write(bytes, 0, c);
                        out.write('\r'); //写入换行
                        outSize += c;
                    }
                    in.close();
                    in = null;
                    bf = new StringBuffer(128);
                    logger.info("正在写文件[" + outPathFileName + "],本次写入: " + inbytes.length + " btyes, 总计已写:" + outSize);
                //}
            }
            bytes = null;
            inbytes = null;
            return true;
        } catch (Exception e) {
            b = false;
            logger.error("写入文件时失败，可能磁盘已满，或者其它原因.", e);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                out = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return b;
    }

    /**
     * 读取参数文件文体内容
     *
     * @param destName   目标文件名称,不包含路径
     * @param destDir    目标目录
     * @param destEncode 目标字符集
     */
    public static List<String> readFileData(String destName, String destDir, String destEncode) {
        List<String> result = null;
        FileInputStream inStream = null;
        BufferedReader bufReader = null;
        String inPathFileName = destDir + fileSplit + destName;
        try {
            inStream = getFileInStream(destName, destDir);
            if (inStream == null) {
                return null;
            }
            bufReader = new BufferedReader(new InputStreamReader(inStream, destEncode));
            String read = null;
            result = new ArrayList<String>();
            while ((read = bufReader.readLine()) != null) {
                result.add(read);
            }
        } catch (Exception e) {
            result = null;
            logger.error("读取文件失败," + inPathFileName, e);
        } finally {
            try {
                if (bufReader != null) {
                    bufReader.close();
                }
                bufReader = null;
                if (inStream != null) {
                    inStream.close();
                }
                inStream = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        logger.info("读取文件行数: " + (result == null ? 0 : result.size()) + ", " + inPathFileName);
        return result;
    }

    /**
     * 没有目录则创建
     */
    public static boolean mkDirs(String srcDir) {
        boolean b = false;
        File dir = new File(srcDir);
        if (!dir.exists()) {
            b = dir.mkdirs();
        } else {
            b = true;
        }
        return b;
    }

    /**
     * 删除文件
     */
    public static boolean deleteFile(String srcName, String srcDir) {
        boolean b = false;
        String dirFileName = srcDir + fileSplit + srcName;
        File outf = new File(dirFileName);
        if (outf.exists()) {
            b = outf.delete();
        } else {
            b = true;
        }
        return b;
    }

    /**
     * 获取输出文件流
     */
    public static FileOutputStream getFileOutStream(String srcName, String srcDir) {
        FileOutputStream out = null;
        try {
            String pathFileName = srcDir + fileSplit + srcName;
            if (!mkDirs(srcDir)) {
                return null;
            }
            out = new FileOutputStream(pathFileName);
        } catch (Exception e) {
            out = null;
        }
        //FileUtils.moveFile(srcFile, destFile);
        return out;
    }

    /**
     * 获取读取文件流
     */
    public static FileInputStream getFileInStream(String srcName, String srcDir) {
        FileInputStream in = null;
        try {
            String pathFileName = srcDir + fileSplit + srcName;
            File inf = new File(pathFileName);
            if ((inf == null) || (!inf.exists())) {
                return null;
            }
            in = new FileInputStream(pathFileName);
        } catch (Exception e) {
            in = null;
        }
        return in;
    }

    public static void delContentByFile(String fileName,String delContent) {
        try {
            //File file = new File("example.txt");
            File file = new File(fileName);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.contains(delContent)) {
                    content.append(line);
                    content.append(System.lineSeparator());
                }
            }

            reader.close();

            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(content.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}