package Graphical;

import Listeners.MouseListener;
import Listeners.MouseMoveListener;
import Listeners.MouseScrollListener;
import Standard.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {


    private JPanel panel1;
    private JPanel drawPanel;
    private JButton addParticleButton;
    private JButton PAUSEButton;
    private JButton FWDButton;
    private JButton FFWDButton;
    private JButton FFFWDButton;
    private JButton FFFFWDButton;
    private JButton FFFFFWDButton;
    private JButton wireButton;
    private JSlider XAxisSlider;
    private JSlider YAxisSlider;
    private JPanel RotationControls;
    private JPanel PathfinderControls;
    private JSlider ZAxisSlider;
    private JButton vertButton;
    private JButton intersectButton;
    private JSlider depthSlider;
    private JSlider xMoveSlider;
    private JSlider yMoveSlider;
    private JSlider zMoveSlider;
    private JSlider xScaleSlider;
    private JSlider yScaleSlider;
    private JSlider zScaleSlider;
    private JButton clearBoardButton;

    private DrawablePanel drawablePanel;

    private Color deactivatedCol = new Color(165, 0, 0);
    private Color activatedCol = new Color(41, 203, 24);
    /**
     * The main window in which all interaction takes place, sets up buttons and main
     */
    public MainFrame() {
        // Window setup
        $$$setupUI$$$();
        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        pack();

        // Create instance of main
        Main main = new Main();

        // Gives main and drawablePanel reference to each other
        main.setDrawablePanel(drawablePanel);
        drawablePanel.setMain(main);

        // Adds listeners to the drawable panel
        drawablePanel.addMouseListener(new MouseListener(main));
        drawablePanel.addMouseMotionListener(new MouseMoveListener(main));
        drawablePanel.addMouseWheelListener(new MouseScrollListener(main));

        XAxisSlider.addChangeListener(e -> {
            main.matrix3.rotation.x = XAxisSlider.getValue();
            main.UpdateMatrix();
        });

        YAxisSlider.addChangeListener(e -> {
            main.matrix3.rotation.y = YAxisSlider.getValue();
            main.UpdateMatrix();
        });

        ZAxisSlider.addChangeListener(e -> {
            main.matrix3.rotation.z = ZAxisSlider.getValue();
            main.UpdateMatrix();
        });

        xMoveSlider.addChangeListener(e -> {
            main.matrix3.translation.x = xMoveSlider.getValue();
            main.UpdateMatrix();
        });

        yMoveSlider.addChangeListener(e -> {
            main.matrix3.translation.y = yMoveSlider.getValue();
            main.UpdateMatrix();
        });

        zMoveSlider.addChangeListener(e -> {
            main.matrix3.translation.z = zMoveSlider.getValue();
            main.UpdateMatrix();
        });

        xScaleSlider.addChangeListener(e -> {
            main.matrix3.scale.x = xScaleSlider.getValue();
            main.UpdateMatrix();
        });

        yScaleSlider.addChangeListener(e -> {
            main.matrix3.scale.y = yScaleSlider.getValue();
            main.UpdateMatrix();
        });

        zScaleSlider.addChangeListener(e -> {
            main.matrix3.scale.z = zScaleSlider.getValue();
            main.UpdateMatrix();
        });

        depthSlider.addChangeListener(e -> main.depth = depthSlider.getValue());

        // Sets up all buttons and their actions
        drawablePanel.repaint();
        wireButton.addActionListener(e -> {
            if (main.wire) {
                main.wire = false;
                wireButton.setBackground(deactivatedCol);
                wireButton.setText("Wireframe: OFF");
            } else {
                main.wire = true;
                wireButton.setBackground(activatedCol);
                wireButton.setText("Wireframe: ON");
            }
        });

        vertButton.addActionListener(e -> {
            if (main.verts) {
                main.verts = false;
                vertButton.setBackground(deactivatedCol);
                vertButton.setText("Vertices: OFF");
            } else {
                main.verts = true;
                vertButton.setBackground(activatedCol);
                vertButton.setText("Vertices: ON");
            }
        });

        intersectButton.addActionListener(e -> {
            if (main.intersections) {
                main.intersections = false;
                intersectButton.setBackground(deactivatedCol);
                intersectButton.setText("Intersections: OFF");
            } else {
                main.intersections = true;
                intersectButton.setBackground(activatedCol);
                intersectButton.setText("Intersections: ON");
            }
        });
    }

    // Turns the drawPanel into a DrawablePanel type which contains all the custom behaviours required, gets reference to new DrawablePanel
    private void createUIComponents() {
        drawPanel = new DrawablePanel();
        drawablePanel = (DrawablePanel) drawPanel;
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        panel1 = new JPanel();
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(8, 13, new Insets(0, 0, 0, 0), -1, -1));
        panel1.setBackground(new Color(-13882324));
        panel1.add(drawPanel, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 11, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(1000, 700), null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
        panel1.add(spacer1, new com.intellij.uiDesigner.core.GridConstraints(7, 1, 1, 11, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(-1, 30), null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer2 = new com.intellij.uiDesigner.core.Spacer();
        panel1.add(spacer2, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 11, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(-1, 30), null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer3 = new com.intellij.uiDesigner.core.Spacer();
        panel1.add(spacer3, new com.intellij.uiDesigner.core.GridConstraints(0, 12, 8, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, new Dimension(30, -1), null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer4 = new com.intellij.uiDesigner.core.Spacer();
        panel1.add(spacer4, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 8, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, new Dimension(30, -1), null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer5 = new com.intellij.uiDesigner.core.Spacer();
        panel1.add(spacer5, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 11, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(-1, 20), null, 0, false));
        addParticleButton = new JButton();
        addParticleButton.setBackground(new Color(-2368549));
        addParticleButton.setText("Reset Simulation");
        panel1.add(addParticleButton, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer6 = new com.intellij.uiDesigner.core.Spacer();
        panel1.add(spacer6, new com.intellij.uiDesigner.core.GridConstraints(5, 8, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        PAUSEButton = new JButton();
        PAUSEButton.setBackground(new Color(-5187));
        PAUSEButton.setText("| |");
        panel1.add(PAUSEButton, new com.intellij.uiDesigner.core.GridConstraints(5, 1, 2, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(20, 20), null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer7 = new com.intellij.uiDesigner.core.Spacer();
        panel1.add(spacer7, new com.intellij.uiDesigner.core.GridConstraints(3, 4, 1, 8, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        FFFFFWDButton = new JButton();
        FFFFFWDButton.setBackground(new Color(-65520));
        FFFFFWDButton.setText(">>>>>");
        panel1.add(FFFFFWDButton, new com.intellij.uiDesigner.core.GridConstraints(5, 7, 2, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(20, 20), null, 0, false));
        FFFFWDButton = new JButton();
        FFFFWDButton.setBackground(new Color(-52992));
        FFFFWDButton.setText(">>>>");
        panel1.add(FFFFWDButton, new com.intellij.uiDesigner.core.GridConstraints(5, 6, 2, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(20, 20), null, 0, false));
        FFFWDButton = new JButton();
        FFFWDButton.setBackground(new Color(-39680));
        FFFWDButton.setText(">>>");
        panel1.add(FFFWDButton, new com.intellij.uiDesigner.core.GridConstraints(5, 5, 2, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(20, 20), null, 0, false));
        FFWDButton = new JButton();
        FFWDButton.setBackground(new Color(-29696));
        FFWDButton.setText(">>");
        panel1.add(FFWDButton, new com.intellij.uiDesigner.core.GridConstraints(5, 4, 2, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(20, 20), null, 0, false));
        FWDButton = new JButton();
        FWDButton.setBackground(new Color(-11918));
        FWDButton.setText(">");
        panel1.add(FWDButton, new com.intellij.uiDesigner.core.GridConstraints(5, 3, 2, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(20, 20), null, 0, false));
        wireButton = new JButton();
        wireButton.setBackground(new Color(-4438273));
        wireButton.setText("Funky Mode");
        panel1.add(wireButton, new com.intellij.uiDesigner.core.GridConstraints(5, 11, 2, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        clearBoardButton = new JButton();
        clearBoardButton.setBackground(new Color(-4473925));
        clearBoardButton.setText("Clear Board");
        panel1.add(clearBoardButton, new com.intellij.uiDesigner.core.GridConstraints(3, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }

}
