package cs3500.music.view.repeats;

import javax.sound.midi.MidiUnavailableException;

import cs3500.music.model.repeats.IRepeatModel;
import cs3500.music.view.CompositeView;

/**
 * Created by Alex on 4/27/2016.
 */
public class RepeatCompositeView extends CompositeView {

  IRepeatModel model;

  public RepeatCompositeView(IRepeatModel r) {
    try {
      this.midiViewImpl = new RepeatMidiView(r);
    } catch (MidiUnavailableException e) {
      e.printStackTrace();
    }
    this.guiViewImpl = new RepeatGuiView(r);

    this.model = r;
  }

  @Override
  public void reset() {
    super.reset();
    this.model.reset();
  }
}
