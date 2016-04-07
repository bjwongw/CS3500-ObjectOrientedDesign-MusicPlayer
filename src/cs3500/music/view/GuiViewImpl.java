package cs3500.music.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import javax.swing.*;
import cs3500.music.model.IMusicModel;

/**
 * The visual view for a composition
 */
public class GuiViewImpl extends JFrame implements GuiView {

  private final static int timerTimeInMilliseconds = 1000;
  private final int borderBuffer = 20;
  private IMusicModel model;
  private ConcreteGuiView displayPanel;
  private int currentTime;
  private Timer timer;
  private boolean isPlaying;

  /**
   * Creates a new GuiViewImpl
   */
  public GuiViewImpl() {
    super("Music Player"); // sets the title of the frame
    this.currentTime = 0;
    this.isPlaying = false;
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
    this.timer = new Timer(model.getTempo() / timerTimeInMilliseconds, new ActionListener() {
      @Override public void actionPerformed(ActionEvent e) {
        if (isPlaying) {
          currentTime += 1;
//          displayPanel.adjustRedLine(currentTime);
          repaint();
        }
      }
    });
    timer.setInitialDelay(0);
  }

  // TODO: ADD RED LINE!

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
    this.timer.restart();
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
  public void goToStart() {

  }

  @Override
  public void goToEnd() {
    this.displayPanel.goToEnd();
    this.repaint();
  }
}
