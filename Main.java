package life;

import javax.swing.*;

public class Main {
    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeAndWait(GameOfLife::new);
    }
}


