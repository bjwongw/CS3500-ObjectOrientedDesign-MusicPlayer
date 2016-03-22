package cs3500.music;

import cs3500.music.model.GenericMusicModel;
import cs3500.music.model.IMusicModel;
import cs3500.music.util.CompositionBuilder;
import cs3500.music.util.MusicReader;
import cs3500.music.view.IMusicView;
import cs3500.music.view.ViewFactory;

import java.io.FileReader;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;

public class MusicEditor {
  public static void main(String[] args) throws IOException, InvalidMidiDataException {
    IMusicView view;
    IMusicView midiView;

    CompositionBuilder<IMusicModel> b = new GenericMusicModel.Builder();

    IMusicModel m = MusicReader.parseFile(new FileReader("mystery-1.txt"), b);

    view = ViewFactory.construct("gui");
    view.initialize(m);

    midiView = ViewFactory.construct("midi");
    midiView.initialize(m);
  }
}
