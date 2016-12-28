package org.scrapper;

/**
* Name: Main
* Date: 20-12-2016
* Update: 27-12-2016
* Description: Main class.
*/

public class Main extends Parser{

    @SuppressWarnings({"ResultOfObjectAllocationIgnored"})
    public Main() {
        new GUI().setVisible(true);        
    }

    public static void main(String[] args) {
        Main m = new Main();
    }
}
