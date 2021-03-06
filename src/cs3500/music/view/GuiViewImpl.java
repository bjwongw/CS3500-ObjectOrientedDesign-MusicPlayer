package cs3500.music.view;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import javax.swing.*;
import cs3500.music.model.IMusicModel;

/**
 * The GUI view for a composition
 */
public class GuiViewImpl extends JFrame implements GuiView {

  // the extra buffer surrounding any components this frame holds
  protected final int borderBuffer = 20;

  // invariant: this will always be a positive integer
  protected int currentTime = 0;

  protected IMusicModel model;
  protected ConcreteGuiView displayPanel;

  protected MouseListener mouseListener;
  private KeyListener keyListener;

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
    this.displayPanel = new ConcreteGuiView(model, borderBuffer, borderBuffer);
    getContentPane().add(displayPanel);
    this.setResizable(false);
    pack();
    this.setSize(this.getWidth() + this.borderBuffer, this.getHeight() + this.borderBuffer);
    this.setVisible(true);
    this.displayPanel.addMouseListener(this.mouseListener);
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
    this.mouseListener = m;
  }

  @Override
  public int getPitchAtCursor() {
    return this.displayPanel.getPitchAtCursor();
  }

  @Override
  public int getBeatAtCursor() {
    return this.displayPanel.getBeatAtCursor();
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
