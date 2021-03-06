package model;

/**
 * Created by Jean-Pierre on 05.11.2016.
 */
public class List<ContentType> {

    //TODO 01: Fertigstellen der Liste
  /* --------- Anfang der privaten inneren Klasse -------------- */

    private class ListNode {

        private ContentType contentObject;
        private ListNode next;

        /**
         * Ein neues Objekt wird erschaffen. Der Verweis ist leer.
         *
         * @param pContent das Inhaltsobjekt vom Typ ContentType
         */
        private ListNode(ContentType pContent) {
            contentObject = pContent;
            next = null;
        }

        /**
         * Der Inhalt des Knotens wird zurueckgeliefert.
         *
         * @return das Inhaltsobjekt des Knotens
         */
        public ContentType getContentObject() {
            return contentObject;
        }

        /**
         * Der Inhalt dieses Kontens wird gesetzt.
         *
         * @param pContent das Inhaltsobjekt vom Typ ContentType
         */
        public void setContentObject(ContentType pContent) {
            contentObject = pContent;
        }

        /**
         * Der Nachfolgeknoten wird zurueckgeliefert.
         *
         * @return das Objekt, auf das der aktuelle Verweis zeigt
         */
        public ListNode getNextNode() {
            return this.next;
        }

        /**
         * Der Verweis wird auf das Objekt, das als Parameter uebergeben
         * wird, gesetzt.
         *
         * @param pNext der Nachfolger des Knotens
         */
        public void setNextNode(ListNode pNext) {
            this.next = pNext;
        }

    }

  /* ----------- Ende der privaten inneren Klasse -------------- */

    // erstes Element der Liste
    ListNode first;

    // letztes Element der Liste
    ListNode last;

    // aktuelles Element der Liste
    ListNode current;

    /**
     * Eine leere Liste wird erzeugt.
     */
    public List() {
        first = null;
        last = null;
        current = null;
    }

    /**
     * Die Anfrage liefert den Wert true, wenn die Liste keine Objekte enthaelt,
     * sonst liefert sie den Wert false.
     *
     * @return true, wenn die Liste leer ist, sonst false
     */
    public boolean isEmpty() {
        //TODO 01a: Die Liste ist leer, wenn es kein erstes Element gibt.
        if(first == null){
            return true;
        }
        return false;
    }

    /**
     * Die Anfrage liefert den Wert true, wenn es ein aktuelles Objekt gibt,
     * sonst liefert sie den Wert false.
     *
     * @return true, falls Zugriff moeglich, sonst false
     */
    public boolean hasAccess() {
        //TODO 01b: Es gibt keinen Zugriff, wenn current auf kein Element verweist.
        if(current != null){
            return true;
        }
        return false;
    }

    /**
     * Falls die Liste nicht leer ist, es ein aktuelles Objekt gibt und dieses
     * nicht das letzte Objekt der Liste ist, wird das dem aktuellen Objekt in
     * der Liste folgende Objekt zum aktuellen Objekt, andernfalls gibt es nach
     * Ausfuehrung des Auftrags kein aktuelles Objekt, d.h. hasAccess() liefert
     * den Wert false.
     */
    public void next() {
        //TODO 01c: Wechsel auf die nächste Node
        if(!isEmpty() && hasAccess() && current != last){
            current = current.getNextNode();
        }else{
            current = null;
        }
    }

    /**
     * Falls die Liste nicht leer ist, wird das erste Objekt der Liste aktuelles
     * Objekt. Ist die Liste leer, geschieht nichts.
     */
    public void toFirst() {
        //TODO 01d: Sprung zur ersten Node
        if(!isEmpty()){
            current = first;
        }
    }

    /**
     * Falls die Liste nicht leer ist, wird das letzte Objekt der Liste
     * aktuelles Objekt. Ist die Liste leer, geschieht nichts.
     */
    public void toLast() {
        //TODO 01e: Sprung auf die letzte Node
        if(!isEmpty()){
            current = last;
        }
    }

    /**
     * Falls es ein aktuelles Objekt gibt (hasAccess() == true), wird das
     * aktuelle Objekt zurueckgegeben, andernfalls (hasAccess() == false) gibt
     * die Anfrage den Wert null zurueck.
     *
     * @return das aktuelle Objekt (vom Typ ContentType) oder null, wenn es
     *         kein aktuelles Objekt gibt
     */
    public ContentType getContent() {
        if(hasAccess()){
            return current.getContentObject();
        }
        return null;
    }

    /**
     * Falls es ein aktuelles Objekt gibt (hasAccess() == true) und pContent
     * ungleich null ist, wird das aktuelle Objekt durch pContent ersetzt. Sonst
     * geschieht nichts.
     *
     * @param pContent
     *            das zu schreibende Objekt vom Typ ContentType
     */
    public void setContent(ContentType pContent) {
        // Nichts tun, wenn es keinen Inhalt oder kein aktuelles Element gibt.
        //TODO 01f: Inhaltsobjekt ersetzen
        if(hasAccess() && pContent != null){
            current.setContentObject(pContent);
        }
    }

    /**
     * Falls es ein aktuelles Objekt gibt (hasAccess() == true), wird ein neues
     * Objekt vor dem aktuellen Objekt in die Liste eingefuegt. Das aktuelle
     * Objekt bleibt unveraendert. <br />
     * Wenn die Liste leer ist, wird pContent in die Liste eingefuegt und es
     * gibt weiterhin kein aktuelles Objekt (hasAccess() == false). <br />
     * Falls es kein aktuelles Objekt gibt (hasAccess() == false) und die Liste
     * nicht leer ist oder pContent gleich null ist, geschieht nichts.
     *
     * @param pContent
     *            das einzufuegende Objekt vom Typ ContentType
     */
    public void insert(ContentType pContent) {
        //TODO 01g: Inhaltsobjekt einfügen
        if(pContent != null) {
            ListNode node = new ListNode(pContent);
            if(hasAccess()) {
                if (current == first) {
                    first = node;
                } else {
                    getPrevious(current).setNextNode(node);
                }
                node.setNextNode(current);
            }else if(isEmpty()){
                first = node;
                last = node;
            }
        }
    }

    /**
     * Falls pContent gleich null ist, geschieht nichts.<br />
     * Ansonsten wird ein neues Objekt pContent am Ende der Liste eingefuegt.
     * Das aktuelle Objekt bleibt unveraendert. <br />
     * Wenn die Liste leer ist, wird das Objekt pContent in die Liste eingefuegt
     * und es gibt weiterhin kein aktuelles Objekt (hasAccess() == false).
     *
     * @param pContent
     *            das anzuhaengende Objekt vom Typ ContentType
     */
    public void append(ContentType pContent) {
        //TODO 01h: Inhaltsobjekt anhängen
        if(pContent != null){
            ListNode node = new ListNode(pContent);
            if(!isEmpty()) {
                last.setNextNode(node);
                last = last.getNextNode();
            }else{
                first = node;
                last = node;
                current = null;
            }
        }
    }

    /**
     * Falls es sich bei der Liste und pList um dasselbe Objekt handelt,
     * pList null oder eine leere Liste ist, geschieht nichts.<br />
     * Ansonsten wird die Liste pList an die aktuelle Liste angehaengt.
     * Anschliessend wird pList eine leere Liste. Das aktuelle Objekt bleibt
     * unveraendert. Insbesondere bleibt hasAccess identisch.
     *
     * @param pList
     *            die am Ende anzuhaengende Liste vom Typ List<ContentType>
     */
    public void concat(List<ContentType> pList) {
        //TODO 01i: eine Liste an eine andere anhängen
        if(pList != this && pList != null && !pList.isEmpty()){
            if(!isEmpty()) {
                last.setNextNode(pList.first);
            }else{
                first = pList.first;
            }
            last = pList.last;

            pList.first = null;
            pList.last = null;
            pList.current = null;
        }
    }

    /**
     * Wenn die Liste leer ist oder es kein aktuelles Objekt gibt (hasAccess()
     * == false), geschieht nichts.<br />
     * Falls es ein aktuelles Objekt gibt (hasAccess() == true), wird das
     * aktuelle Objekt geloescht und das Objekt hinter dem geloeschten Objekt
     * wird zum aktuellen Objekt. <br />
     * Wird das Objekt, das am Ende der Liste steht, geloescht, gibt es kein
     * aktuelles Objekt mehr.
     */
    public void remove() {
        // Nichts tun, wenn es kein aktuelles Element gibt oder die Liste leer ist.
        //TODO 01j: eine Node samt Inhaltsobjekt entfernen
        if(!isEmpty() && hasAccess()){
            if(current != first && current != last){
                getPrevious(current).setNextNode(current.getNextNode());
                current = current.getNextNode();
            }else if(current == first && current != last){
                first = first.getNextNode();
                current = current.getNextNode();
            }else if(current != first && current == last){
                last = getPrevious(last);
                last.setNextNode(null);
                current = null;
            }else{
                first = null;
                last = null;
                current = null;
            }
        }
    }

    /**
     * Liefert den Vorgaengerknoten des Knotens pNode. Ist die Liste leer, pNode
     * == null, pNode nicht in der Liste oder pNode der erste Knoten der Liste,
     * wird null zurueckgegeben.
     *
     * @param pNode
     *         der Knoten, dessen Vorgaenger zurueckgegeben werden soll
     * @return der Vorgaenger des Knotens pNode oder null, falls die Liste leer ist,
     *         pNode == null ist, pNode nicht in der Liste ist oder pNode der erste Knoten
     *         der Liste ist
     */
    private ListNode getPrevious(ListNode pNode) {
        //TODO 01k: Vorgänger-Node der aktuellen Node liefern.
        if(!isEmpty() && pNode != null && pNode != first) {
            ListNode node = first;
            while(node != null && node.getNextNode() != pNode){
                node = node.getNextNode();
            }
            return node;
        }
        return null;
    }

}
