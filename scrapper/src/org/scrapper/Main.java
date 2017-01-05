package org.scrapper;

import javax.swing.SwingUtilities;

/**
* Name: Main
* Date: 20-12-2016
* Update: 05-01-2017
* Description: Start class.
*/

public class Main{
    public static void main(String[] args) {
        SwingUtilities.invokeLater(
            () -> new GUI().setVisible(true) 
        );
    }
}
