package cs3500.music.view;

import cs3500.music.model.Note;

import java.awt.*;
//import java.awt.event.MouseListener; Possibly of interest for handling mouse events

import javax.swing.*;

/**
 * A skeleton Frame (i.e., a window) in Swing
 */
public class GuiViewFrame extends JFrame implements IMusicView {

  // You may want to refine this to a subtype of JPanel (did that by making it Concrete...)
  private final PitchGuiViewPanel displayPanel;

  /**
   * Creates new GuiView
   */
  public GuiViewFrame() {
    super("Music Player"); // sets the title of the frame
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//    displayPanel = new ConcreteGuiViewPanel();
    displayPanel = new PitchGuiViewPanel();
    displayPanel.setLowestNote(new Note(Note.Pitch.F_SHARP, 0, 2, 3, 1, 3));
    displayPanel.setHighestNote(new Note(Note.Pitch.A, 2, 2, 3, 1, 3));
    getContentPane().add(displayPanel);
//    setLocationRelativeTo(null);
    pack();
  }

  @Override
  public void initialize(){
    this.setVisible(true);
  }

  @Override
  public void update(int time) {
  }

  @Override
  public Dimension getPreferredSize(){
    return new Dimension(1000, 750);
  }
}
