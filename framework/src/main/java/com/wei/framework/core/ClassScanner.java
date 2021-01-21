package com.wei.framework.core;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ClassScanner {

    public static List<Class<?>> scannerClasses(String packageName) throws IOException, ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        String path = packageName.replace(".", "/");
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Enumeration<URL> resources = classLoader.getResources(path);
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            if (resource.getProtocol().contains("jar")) {
                JarURLConnection jarURLConnection = (JarURLConnection)resource.openConnection();
                String jarFilePath = jarURLConnection.getJarFile().getName();
                classes.addAll(getClassFromJar(jarFilePath, path));
            } else {
                String filePath = resource.getPath();
                classes.addAll(getClassFromFile(new File(filePath), classes));
            }
        }
        return classes;
    }

    public static List<Class<?>> getClassFromJar(String jarFilePath, String path) throws IOException, ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        JarFile jarFile = new JarFile(jarFilePath);
        Enumeration<JarEntry> jarEntryEnumerations = jarFile.entries();
        while (jarEntryEnumerations.hasMoreElements()) {
            JarEntry jarEntry = jarEntryEnumerations.nextElement();
            String entryName = jarEntry.getName();
            if (entryName.startsWith(path) && entryName.endsWith(".class")) {
                String classFullName = entryName.replace("/", ".").substring(0,entryName.length() - 6);
                classes.add(Class.forName(classFullName));
            }
        }
        return classes;
    }

    public static List<Class<?>> getClassFromFile(File dir, List<Class<?>> classes) throws IOException, ClassNotFoundException {
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            for (File file : files) {
                getClassFromFile(file, classes);
            }
        } else  if (dir.getName().endsWith(".class")) {
            String entryName = dir.getPath();
            System.out.println(entryName);
            String className = entryName.substring(entryName.indexOf("classes") + 8).replace(File.separator, ".");
            System.out.println(className);
            String classFullName = className.substring(0, className.length() - 6);
            classes.add(Class.forName(classFullName));
        }
        return classes;
    }
}
