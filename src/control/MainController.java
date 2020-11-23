package control;

import model.File;
import model.List;
import sun.net.URLCanonicalizer;

import javax.swing.text.Document;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by Jean-Pierre on 05.11.2016.
 */
public class MainController {

    private List<File>[] allShelves;    //Ein Array, das Objekte der Klasse Liste verwaltet, die wiederum Objekte der Klasse File verwalten.

    public MainController(){
        allShelves = new List[2];
        allShelves[0] = new List<File>(); //Beachtet die unterschiedliche Instanziierung! Was bedeutet das?
        allShelves[1] = new List<>();
        createFiles();
    }

    /**
     * Die Akten eines Regals werden vollständig ausgelesen.
     * @param index Regalnummer
     * @return String-Array mit den Familiennamen
     */
    public String[] showShelfContent(int index){
        //TODO 03: Ausgabe der Inhalte
        int count = 0;
        allShelves[index].toFirst();
        while(allShelves[index].hasAccess()){
            count++;
            allShelves[index].next();
        }

        String[] output = new String[count];
        allShelves[index].toFirst();
        for(int i = 0; i < output.length; i++) {
            output[i] = allShelves[index].getContent().getName();
            allShelves[index].next();
        }
        return output;
    }

    /**
     * Ein Regal wird nach Familiennamen aufsteigend sortiert.
     * @param index Regalnummer des Regals, das sortiert werden soll.
     * @return true, falls die Sortierung geklappt hat, sonst false.
     */
    public boolean sort(int index){
        //TODO 07: Sortieren einer Liste.
        if(!allShelves[index].isEmpty()) {
            int count = 0;
            allShelves[index].toFirst();
            while (allShelves[index].hasAccess()) {
                count++;
                allShelves[index].next();
            }

            for (int i = 0; i < count; i++) {
                allShelves[index].toFirst();
                int inde = 0;
                String string = null;
                File content = null;
                for (int j = 0; j < count && allShelves[index].hasAccess(); j++) {
                    if(j == i){
                        inde = j;
                        string = allShelves[index].getContent().getName();
                        content = allShelves[index].getContent();
                    }
                    if (j>i && allShelves[index].getContent().getName().compareToIgnoreCase(string) < 0) {
                        string = allShelves[index].getContent().getName();
                        content = allShelves[index].getContent();
                        inde = j;
                    }
                    allShelves[index].next();
                }

                allShelves[index].toFirst();
                for (int j = 0; j <= inde; j++) {
                    if (j == i) {
                        allShelves[index].insert(content);
                    }else {
                        allShelves[index].next();
                    }
                }
                allShelves[index].remove();
            }
            return true;
        }
        return false;
    }

    /**
     * Die gesammte Aktensammlung eines Regals wird zur Aktensammlung eines anderen Regals gestellt.
     * @param from Regalnummer, aus dem die Akten genommen werden. Danach sind in diesem Regal keine Akten mehr.
     * @param to Regalnummer, in das die Akten gestellt werden.
     * @return true, falls alles funktionierte, sonst false.
     */
    public boolean appendFromTo(int from, int to){
        //TODO 04: Die Objekte einer Liste an eine andere anhängen und dabei die erste Liste leeren.
        if(!allShelves[from].isEmpty()){
            allShelves[to].concat(allShelves[from]);
            return true;
        }
        return false;
    }

    /**
     * Es wird eine neue Akte erstellt und einem bestimmten Regal hinzugefügt.
     * @param index Regalnummer
     * @param name Name der Familie
     * @param phoneNumber Telefonnummer der Familie
     * @return true, falls das Hinzufügen geklappt hat, sonst false.
     */
    public boolean appendANewFile(int index, String name, String phoneNumber){
        //TODO 02: Hinzufügen einer neuen Akte am Ende der Liste.
        if(allShelves[index] != null) {
            File file = new File(name, phoneNumber);
            allShelves[index].append(file);
            return true;
        }
        return false;
    }

    /**
     * Es wird eine neue Akte in ein Regal eingefügt. Funktioniert nur dann sinnvoll, wenn das Regal vorher bereits nach Namen sortiert wurde.
     * @param index Regalnummer, in das die neue Akte einsortiert werden soll.
     * @param name Name der Familie
     * @param phoneNumber Telefonnummer der Familie
     * @return true, falls das Einfügen geklappt hat, sonst false.
     */
    public boolean insertANewFile(int index, String name, String phoneNumber){
        //TODO 08: Einfügen einer neuen Akte an die richtige Stelle innerhalb der Liste.
        if(allShelves[index] != null && !name.equals("") && !phoneNumber.equals("")) {
            int count = 0;
            allShelves[index].toFirst();
            while (allShelves[index].hasAccess()) {
                count++;
                allShelves[index].next();
            }

            allShelves[index].toFirst();
            for(int i = 0; i<count; i++){
                if(allShelves[index].getContent().getName().compareTo(name) > 0){
                    File file = new File(name, phoneNumber);
                    allShelves[index].insert(file);
                    return true;
                }
                allShelves[index].next();
            }
            File file = new File(name, phoneNumber);
            allShelves[index].append(file);
            return true;
        }
        return false;
    }

    /**
     * Es wird nach einer Akte gesucht.
     * @param name Familienname, nach dem gesucht werden soll.
     * @return Zahlen-Array der Länge 2. Bei Index 0 wird das Regal, bei Index 1 die Position der Akte angegeben. Sollte das Element - also die Akte zum Namen - nicht gefunden werden, wird {-1,-1} zurückgegeben.
     */
    public int[] search(String name){
        //TODO 05: Suchen in einer Liste.
        for(int i = 0; i<allShelves.length; i++){
            int count = 0;
            allShelves[i].toFirst();
            while(allShelves[i].hasAccess() && !allShelves[i].getContent().getName().equals(name)){
                count++;
                allShelves[i].next();
            }
            if(allShelves[i].hasAccess() && allShelves[i].getContent().getName().equals(name)){
                return new int[]{i, count};
            }
        }
        return new int[]{-1,-1};
    }

    /**
     * Eine Akte wird entfernt. Dabei werden die enthaltenen Informationen ausgelesen und zurückgegeben.
     * @param shelfIndex Regalnummer, aus dem die Akte entfernt wird.
     * @param fileIndex Aktennummer, die entfernt werden soll.
     * @return String-Array der Länge 2. Index 0 = Name, Indedx 1 = Telefonnummer.
     */
    public String[] remove(int shelfIndex, int fileIndex){
        //TODO 06: Entfernen aus einer Liste.
        allShelves[shelfIndex].toFirst();
        for(int i = 0; i<fileIndex && allShelves[shelfIndex].hasAccess(); i++) {
            allShelves[shelfIndex].next();
        }
        if(allShelves[shelfIndex].hasAccess()) {
            String[] output = new String[]{allShelves[shelfIndex].getContent().getName(), allShelves[shelfIndex].getContent().getPhoneNumber()};
            allShelves[shelfIndex].remove();
            return output;
        }
        return new String[]{"Nicht vorhanden","Nicht vorhanden"};
    }

    public String[] search(int shelfIndex, int fileIndex, int website){
        //TODO 06: Entfernen aus einer Liste.
        allShelves[shelfIndex].toFirst();
        for(int i = 0; i<fileIndex && allShelves[shelfIndex].hasAccess(); i++) {
            allShelves[shelfIndex].next();
        }
        if(allShelves[shelfIndex].hasAccess()) {
            String[] output = new String[]{allShelves[shelfIndex].getContent().getName(), allShelves[shelfIndex].getContent().getPhoneNumber()};
            String searchString = allShelves[shelfIndex].getContent().getName().replace(" ", "+");

            switch (website) {
                case 0:
                    try {
                        URI domain = new URI("https://www.google.com/search?q=" + searchString);
                        Desktop.getDesktop().browse(domain);
                    } catch (IOException | URISyntaxException e) {
                        e.printStackTrace();
                    }
                    break;
                case 1:
                    try {
                        URI domain = new URI("https://de.wikipedia.org/w/index.php?search=" + searchString);
                        Desktop.getDesktop().browse(domain);
                    } catch (IOException | URISyntaxException e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    try {
                        URI domain = new URI("https://www.google.com/search?q=" + searchString);
                        Desktop.getDesktop().browse(domain);
                    } catch (IOException | URISyntaxException e) {
                        e.printStackTrace();
                    }
                    break;
            }

            return output;
        }
        return new String[]{"Nicht vorhanden","Nicht vorhanden"};
    }

    public File getSelectedFile(int shelfIndex, int fileIndex){
        allShelves[shelfIndex].toFirst();
        for(int i = 0; i<fileIndex && allShelves[shelfIndex].hasAccess(); i++) {
            allShelves[shelfIndex].next();
        }
        if(allShelves[shelfIndex].hasAccess()) {
            return allShelves[shelfIndex].getContent();
        }
        return null;
    }

    /**
     * Es werden 14 zufällige Akten angelegt und zufällig den Regalen hinzugefügt.
     */
    private void createFiles(){
        for(int i = 0; i < 14; i++){
            int shelfIndex = (int)(Math.random()*allShelves.length);

            int nameLength = (int)(Math.random()*5)+3;
            String name = "";
            for(int j = 0; j < nameLength; j++){
                name = name + (char) ('A' + (int)(Math.random()*26));
            }

            int phoneLength = (int)(Math.random()*2)+8;
            String phone = "0";
            for (int k = 1; k < phoneLength; k++){
                phone = phone + (int)(Math.random()*10);
            }

            appendANewFile(shelfIndex,name,phone);
        }
    }

}
