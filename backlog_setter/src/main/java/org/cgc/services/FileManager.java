package org.cgc.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.cgc.items.FileNode;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileManager {
    // Create FileNode data with folder/file info
    public FileNode buildFileTree(File dir) {
        String nombre = dir.getName();
        // Si es la raíz y el nombre es vacío (ej. en Windows C:\), usamos la ruta
        if (nombre.isEmpty()) nombre = dir.getAbsolutePath();

        String extension = "";
        if (!dir.isDirectory() && nombre.contains(".")) {
            if (!(nombre.lastIndexOf(".") == 0)){ //if starts with . ignore it
                extension = nombre.substring(nombre.lastIndexOf(".") + 1);
                //Get rid of extension in name
                nombre = nombre.substring(0,nombre.lastIndexOf("."));
            }
        }



        FileNode node = new FileNode(nombre, extension, dir.isDirectory());

        if (dir.isDirectory()) {
            File[] lista = dir.listFiles();
            if (lista != null) {
                for (File f : lista) {
                    node.hijos.add(buildFileTree(f));
                }
            }
        }
        return node;
    }

    //Create tree.json on program folder
    public void saveTreeToJson(FileNode root) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        // Se guarda en el directorio de ejecución actual
        try (FileWriter writer = new FileWriter("tree.json")) {
            gson.toJson(root, writer);
            System.out.println("Service: tree.json creado con éxito.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Return tree.json data
    public FileNode readTreeFromJson() {
        Gson gson = new Gson();
        File file = new File("tree.json");
        if (!file.exists()) {
            System.err.println("Service: tree.json file not found");
            return null;
        }
        try (FileReader reader = new FileReader(file)) {
            FileNode tree = gson.fromJson(reader, FileNode.class);
            System.out.println("Service: tree.json loaded");
            return tree;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    //Return tree.json data from path
    public FileNode readTreeFromJson(String filePath) {
        FileNode tree = null;
        return tree;
    }
}
