package cs3500.music;

import cs3500.music.model.GenericMusicModel;
import cs3500.music.model.IMusicModel;
import cs3500.music.util.CompositionBuilder;
import cs3500.music.util.MusicReader;
import cs3500.music.view.IMusicView;
import cs3500.music.view.ViewFactory;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;

public class MusicEditor {
  public static void main(String[] args) throws IOException, InvalidMidiDataException {
    IMusicView consoleView = ViewFactory.construct("console");
    IMusicView guiView = ViewFactory.construct("gui");
    IMusicView midiView = ViewFactory.construct("midi");

    CompositionBuilder<IMusicModel> b = new GenericMusicModel.Builder();
    IMusicModel m = MusicReader.parseFile(new FileReader("mystery-2.txt"), b);

    consoleView.initialize(m);
    guiView.initialize(m);
    midiView.initialize(m);
  }

  //This is what we'll use when we output the java application, just rename it to main
  public static void main2(String[] args) throws IOException, InvalidMidiDataException {
    FileReader file = new FileReader(args[1]);
    IMusicView view = ViewFactory.construct(args[2]);

    CompositionBuilder<IMusicModel> b = new GenericMusicModel.Builder();
    IMusicModel m = MusicReader.parseFile(file, b);

    view.initialize(m);
  }
}
