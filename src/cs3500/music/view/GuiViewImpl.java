package cs3500.music.view;

import java.awt.event.MouseListener;
import javax.swing.*;
import cs3500.music.model.IMusicModel;

/**
 * The visual view for a composition
 */
public class GuiViewImpl extends JFrame implements IGuiView {

  private IMusicModel model;
  private CompositionViewPanel displayPanel;
  private int time;
  private final int borderBuffer = 20;

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
    this.setResizable(false);
    pack();
    this.setSize(this.getWidth() + this.borderBuffer, this.getHeight() + this.borderBuffer);
    this.setVisible(true);
  }

  // TODO: ADD RED LINE!

  @Override
  public void play() {

  }

  @Override
  public void pause() {

  }

  @Override
  public void reset() {
    this.displayPanel.reset();
    this.repaint();
  }

  @Override
  public void addMouseListener(MouseListener m) {
    this.displayPanel.addMouseListener(m);
  }

  @Override
  public int getPitchAtCursor() {
    return this.displayPanel.getPitchAtCursor(this.borderBuffer);
  }

  @Override
  public int getBeatAtCursor() {
    return this.displayPanel.getBeatAtCursor(this.borderBuffer);
  }

  @Override
  public void update() {
    this.displayPanel.updatePanel();
    this.repaint();
  }

  @Override
  public void scrollRight() {
    this.displayPanel.shift("right");
    this.repaint();
  }

  @Override
  public void scrollLeft() {
    this.displayPanel.shift("left");
    this.repaint();
  }

  @Override
  public void scrollUp() {
    this.displayPanel.shift("up");
    this.repaint();
  }

  @Override
  public void scrollDown() {
    this.displayPanel.shift("down");
    this.repaint();
  }

  @Override
  public void goToEnd() {
    this.displayPanel.goToEnd();
    this.repaint();
  }
}
