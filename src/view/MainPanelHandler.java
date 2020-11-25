package view;

import control.MainController;
import model.File;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;

/**
 * Created by Jean-Pierre on 05.11.2016.
 */
public class MainPanelHandler {
    private JPanel panel;
    private JList shelf01;
    private JList shelf02;
    private JList[] allShelfs;
    private JTextArea outputArea;
    private JButton sortButton01;
    private JButton sortButton02;
    private JButton appendTheShelfButton;
    private JTextField nameTextField;
    private JTextField phoneNrTextField;
    private JButton appendButton01;
    private JButton appendButton02;
    private JButton insertButton01;
    private JButton insertButton02;
    private JButton searchButton;
    private JButton removeButton;
    private JButton suchImInternetButton;
    private JComboBox comboBox1;
    private JTextField editNameField;
    private JTextField editNumberField;
    private MainController controller;

    private int shelfInd, listInd;

    public MainPanelHandler(MainController controller){
        this.controller = controller;
        createButtons();
        createShelfs();
        createFields();
        handleInsertButtons(0, false);
        handleInsertButtons(1, false);
        editNameField.addKeyListener(new KeyAdapter() {
        });
    }

    private void createButtons(){
        sortButton01.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sortAShelf(0);
            }
        });
        sortButton02.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sortAShelf(1);
            }
        });
        appendTheShelfButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                appendShelf(1,0);
            }
        });
        appendButton01.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                appendANewFileTo(0);
            }
        });
        appendButton02.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                appendANewFileTo(1);
            }
        });
        insertButton01.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertANewFileTo(0);
            }
        });
        insertButton02.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertANewFileTo(1);
            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchForFile();
            }
        });
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeSelectedFile();
            }
        });
        suchImInternetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                surchSelectedFile();
            }
        });
    }

    private void createShelfs(){
        allShelfs = new JList[2];
        allShelfs[0] = shelf01;
        allShelfs[1] = shelf02;
        shelf01.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                shelf02.clearSelection();
            }
        });
        shelf02.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                shelf01.clearSelection();
            }
        });
        for(int i = 0; i < allShelfs.length; i++){
            update(i, true);
        }
        shelf01.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                super.mouseClicked(mouseEvent);
                updateEditField(controller.getSelectedFile(0, shelf01.getSelectedIndex()));
                shelfInd = 0;
                listInd = shelf01.getSelectedIndex();
            }
        });
        shelf02.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                super.mouseClicked(mouseEvent);
                updateEditField(controller.getSelectedFile(1, shelf02.getSelectedIndex()));
                shelfInd = 1;
                listInd = shelf02.getSelectedIndex();
            }
        });
    }

    private void createFields(){
        editNameField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent keyEvent) {
                super.keyTyped(keyEvent);
                if(shelfInd == 0) {
                    controller.getSelectedFile(0, listInd).setName(editNameField.getText());
                }else if(shelfInd == 1){
                    controller.getSelectedFile(1, listInd).setName(editNameField.getText());
                }
                update(shelfInd, false);
            }
        });

        editNumberField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent keyEvent) {
                super.keyTyped(keyEvent);
                if(shelfInd == 0) {
                    controller.getSelectedFile(0, listInd).setPhoneNumber(editNumberField.getText());
                }else if(shelfInd == 1){
                    controller.getSelectedFile(1, listInd).setPhoneNumber(editNumberField.getText());
                }
                update(shelfInd, false);
            }
        });
    }

    private void update(int shelfIndex, boolean clearSel){
        //Aktualisierung des gewählten Regals
        DefaultListModel listModel = new DefaultListModel();

        String[] output = controller.showShelfContent(shelfIndex);
        if(output.length > 0) {
            for (int i = 0; i < output.length; i++) {
                String outputText = "Aktenindex: " + i + "\n Familie: " + output[i];
                listModel.addElement(outputText);
            }
        }else{
            String outputText = "Nix drin.";
            listModel.addElement(outputText);
        }

        allShelfs[shelfIndex].setModel(listModel);
        if(clearSel) {
            for (int i = 0; i < allShelfs.length; i++) {
                allShelfs[i].clearSelection();
                updateEditField(null);
            }
        }

    }

    private void handleInsertButtons(int shelfIndex, boolean b){
        switch (shelfIndex){
            case 0:     insertButton01.setEnabled(b);
                break;
            case 1:     insertButton02.setEnabled(b);
        }
    }

    private void sortAShelf(int shelfIndex){
        if(controller.sort(shelfIndex)){
            update(shelfIndex, true);
            handleInsertButtons(shelfIndex, true);
            addTextToOutput("Es wurde das Regal "+shelfIndex+" sortiert.");
        }else{
            addTextToOutput("Sortieren ist fehlgeschlagen.");
        }
    }

    private void appendShelf(int fromShelf, int toShelf){
        if(controller.appendFromTo(fromShelf, toShelf)){
            addTextToOutput("Die Akten wurden aus dem Regal " + (fromShelf+1) + " in das Ragel " + (toShelf+1) + " verschoben.");
        }else{
            addTextToOutput("Es konnten keine Akten verschoben werden.");
        }
        update(fromShelf, true);
        update(toShelf, true);
        handleInsertButtons(fromShelf, false);
        handleInsertButtons(toShelf, false);
    }

    private void appendANewFileTo(int shelfIndex){
        if(controller.appendANewFile(shelfIndex, nameTextField.getText(), phoneNrTextField.getText())){
            addTextToOutput("Es wurde eine Akte für die Familie " + nameTextField.getText() + " angelegt und hinzugefügt.");
            update(shelfIndex, true);
            handleInsertButtons(shelfIndex, false);
        }else{
            addTextToOutput("Es konnte keine neue Akte angelegt und hinzugefügt werden.");
        }
    }

    private void insertANewFileTo(int shelfIndex){
        if(controller.insertANewFile(shelfIndex, nameTextField.getText(), phoneNrTextField.getText())){
            addTextToOutput("Die Akte der Familie " + nameTextField.getText() + " wurde in Regal " + (shelfIndex+1) + " einsortiert.");
        }else{
            addTextToOutput("Das Einfügen einer neuen Akte ist gescheitert.");
        }
        update(shelfIndex, true);
    }

    private void searchForFile(){
        int[] shelfAndIndex = controller.search(nameTextField.getText());
        if(shelfAndIndex[0] == - 1){
            addTextToOutput("Es existiert keine Akte zur Familie " + nameTextField.getText() + ".");
        }else{
            addTextToOutput("Es wurde eine Akte in Regal " + (shelfAndIndex[0]+1) + " gefunden.");
            allShelfs[shelfAndIndex[0]].setSelectedIndex(shelfAndIndex[1]);
        }
    }

    private void removeSelectedFile(){
        if(!shelf01.isSelectionEmpty()){
            String[] info = controller.remove(0,shelf01.getSelectedIndex());
            addTextToOutput("Es wurde aus dem Regal 1 die Akte mit dem Index "+shelf01.getSelectedIndex()+" entfernt. Familie "+info[0]+", Telefonnummer: "+info[1]);
            update(0, true);
        }else if(!shelf02.isSelectionEmpty()){
            String[] info = controller.remove(1,shelf02.getSelectedIndex());
            addTextToOutput("Es wurde aus dem Regal 2 die Akte mit dem Index "+shelf02.getSelectedIndex()+" entfernt. Familie "+info[0]+", Telefonnummer: "+info[1]);
            update(1, true);
        }
    }

    private void surchSelectedFile(){
        if(!shelf01.isSelectionEmpty()){
            String[] info = controller.search(0,shelf01.getSelectedIndex(), comboBox1.getSelectedIndex());
            addTextToOutput("Es wurde aus dem Regal 1 die Akte mit dem Index "+shelf01.getSelectedIndex()+" im Internet gesucht. Familie "+info[0]+", Telefonnummer: "+info[1]);
            update(0, true);
        }else if(!shelf02.isSelectionEmpty()){
            String[] info = controller.search(1,shelf02.getSelectedIndex(), comboBox1.getSelectedIndex());
            addTextToOutput("Es wurde aus dem Regal 2 die Akte mit dem Index "+shelf02.getSelectedIndex()+" im Internet gesucht. Familie "+info[0]+", Telefonnummer: "+info[1]);
            update(1, true);
        }else{
            addTextToOutput("Bitte wählen sie einen Namen zum Suchen aus.");
        }
    }

    private void updateEditField(File file){
        if(file != null) {
            editNameField.setText(file.getName());
            editNumberField.setText(file.getPhoneNumber());
            editNameField.enable();
            editNumberField.enable();
        }else{
            editNameField.setText("");
            editNumberField.setText("");
            editNameField.disable();
            editNumberField.disable();
        }
    }

    public JPanel getPanel(){
        return panel;
    }

    private void addTextToOutput(String textToAdd){
        outputArea.setText(outputArea.getText() + "\n" + textToAdd);
    }
}
