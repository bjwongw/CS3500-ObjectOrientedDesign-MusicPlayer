package cs3500.music;

import cs3500.music.model.GenericMusicModel;
import cs3500.music.model.IMusicModel;
import cs3500.music.util.CompositionBuilder;
import cs3500.music.util.MusicReader;
import cs3500.music.view.GuiViewFrame;
import cs3500.music.view.MidiViewImpl;

import java.io.FileReader;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;


public class MusicEditor {
  public static void main(String[] args) throws IOException, InvalidMidiDataException {
    GuiViewFrame view = new GuiViewFrame();
    MidiViewImpl midiView; // = new MidiViewImpl();
    // You probably need to connect these views to your model, too...

    view.setPreferredSize(view.getPreferredSize());
    view.initialize();

    CompositionBuilder<IMusicModel> b = new GenericMusicModel.Builder();
    IMusicModel m = MusicReader.parseFile(new FileReader("mystery-3.txt"), b);

    midiView = new MidiViewImpl(m);

    int beat = 0;
    while (true) {
      midiView.update(beat);
      try {
        Thread.sleep(m.getTempo() / 1000);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
      beat += 1;
    }
  }
}
