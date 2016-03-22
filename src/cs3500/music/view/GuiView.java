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

  private GuiViewPanel displayPanel;
  private IMusicModel model;

  /**
   * Creates new GuiView
   */
  public GuiView() {
    super("Music Player"); // sets the title of the frame
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
  }

  @Override
  public void initialize(IMusicModel m){
    this.model = m;
    this.displayPanel = new GuiViewPanel(model);
    JScrollPane scroll = new JScrollPane(displayPanel);
    getContentPane().add(scroll);
    pack();
    this.setPreferredSize(this.getPreferredSize());
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
