package cs3500.music.view;

import cs3500.music.model.IMusicModel;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import javax.sound.midi.MidiUnavailableException;

/**
 * A view that has both a visual and audio component.
 */
public class CompositeView implements GuiView {
  private final View midiViewImpl;
  private final GuiView guiViewImpl;

  /**
   * Constructs a CompositeView
   * @param m the music model to view
   */
  public CompositeView(IMusicModel m) {
    try {
      this.midiViewImpl = new MidiView();
    } catch (MidiUnavailableException e) {
      throw new RuntimeException(e);
    }
    this.guiViewImpl = new GuiViewImpl();
    this.midiViewImpl.initialize(m);
    this.guiViewImpl.initialize(m);
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
  public boolean addTickHandler(Runnable r) {
    this.midiViewImpl.addTickHandler(r);
    return true;
  }

  @Override
  public void addKeyListener(KeyListener k) {
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

  @Override
  public void moveBeatIndicator() {
    this.guiViewImpl.moveBeatIndicator();
  }
}
