package org.cgc;

import com.formdev.flatlaf.intellijthemes.FlatOneDarkIJTheme;
import org.cgc.items.FileNode;
import org.cgc.services.FileManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/*
TODO Check jar+tree.json functionality
TODO Continue with:
TODO    reload button
TODO    load tree.json on init
TODO    function panel options:
TODO        show json data (actual output)
TODO        declare backlog set rules
TODO        start week backlog
TODO        show day backlog
TODO        backlog beat tracker
*/
//FIXME Jar with dependencies not running

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
        try {
            SwingUtilities.invokeLater(() -> {
                try {
                    JFrame window = new JFrame("The Backlog");
                    mainWindow gui = new mainWindow();
                    window.setContentPane(gui.mainPanel);
                    gui.initUIComponents(); // Call initialization AFTER content pane is set

                    //Set start parameters
                    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    window.setSize(1600, 900);
                    window.setVisible(true);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public mainWindow() {
        // Constructor should ideally be empty or only contain non-UI dependent logic
        // UI component initialization and listeners should be in a separate method
        // that is called after the components are guaranteed to be initialized.
    }

    // This method initializes UI components and sets up listeners
    private void initUIComponents() {
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
