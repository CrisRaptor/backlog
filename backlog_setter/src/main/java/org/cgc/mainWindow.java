package org.cgc;

import com.formdev.flatlaf.intellijthemes.FlatOneDarkIJTheme;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import org.cgc.items.FileNode;
import org.cgc.services.FileManager;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Locale;

/*
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

        // App window startup
        SwingUtilities.invokeLater(() -> {
            try {
                JFrame window = new JFrame("The Backlog");
                mainWindow gui = new mainWindow();
                if (gui.mainPanel == null) {
                    JOptionPane.showMessageDialog(null, "Error: El panel principal no se cargó.");
                    System.exit(1);
                }

                //Start tree info initialization
                File checkFile = new File("tree.json");
                if (!checkFile.exists()) { //If missing, load tree on app folder
                    System.out.println("Launch: tree.json not detected");
                } else {
                    System.out.println("Launch: tree.json detected, loading data");
                }
                gui.updateUIPanel();

                //Set pane
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
    }

    public mainWindow() {
    }

    // Initializes UI components and sets up listeners
    private void initUIComponents() {
        //Button listener
        buttonChoose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                //Chooser config
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                chooser.setDialogTitle("Selecciona la carpeta del proyecto");

                int returnValue = chooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    filePath = chooser.getSelectedFile().getAbsolutePath();
                    loadFileTree(filePath);
                    updateUIPanel();
                    System.out.println("Saved path: " + filePath);
                }
            }
        });
        //Reload listener
        reload.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Load tree info, if not detected generate one based on app folder
                File checkFile = new File("tree.json");
                if (!checkFile.exists()) { //If missing, load tree on app folder
                    System.out.println("Reload: tree.json not detected, generating one based on app folder");
                    loadFileTree(filePath);
                    updateUIPanel();
                } else {//Load tree again
                    System.out.println("Reload: tree.json detected, loading data");
                    updateUIPanel();
                }
            }
        });
    }

    // Actualiza el registro de archivos
    private void loadFileTree(String path) {
        File rootDir = new File(path);

        if (rootDir.exists() && rootDir.isDirectory()) {
            // Creamos el objeto árbol
            FileNode rootNode = service.buildFileTree(rootDir);

            // Guardamos a JSON
            service.saveTreeToJson(rootNode);
        }
    }

    //Actualiza el panel de informacion
    private void updateUIPanel() {
        FileNode data = service.readTreeFromJson();
        if (data != null) {
            output.setText("Loaded data from: \"" + filePath + "\"\n" + data.toString());
        } else {
            output.setText("No data, use reload for generate this folder data.\n" +
                    "Use button for selecting a folder to load.");
        }
        output.setCaretPosition(0);
    }

    //Non author code from here

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.setMaximumSize(new Dimension(1920, 1080));
        mainPanel.setMinimumSize(new Dimension(800, 600));
        mainPanel.setOpaque(false);
        mainPanel.setPreferredSize(new Dimension(1200, 900));
        choosePanel = new JPanel();
        choosePanel.setLayout(new GridLayoutManager(1, 2, new Insets(10, 0, 0, 0), -1, -1));
        mainPanel.add(choosePanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(400, -1), new Dimension(-1, 100), 0, true));
        buttonChoose = new JButton();
        buttonChoose.setAlignmentY(0.5f);
        buttonChoose.setAutoscrolls(false);
        Font buttonChooseFont = this.$$$getFont$$$("Consolas", Font.BOLD, 20, buttonChoose.getFont());
        if (buttonChooseFont != null) buttonChoose.setFont(buttonChooseFont);
        buttonChoose.setHorizontalTextPosition(0);
        buttonChoose.setMargin(new Insets(5, 10, 0, 10));
        buttonChoose.setOpaque(true);
        buttonChoose.setText("Examinar");
        choosePanel.add(buttonChoose, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_NORTHEAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(-1, 30), new Dimension(435, 40), new Dimension(-1, 500), 0, false));
        reload = new JButton();
        reload.setAlignmentY(0.5f);
        reload.setAutoscrolls(false);
        reload.setBackground(new Color(-14605013));
        Font reloadFont = this.$$$getFont$$$("Consolas", Font.BOLD, 20, reload.getFont());
        if (reloadFont != null) reload.setFont(reloadFont);
        reload.setHorizontalTextPosition(0);
        reload.setIcon(new ImageIcon(getClass().getResource("/reload.png")));
        reload.setLabel("");
        reload.setMargin(new Insets(0, 0, 0, 0));
        reload.setOpaque(true);
        reload.setText("");
        reload.setVerifyInputWhenFocusTarget(true);
        choosePanel.add(reload, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(30, 30), new Dimension(40, 40), new Dimension(50, 50), 0, false));
        functionPanel = new JPanel();
        functionPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(functionPanel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        outputScroll = new JScrollPane();
        functionPanel.add(outputScroll, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        output = new JTextArea();
        output.setBackground(new Color(-15066319));
        output.setEditable(false);
        Font outputFont = this.$$$getFont$$$("Consolas", Font.PLAIN, 16, output.getFont());
        if (outputFont != null) output.setFont(outputFont);
        output.setLineWrap(false);
        output.setText("Nothing");
        outputScroll.setViewportView(output);
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }

}
