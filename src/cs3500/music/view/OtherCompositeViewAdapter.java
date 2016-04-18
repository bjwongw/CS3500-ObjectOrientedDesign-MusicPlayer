package cs3500.music.view;

import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import cs3500.music.model.IMusicModel;
import cs3500.music.model.IMusicModelToIMusicAdapter;
import cs3500.music.other.view.CompositeView;

import javax.sound.midi.InvalidMidiDataException;

/**
 * Adapts a CompositeView (from other.view) class to a GuiView
 */
public class OtherCompositeViewAdapter extends CompositeView implements GuiView {
  IMusicModel m;
  int scrollOffsetX = 0;
  int scrollOffsetY = 0;

  /**
   * Constructs an OtherCompositeViewAdapter
   */
  public OtherCompositeViewAdapter() {
    super();
  }

  @Override
  public int getPitchAtCursor() {
    System.out.printf("x: %d, y: %d\n", this.getCursorPostion().x, this.getCursorPostion().y);
    return m.getHighestNote().getMidiPitch() - ((this.getCursorPostion().y - 60 + scrollOffsetY)
            / 20);
  }

  @Override
  public int getBeatAtCursor() {
    return (this.getCursorPostion().x - 50 + scrollOffsetX) / 20;
  }

  @Override
  public void update() {

  }

  @Override
  public void scrollRight() {
    scrollOffsetX += 50;
    this.scrollright();
  }

  @Override
  public void scrollLeft() {
    scrollOffsetX -= 50;
    this.scrollleft();
  }

  @Override
  public void scrollUp() {
    scrollOffsetY -= 50;
    this.scrollup();
  }

  @Override
  public void scrollDown() {
    scrollOffsetY += 50;
    this.scrolldown();
  }

  @Override
  public void goToStart() {
    this.scrollBegin();
  }

  @Override
  public void goToEnd() {
    this.scrollEnd();
  }

  @Override
  public void moveBeatIndicator() {

  }

  @Override
  public void initialize(IMusicModel m) {
    this.m = m;
    try {
      this.initialize(new IMusicModelToIMusicAdapter(m));
    } catch (InterruptedException | InvalidMidiDataException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void play() {
    this.resume();
  }

  @Override
  public void pause() {
    this.stop();
  }

  @Override
  public void reset() {
    this.pause();
    this.goToStart();
  }

  @Override
  public boolean addTickHandler(Runnable r) {
    return false;
  }
}
