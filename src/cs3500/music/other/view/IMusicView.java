package cs3500.music.other.view;

import javax.sound.midi.InvalidMidiDataException;

import cs3500.music.model.IMusic;

/**
 * Created by renyuan on 3/16/16.
 */
public interface IMusicView {

  /**
   * initialize the view
   *
   * @param m the music is passed to model
   */
  void initialize(IMusic m) throws InterruptedException, InvalidMidiDataException;


}
