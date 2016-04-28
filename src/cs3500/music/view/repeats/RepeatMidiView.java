package cs3500.music.view.repeats;

import java.util.Timer;
import java.util.TimerTask;

import javax.sound.midi.MidiUnavailableException;

import cs3500.music.model.IMusicModel;
import cs3500.music.model.repeats.IRepeatModel;
import cs3500.music.model.Note;
import cs3500.music.view.MidiView;

/**
 * Created by Alex on 4/27/2016.
 */
public class RepeatMidiView extends MidiView {

  private IRepeatModel model;

  public RepeatMidiView(IRepeatModel r) throws MidiUnavailableException {
    this.model = r;
  }

  @Override
  public void initialize(IMusicModel m) {
    super.initialize(this.model);
  }

  @Override
  public void play() {
    if (this.model == null) {
      throw new IllegalStateException("Cannot play the Midi model without providing a model (via" +
              " initialize) first!");
    }
    this.timer = new Timer();
    this.timer.schedule(new PlayBeat(), 0, this.model.getTempo() / 1000);
  }

  private class PlayBeat extends TimerTask {
    @Override
    public void run() {
      for(Note n : model.notesToPlay(currentBeat)) {
        playNote(n);
      }
      currentBeat = model.nextBeat(currentBeat);

      if(!(handler == null)) {
        handler.run();
      }
    }
  }

}
