package cs3500.music.view;

import java.awt.*;

import javax.swing.*;

import cs3500.music.model.IMusicModel;
import cs3500.music.model.Note;

//import java.awt.event.MouseListener; Possibly of interest for handling mouse events

/**
 * The visual view for a composition
 */
public class GuiView extends JFrame implements IGuiView {

  private CompositionViewPanel displayPanel;
  private IMusicModel model;
  private int time;

  /**
   * Creates a new GuiView
   */
  public GuiView() {
    super("Music Player"); // sets the title of the frame
    this.time = 0;
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
  }

  @Override
  public void initialize(IMusicModel m) {
    this.model = m;
    this.displayPanel = new CompositionViewPanel(model);
    JScrollPane scroll = new JScrollPane(displayPanel);
    getContentPane().add(scroll);
    pack();
    this.setPreferredSize(this.getPreferredSize());
    this.setVisible(true);
  }

  @Override
  public void play() {

  }

  @Override
  public void pause() {

  }

  @Override
  public void reset() {

  }

  @Override
  public Note.Pitch getPitchAt(int x, int y) {
    return null;
  }

  @Override
  public int getBeatAt(int x, int y) {
    return 0;
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(1000, 750);
  }
}
