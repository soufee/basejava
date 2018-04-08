package ru.shoma.webapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by Shoma on 11.03.2018.
 */
public class MainFile {
    public static void main(String[] args) {
//        File file = new File(".\\.gitignore");
//        try {
//            System.out.println(file.getCanonicalPath());
//        } catch (IOException e) {
//            throw new RuntimeException("Error", e);
//        }
        File dir = new File(".\\src\\ru\\shoma\\webapp");
        //System.out.println(file.isFile());
//        String[] files = dir.list();
//        if (dir.isDirectory() && files != null) {
//            for (int i = 0; i < files.length; i++) {
//                System.out.println(files[i]);
//            }
//        }

//        try (FileInputStream fis = new FileInputStream(file.getCanonicalPath())) {
//            System.out.println(fis.read());
//        } catch (IOException e) {
//            throw new RuntimeException("Error", e);
//        }

        printDirectoryDeep(dir);
    }
//TODO красивый вывод директорий и файлов
    public static void printDirectoryDeep(File dir) {
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    System.out.println("\t f:: " + file.getName());
                } else if (file.isDirectory()) {
                    System.out.println("D:: " + file.getName());
                    printDirectoryDeep(file);
                }
            }
        }
    }

}
