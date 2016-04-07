package cs3500.music.view;

import java.awt.event.MouseListener;
import javax.swing.*;
import cs3500.music.model.IMusicModel;

/**
 * The visual view for a composition
 */
public class GuiViewImpl extends JFrame implements GuiView {

  // the extra buffer surrounding any components this frame holds
  private final int borderBuffer = 20;

  // invariant: this will always be a positive integer
  private int currentTime = 0;

  private IMusicModel model;
  private ConcreteGuiView displayPanel;

  /**
   * Creates a new GuiViewImpl
   */
  public GuiViewImpl() {
    super("Music Player"); // sets the title of the frame
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
  }

  @Override
  public void initialize(IMusicModel m) {
    this.model = m;
    this.displayPanel = new ConcreteGuiView(model);
    getContentPane().add(displayPanel);
    this.setResizable(false);
    pack();
    this.setSize(this.getWidth() + this.borderBuffer, this.getHeight() + this.borderBuffer);
    this.setVisible(true);
  }

  @Override
  public void play() { }

  @Override
  public void pause() { }

  @Override
  public void reset() {
    this.currentTime = 0;
    this.displayPanel.reset();
    this.validate();
    this.repaint();
  }

  @Override
  public boolean addTickHandler(Runnable r) {
    return false;
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
    this.validate();
    this.repaint();
  }

  @Override
  public void scrollRight() {
    this.displayPanel.shift("right");
    this.validate();
    this.repaint();
  }

  @Override
  public void scrollLeft() {
    this.displayPanel.shift("left");
    this.validate();
    this.repaint();
  }

  @Override
  public void scrollUp() {
    this.displayPanel.shift("up");
    this.validate();
    this.repaint();
  }

  @Override
  public void scrollDown() {
    this.displayPanel.shift("down");
    this.validate();
    this.repaint();
  }

  @Override
  public void goToStart() {
    this.displayPanel.reset();
    this.validate();
    this.repaint();
  }

  @Override
  public void goToEnd() {
    this.displayPanel.goToEnd();
    this.validate();
    this.repaint();
  }

  @Override
  public void moveBeatIndicator() {
    this.currentTime += 1;
    this.displayPanel.setBeatBar(this.currentTime);
    this.validate();
    this.repaint();
  }
}
