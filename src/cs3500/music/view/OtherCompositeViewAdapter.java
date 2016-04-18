package cs3500.music.view;

import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import cs3500.music.model.IMusicModel;
import cs3500.music.other.view.*;
import cs3500.music.other.view.CompositeView;

/**
 * Created by alex on 4/18/2016.
 */
public class OtherCompositeViewAdapter extends CompositeView implements GuiView {

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
    this.initialize(new IMusicModelToIMusicAdapter(m));
  }

  @Override
  public void play() {
    this.resume();
  }

  @Override
  public void pause() {
    this.pause();
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
