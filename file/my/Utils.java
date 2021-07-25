package Swing.dhk.file.my;

import java.io.File;
import java.util.Scanner;

/**
 * @DATE 2021/6/25
 * @author DouJiao
 * @version 1.0
 * 豆角的工具类
 */
public class Utils {
    /**
     * 缩写 System.out.println();
     * @param obj
     */
    public static void prl(Object obj){
        System.out.println(obj);
    }

    /**
     * 缩写 System.out.print();
     * @param obj
     */
    public static void pr(Object obj){
        System.out.print(obj);
    }

    /**
     * 重命名文件 , 新命名文件 + 数值 排列
     * @param filePath 目录路径
     * @param newFileName 新命名文件的格式前缀
     * @return 目录路径
     */
    public static String renameFilesNum(String filePath , String newFileName){
        //实例化file，返回目录的抽象路径
        File file = new File(filePath);
        //判断此抽象路径名表示的文件是否为目录
        if (file.isDirectory()){
            //定义字符串数组变量，用于保存该目录下的文件名
            String list[]=file.list();
            //遍历
            for (int i = 0; i < list.length; i++) {
                File dirFiles = new File(filePath+"\\"+list[i]);
                //如果文件数小于10，结尾格式为 0+i
                System.out.println(dirFiles);
                    if (i<10){
                        dirFiles.renameTo(new File(filePath + "\\" + newFileName + "0"+(i+1)));
                    }else {
                        dirFiles.renameTo(new File(filePath + "\\" + newFileName + (i+1)));
                    }

            }
        }
        return filePath;
    }

    /**
     * 挨个重命名文件
     * @param path
     * @return 路径
     */
    public static File renameFilesAll(String path ){
        //定义dirname保存路径
        String dirname = path;
        //通过将给定的路径名字符串转换为抽象路径名来创建新的 File 实例
        File f = new File(dirname);

        //判断是否为此抽象路径名表示的文件是否为目录
        if(f.isDirectory()){
            //返回一个字符串数组，命名由此抽象路径名表示的目录中的文件和目录
            String str[] = f.list();
            //实例化sc，从键盘获取输入
            Scanner sc = new Scanner(System.in);
            //循环str.length次
            for(int i = 0; i<str.length; i++){
                //创建新的 File 实例
                File file = new File(str[i]);
                //控制台输出
                System.out.println("文件 "+(i+1)+": "+str[i]);
                System.out.print("请输入要修改的名称: ");
                //字符串inputVal保存从键盘接收的数据
                String inputVal = sc.nextLine();
                //重命名由此抽象路径名表示的文件
                file.renameTo(new File(dirname + "\\" + inputVal));
                //控制台输出更改后的文件
                System.out.println("文件 "+(i+1)+" 修改后名称为: "+dirname + "\\" + inputVal+"\n");
            }
            //关闭Scanner
            sc.close();
        }
        //返回dirname路径
        return f;
    }

    /**
     *
     * @param path 目录路径
     * @param Prefix 文件新命名的前缀
     * @param suffix 文件格式
     */
    public static void reNameTo(String path , String Prefix , String suffix ){
        //实例化file
        File file = new File(path);
        //判断此抽象路径名表示的文件是否为目录
        if(file.isDirectory()){
            //保存目录下的文件名
           String dirFile[] = file.list();
            for (int i = 0; i < dirFile.length; i++) {
                //完整路径
                File allFile = new File(path+"\\"+dirFile[i]);
                if(i<10){
                    allFile.renameTo(new File(path +"\\"+ Prefix+"0"+(i+1)+"."+suffix));
                }else {
                    allFile.renameTo(new File(path +"\\"+ Prefix+(i+1)+"."+suffix));
                }
            }
        }
    }

}
