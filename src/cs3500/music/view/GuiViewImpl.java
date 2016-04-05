package cs3500.music.view;

import java.awt.*;
import javax.swing.*;
import cs3500.music.model.IMusicModel;
import cs3500.music.model.Note;

/**
 * The visual view for a composition
 */
public class GuiViewImpl extends JFrame implements IGuiView {

  private IMusicModel model;
  private CompositionViewPanel displayPanel;
  private int time;

  /**
   * Creates a new GuiViewImpl
   */
  public GuiViewImpl() {
    super("Music Player"); // sets the title of the frame
    this.time = 0;
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
  }

  @Override
  public void initialize(IMusicModel m) {
    this.model = m;
    this.displayPanel = new CompositionViewPanel(model);
    getContentPane().add(displayPanel);
//    JScrollPane scroll = new JScrollPane(displayPanel);
//    getContentPane().add(scroll);
    pack();
    this.setVisible(true);
    this.setResizable(false);
  }

  @Override
  public void play() {

  }

  @Override
  public void pause() {

  }

  @Override
  public void reset() {
    this.displayPanel.reset();
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
  public void update() {

  }

  @Override
  public void scrollRight() {
    this.displayPanel.shift("right");
  }

  @Override
  public void scrollLeft() {
    this.displayPanel.shift("left");
  }

  @Override
  public void scrollUp() {
    this.displayPanel.shift("up");
  }

  @Override
  public void scrollDown() {
    this.displayPanel.shift("down");
  }

  @Override
  public void goToStart() {

  }

  @Override
  public void goToEnd() {

  }

//  @Override
//  public Dimension getPreferredSize() {
//    return new Dimension(displayPanel.getWidth() + 50, displayPanel.getHeight() + 50);
//  }
}
