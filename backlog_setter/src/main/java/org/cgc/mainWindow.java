package org.cgc;

import com.formdev.flatlaf.intellijthemes.FlatOneDarkIJTheme;
import org.cgc.items.FileNode;
import org.cgc.services.FileManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class mainWindow {
    private static FileManager service;
    public static String filePath = "";
    private JPanel mainPanel;
    private JButton buttonChoose;
    private JButton reload;
    private JPanel choosePanel;
    private JTextArea output;
    private JPanel functionPanel;
    private JScrollPane outputScroll;

    public static void main(String[] args) {
        // Parameter startup
        filePath = System.getProperty("user.dir");
        service = new FileManager();

        //Theme FlatLaf OneDark
        FlatOneDarkIJTheme.setup();

        //Fluid resize
        System.setProperty("sun.awt.noerasebackground", "true");
        Toolkit.getDefaultToolkit().setDynamicLayout(true);

        // App window startup
        SwingUtilities.invokeLater(() -> {
            try {
                JFrame window = new JFrame("The Backlog");
                mainWindow gui = new mainWindow();
                window.setContentPane(gui.mainPanel);

                //Set start parameters
                window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                window.setSize(1600, 900);
                window.setVisible(true);


            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public mainWindow() {
        output.setEditable(false);
        //Button listener
        buttonChoose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                //Chooser config
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                chooser.setDialogTitle("Selecciona la carpeta del proyecto");

                int returnValue = chooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION){
                    filePath = chooser.getSelectedFile().getAbsolutePath();
                    fileTreeService(filePath);
                    updateUIPanel();
                    System.out.println("Saved path: "+filePath);
                }
            }
        });
    }

    // Actualiza el registro de archivos
    private void fileTreeService(String path) {
        File rootDir = new File(path);

        if (rootDir.exists() && rootDir.isDirectory()) {
            // Creamos el objeto árbol
            FileNode rootNode = service.buildFileTree(rootDir);

            // Guardamos a JSON
            service.saveTreeToJson(rootNode);
        }
    }

    //Actualiza el panel de informacion
    private void updateUIPanel(){
        FileNode data = service.readTreeFromJson();
        if (data != null) {
            output.setText(data.toString());
        } else {
            output.setText("No data");
        }
        output.setCaretPosition(0);
    }
}
