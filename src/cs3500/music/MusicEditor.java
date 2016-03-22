package cs3500.music;

import cs3500.music.model.GenericMusicModel;
import cs3500.music.model.IMusicModel;
import cs3500.music.util.CompositionBuilder;
import cs3500.music.util.MusicReader;
import cs3500.music.view.GuiView;
import cs3500.music.view.IMusicView;
import cs3500.music.view.MidiView;
import cs3500.music.view.ViewFactory;

import java.io.FileReader;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;


public class MusicEditor {
  public static void main(String[] args) throws IOException, InvalidMidiDataException {
    IMusicView view; // = new GuiViewFrame(...);
    MidiView midiView; // = new MidiView();
    // You probably need to connect these views to your model, too...

    CompositionBuilder<IMusicModel> b = new GenericMusicModel.Builder();

    IMusicModel m = MusicReader.parseFile(new FileReader("mystery-1.txt"), b);

    view = ViewFactory.construct("gui");
    view.initialize(m);

//    System.out.println(m.printMusic());

//    midiView = new MidiView(m);
//    int beat = 0;
//    while (true) {
//      midiView.update(beat);
//      try {
//        Thread.sleep(m.getTempo() / 1000);
//      } catch (InterruptedException e) {
//        throw new RuntimeException(e);
//      }
//      beat += 1;
//    }
  }
}
