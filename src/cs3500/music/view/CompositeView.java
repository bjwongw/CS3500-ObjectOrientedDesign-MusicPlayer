package cs3500.music.view;

import cs3500.music.model.IMusicModel;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

/**
 * A view that has both a visual and audio component.
 */
// TODO review MIDI functionality for the method implemented here
public class CompositeView implements GuiView {
  private final View midiViewImpl;
  private final GuiView guiViewImpl;

  /**
   * Constructs a CompositeView
   * @param midiViewImpl the MIDI view (audio portion of the composite view)
   * @param guiViewImpl the GUI view (visual portion of the composite view)
   */
  public CompositeView(View midiViewImpl, GuiView guiViewImpl) {
    this.midiViewImpl = midiViewImpl;
    this.guiViewImpl = guiViewImpl;
  }

  @Override
  public void initialize(IMusicModel m) {
    this.midiViewImpl.initialize(m);
    this.guiViewImpl.initialize(m);
  }

  @Override
  public void play() {
    this.midiViewImpl.play();
    this.guiViewImpl.play();
  }

  @Override
  public void pause() {
    this.midiViewImpl.pause();
    this.guiViewImpl.pause();
  }

  @Override
  public void reset() {
    this.midiViewImpl.reset();
    this.guiViewImpl.reset();
  }

  @Override
  public void addKeyListener(KeyListener k) {
//    this.midiViewImpl.addKeyListener(k);
    this.guiViewImpl.addKeyListener(k);
  }

  @Override
  public void addMouseListener(MouseListener m) {
    this.guiViewImpl.addMouseListener(m);
  }

  @Override
  public int getPitchAtCursor() {
    return this.guiViewImpl.getPitchAtCursor();
  }

  @Override public int getBeatAtCursor() {
    return this.guiViewImpl.getBeatAtCursor();
  }

  @Override
  public void update() {
//    this.midiViewImpl.update();
    this.guiViewImpl.update();
  }

  @Override
  public void scrollRight() {
    this.guiViewImpl.scrollRight();
  }

  @Override
  public void scrollLeft() {
    this.guiViewImpl.scrollLeft();
  }

  @Override
  public void scrollUp() {
    this.guiViewImpl.scrollUp();
  }

  @Override
  public void scrollDown() {
    this.guiViewImpl.scrollDown();
  }

  @Override
  public void goToStart() {
    this.guiViewImpl.goToStart();
  }

  @Override
  public void goToEnd() {
    this.guiViewImpl.goToEnd();
  }
}
