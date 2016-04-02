package cs3500.music.view;

import java.awt.*;

import javax.swing.*;

import cs3500.music.model.IMusicModel;

//import java.awt.event.MouseListener; Possibly of interest for handling mouse events

/**
 * The visual view for a composition
 */
public class GuiView extends JFrame implements IMusicView {

  private CompositionViewPanel displayPanel;
  private IMusicModel model;

  /**
   * Creates a new GuiView
   */
  public GuiView() {
    super("Music Player"); // sets the title of the frame
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
  public Dimension getPreferredSize() {
    return new Dimension(1000, 750);
  }
}
