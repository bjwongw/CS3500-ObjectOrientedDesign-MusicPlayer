package cs3500.music.view;

import cs3500.music.model.IMusicModel;
import cs3500.music.model.Note;

import java.awt.*;
//import java.awt.event.MouseListener; Possibly of interest for handling mouse events

import javax.swing.*;

/**
 * The visual view
 */
public class GuiView extends JFrame implements IMusicView {



  // You may want to refine this to a subtype of JPanel (did that by making it Concrete...)
  private final GuiViewPanel displayPanel;
  private final IMusicModel model;

  /**
   * Creates new GuiView
   */
  public GuiView(IMusicModel model) {
    super("Music Player"); // sets the title of the frame
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    this.model = model;
    this.displayPanel = new GuiViewPanel(model);
    JScrollPane scroll = new JScrollPane(displayPanel);
    getContentPane().add(scroll);
//    setLocationRelativeTo(null);
    pack();
  }

  @Override
  public void initialize(){
    this.setVisible(true);
  }

  @Override
  public void update(int beat) {
  }

  @Override
  public Dimension getPreferredSize(){
    return new Dimension(1000, 750);
  }
}
