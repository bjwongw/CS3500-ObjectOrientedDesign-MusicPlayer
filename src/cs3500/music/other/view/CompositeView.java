package cs3500.music.other.view;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.sound.midi.InvalidMidiDataException;

import cs3500.music.other.model.IMusic;

/**
 * Created by renyuan on 3/29/16.
 */



// an combination view of Midi  and visual view.
public class CompositeView implements GuiView {
  protected GuiView visual;
  private MidiViewImpl midi;


  public CompositeView() {
    this.visual = new GuiViewFrame();
    this.midi = new MidiViewImpl();
  }

  ;

  /**
   * initialize the view
   *
   * @param m the music is passed to model
   */
  @Override
  public void initialize(IMusic m) throws InterruptedException, InvalidMidiDataException {
    visual.initialize(m);
    midi.initialize(m);
  }


  @Override
  public void addKeyListener(KeyListener kbd) {
    visual.addKeyListener(kbd);

  }


  @Override
  public void resetFocus() {
    visual.resetFocus();
  }


  @Override
  public void resume() {
    visual.resume();
    midi.start();
  }


  @Override
  public void move1(int x, int y) {
    this.remove(x, y);
    midi.remove(x, y);
  }

  @Override
  public void move2(int x, int y, int duration, int instrument, int volume) {

    this.visual.move2(x, y, duration, instrument, volume);
    this.midi.addNote(x, y, duration, instrument, volume);

  }


  @Override
  public void scrollright() {
    visual.scrollright();
  }

  @Override
  public void scrollleft() {
    visual.scrollleft();
  }

  @Override
  public void scrollup() {
    visual.scrollup();
  }

  @Override
  public void scrolldown() {
    visual.scrolldown();
  }


  @Override
  public void scrollEnd() {
    visual.scrollEnd();
  }

  @Override
  public void scrollBegin() {
    visual.scrollBegin();
  }


  @Override
  public void addNote(int x, int y, int duration, int instrument, int volume) {
    visual.addNote(x, y, duration, instrument, volume);
    midi.addNote(x, y, duration, instrument, volume);
  }

  @Override
  public void remove(int x, int y) {
    visual.remove(x, y);
    midi.remove(x, y);

  }

  @Override
  public void stop() {
    visual.stop();
    midi.stop();
  }

//  @Override
//  public GuiViewFrame getVisual(){
//    return this.visual;
//  }

  @Override
  public void deleteAll(ArrayList<int[]> lop) {
    visual.deleteAll(lop);
    midi.deleteAll(lop);
  }

  @Override
  public void addMouseListener(MouseListener mh) {
    this.visual.addMouseListener(mh);
  }

  @Override
  public void setModel(IMusic m){
    visual.setModel(m);
    midi.setMode(m);
  }
}
