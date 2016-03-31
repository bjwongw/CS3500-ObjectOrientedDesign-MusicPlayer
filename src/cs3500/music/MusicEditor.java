package cs3500.music;

import cs3500.music.model.GenericMusicModel;
import cs3500.music.model.IMusicModel;
import cs3500.music.util.CompositionBuilder;
import cs3500.music.util.MusicReader;
import cs3500.music.view.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

public class MusicEditor {

  /**
   * A method to test all three views at once on the given file.
   *
   * @param file the file to run all views on
   */
  public static void main2(String file) throws IOException, InvalidMidiDataException {
    IMusicView consoleView = ViewFactory.construct("console");
    IMusicView guiView = ViewFactory.construct("gui");
    IMusicView midiView = ViewFactory.construct("midi");

    CompositionBuilder<IMusicModel> b = new GenericMusicModel.Builder();
    IMusicModel m = MusicReader.parseFile(new FileReader(file), b);

    consoleView.initialize(m);
    guiView.initialize(m);
    midiView.initialize(m);
  }

  /**
   * The main method. Runs a given view on a given file.
   *
   * Arguments: file viewtype file is a path to a music file viewtype is one of console, midi, or
   * gui
   *
   * @param args command line arguments described above
   * @throws IOException              on bad file
   * @throws InvalidMidiDataException when midi cannot be accessed
   */
  public static void main3(String[] args) throws IOException, InvalidMidiDataException {

    if (args.length < 2) {
      System.out.println("Arguments: file viewtype");
      System.out.println("file is a path to a music file");
      System.out.println("viewtype is one of console, midi, or gui");
      return;
    }

    FileReader file;
    IMusicView view;

    try {
      file = new FileReader(args[0]);
      view = ViewFactory.construct(args[1]);
    } catch (FileNotFoundException | IllegalArgumentException e) {
      System.out.println(e.getMessage());
      return;
    }

    CompositionBuilder<IMusicModel> b = new GenericMusicModel.Builder();
    IMusicModel m = MusicReader.parseFile(file, b);

    view.initialize(m);
  }

  public static void main(String[] args) throws IOException, MidiUnavailableException {

    CompositionBuilder<IMusicModel> b = new GenericMusicModel.Builder();
    IMusicModel m = MusicReader.parseFile(new FileReader("mary-little-lamb.txt"), b);

    IMusicView view = new ConsoleView(System.out);
    view.initialize(m);
  }
}
