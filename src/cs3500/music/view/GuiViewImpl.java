package cs3500.music.view;

import java.awt.event.MouseListener;
import javax.swing.*;
import cs3500.music.model.IMusicModel;

/**
 * The visual view for a composition
 */
public class GuiViewImpl extends JFrame implements GuiView {

  private final static int timerTimeInMilliseconds = 1000;
  private final int borderBuffer = 20;
  private int currentTime = 0;
  private boolean isPlaying = false;
  private IMusicModel model;
  private ConcreteGuiView displayPanel;
  private Timer timer;

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
    this.timer = new Timer(model.getTempo() / timerTimeInMilliseconds, e -> {
      if (isPlaying) {
        currentTime += 1;
        displayPanel.setBeatBar(currentTime);
      }
    });
    timer.setInitialDelay(0);
  }

  @Override
  public void play() {
    this.isPlaying = true;
    this.timer.start();
  }

  @Override
  public void pause() {
    this.isPlaying = false;
    this.timer.stop();
  }

  @Override
  public void reset() {
    this.isPlaying = false;
    this.displayPanel.reset();
    this.currentTime = 0;
    this.timer.restart();
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
    this.displayPanel.setBeatBar(this.currentTime + 1);
    this.currentTime += 1;
    this.validate();
    this.repaint();
  }
}
