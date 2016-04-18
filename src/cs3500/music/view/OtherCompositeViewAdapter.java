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

  /**
   * Constructs an OtherCompositeViewAdapter
   */
  public OtherCompositeViewAdapter() {
    super();
  }

  @Override
  public int getPitchAtCursor() {
    return 0;
  }

  @Override
  public int getBeatAtCursor() {
    return 0;
  }

  @Override
  public void update() {

  }

  @Override
  public void scrollRight() {
    this.scrollright();
  }

  @Override
  public void scrollLeft() {
    this.scrollleft();
  }

  @Override
  public void scrollUp() {
    this.scrollup();
  }

  @Override
  public void scrollDown() {
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
