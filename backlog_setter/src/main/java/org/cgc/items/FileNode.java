package org.cgc.items;

import java.util.ArrayList;
import java.util.List;

public class FileNode {
    public String nombre;
    public String extension;
    public boolean esCarpeta;
    public List<FileNode> hijos = new ArrayList<>();
    private static int folderDeepness = 0;

    public FileNode(String nombre, String extension, boolean esCarpeta) {
        this.nombre = nombre;
        this.extension = extension;
        this.esCarpeta = esCarpeta;
    }

    @Override
    public String toString() {
        String data = "";
        if (esCarpeta) {
            data += "C-";
        } else {
            data += "F-";
        }
        data += nombre;
        if (!extension.isEmpty()){
            data += "." + extension;
        }
        if (!hijos.isEmpty()) {
            folderDeepness++;
            data += " {\n";
            for (FileNode hijo : hijos) {
                for (int i = 0; i < folderDeepness; i++) { //Deepness spacing
                    data += "\t";
                }
                data += hijo.toString() + "\n";
            }
            folderDeepness--;
            for (int i = 0; i < folderDeepness; i++) { //Deepness spacing
                data += "\t";
            }
            data += "}";
        }
        return data;
    }
}
