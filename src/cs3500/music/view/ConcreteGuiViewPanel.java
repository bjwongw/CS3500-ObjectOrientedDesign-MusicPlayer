package cs3500.music.view;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import javax.swing.*;

/**
 * A dummy view that simply draws a string 
 */
public class ConcreteGuiViewPanel extends JPanel {

  private static final int PREF_W = 125;

  @Override
  public void paintComponent(Graphics g){
    // Handle the default painting
    super.paintComponent(g);

    // Look for more documentation about the Graphics class,
    // and methods on it that may be useful
    g.drawString("Hello World", 25, 25);
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(PREF_W, PREF_W);
  }

}
