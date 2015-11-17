/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.agh.kis.core.panels;

import java.awt.Window;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Consumer;
import javax.swing.JFileChooser;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
import org.openide.util.Exceptions;

/**
 *
 * @author Jakub Piotorwski
 */
public class LogicFormulasPanel extends javax.swing.JPanel {

    private static final String DEFULT_FORMULAS = "/pl/edu/agh/kis/core/panels/defaultFormulas.txt";
    private static final String USER_FORMULAS = "/pl/edu/agh/kis/core/panels/userFormulas.txt";
    private GeneratorPanel parent;
    private Path defaultPath, userPath;
    private BufferedWriter writer;
    private boolean isDefault;

    /**
     * Creates new form LogicFormulasPanel
     */
    public LogicFormulasPanel() {
        initComponents();
    }
    
    public LogicFormulasPanel(GeneratorPanel parent) {
        initComponents();
        this.parent = parent;
        try {
            defaultPath = Paths.get(getClass().getClassLoader().getResource(DEFULT_FORMULAS).toURI());
            userPath = Paths.get(getClass().getClassLoader().getResource(USER_FORMULAS).toURI());
        } catch (URISyntaxException ex) {
            Exceptions.printStackTrace(ex);
        }

        logicFormulasTextPane.getDocument().addDocumentListener(new LogicFormulasDocumentListener());

        try {
            if (Files.readAllLines(userPath).isEmpty()) {
                loadLogicalFormulas(defaultPath);
                isDefault = true;
                restoreDefaultButton.setEnabled(!isDefault);
            } else {
                loadLogicalFormulas(userPath);
                isDefault = false;
                restoreDefaultButton.setEnabled(!isDefault);
            }
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jScrollPane1 = new javax.swing.JScrollPane();
        logicFormulasTextPane = new javax.swing.JTextPane();
        selectLogicFormulasButton = new javax.swing.JButton();
        chooseLogicFormulasFileButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        restoreDefaultButton = new javax.swing.JButton();

        setLayout(new java.awt.GridBagLayout());

        jScrollPane1.setViewportView(logicFormulasTextPane);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        add(jScrollPane1, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(selectLogicFormulasButton, org.openide.util.NbBundle.getMessage(LogicFormulasPanel.class, "LogicFormulasPanel.selectLogicFormulasButton.text")); // NOI18N
        selectLogicFormulasButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectLogicFormulasButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 10, 5);
        add(selectLogicFormulasButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(chooseLogicFormulasFileButton, org.openide.util.NbBundle.getMessage(LogicFormulasPanel.class, "LogicFormulasPanel.chooseLogicFormulasFileButton.text")); // NOI18N
        chooseLogicFormulasFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chooseLogicFormulasFileButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        add(chooseLogicFormulasFileButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(cancelButton, org.openide.util.NbBundle.getMessage(LogicFormulasPanel.class, "LogicFormulasPanel.cancelButton.text")); // NOI18N
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 10);
        add(cancelButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(restoreDefaultButton, org.openide.util.NbBundle.getMessage(LogicFormulasPanel.class, "LogicFormulasPanel.restoreDefaultButton.text")); // NOI18N
        restoreDefaultButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                restoreDefaultButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 5);
        add(restoreDefaultButton, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        ((Window) this.getRootPane().getParent()).dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void restoreDefaultButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_restoreDefaultButtonActionPerformed
        loadLogicalFormulas(defaultPath);
        isDefault = true;
        restoreDefaultButton.setEnabled(!isDefault);
    }//GEN-LAST:event_restoreDefaultButtonActionPerformed

    private void chooseLogicFormulasFileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chooseLogicFormulasFileButtonActionPerformed
        final JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int returnVal = fc.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            try {
                writer = Files.newBufferedWriter(userPath);
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            }
            Consumer<String> textConsumer = (String line) -> {
                try {
                    writer.write(line);
                    writer.newLine();
                } catch (IOException ex) {
                    Exceptions.printStackTrace(ex);
                }
            };
            try {
                Files.lines(Paths.get(file.getAbsolutePath())).forEachOrdered(textConsumer);
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            } finally {
                try {
                    writer.close();
                    loadLogicalFormulas(userPath);
                    isDefault = false;
                    restoreDefaultButton.setEnabled(!isDefault);
                } catch (IOException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
        }
    }//GEN-LAST:event_chooseLogicFormulasFileButtonActionPerformed

    private void selectLogicFormulasButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectLogicFormulasButtonActionPerformed
        if (isDefault) {
            // Set parent formula file to defaultFormulas.txt
            parent.setTemporalLogicFormulas(this.logicFormulasTextPane.getText());
            try {
                writer = Files.newBufferedWriter(userPath);
                writer.close();
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            }
        } else {
            try {
                // Set parent formula file to userFormulas.txt
                parent.setTemporalLogicFormulas(this.logicFormulasTextPane.getText());
                writer = Files.newBufferedWriter(userPath);
                writer.write(logicFormulasTextPane.getText());
                writer.close();
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            }
        }

        ((Window) this.getRootPane().getParent()).dispose();
    }//GEN-LAST:event_selectLogicFormulasButtonActionPerformed

    private void loadLogicalFormulas(Path path) {
        logicFormulasTextPane.setText("");
        StyledDocument doc = logicFormulasTextPane.getStyledDocument();
        Consumer<String> textConsumer = (String line) -> {
            try {
                doc.insertString(doc.getLength(), line + "\n", null);
            } catch (BadLocationException ex) {
                Exceptions.printStackTrace(ex);
            }
        };
        try {
            Files.lines(path).forEachOrdered(textConsumer);
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    private class LogicFormulasDocumentListener implements DocumentListener {

        @Override
        public void insertUpdate(DocumentEvent e) {
            isDefault = false;
            restoreDefaultButton.setEnabled(!isDefault);
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            isDefault = false;
            restoreDefaultButton.setEnabled(!isDefault);
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            isDefault = false;
            restoreDefaultButton.setEnabled(!isDefault);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton chooseLogicFormulasFileButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextPane logicFormulasTextPane;
    private javax.swing.JButton restoreDefaultButton;
    private javax.swing.JButton selectLogicFormulasButton;
    // End of variables declaration//GEN-END:variables
}
