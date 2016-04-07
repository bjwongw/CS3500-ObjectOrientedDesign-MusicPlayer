package cs3500.music;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;

import cs3500.music.controller.Controller;
import cs3500.music.controller.IController;
import cs3500.music.model.GenericMusicModel;
import cs3500.music.model.IMusicModel;
import cs3500.music.model.Note;
import cs3500.music.util.CompositionBuilder;
import cs3500.music.util.MusicReader;
import cs3500.music.view.*;
import javax.sound.midi.MidiUnavailableException;

public class MusicEditor {

  /**
   * A method to test all three views at once on the given file.
   *
   * @param file the file to run all views on
   */
  public static void main2(String file) throws IOException, InvalidMidiDataException {
    View consoleView = ViewFactory.construct("console");
    View guiView = ViewFactory.construct("gui");
    View midiView = ViewFactory.construct("midi");

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
    View view;

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
//
//    try {
//      main2("mystery-3.txt");
//    } catch (Exception e) {
//
    CompositionBuilder<IMusicModel> b = new GenericMusicModel.Builder();

    // NOTE df-ttfaf.txt won't run because it has at least one note with a duration < 1
    IMusicModel m = MusicReader.parseFile(new FileReader("mary-little-lamb.txt"), b);

    IMusicModel emptyModel = new GenericMusicModel(10000);
    emptyModel.addNote(new Note(Note.Pitch.C_SHARP, 3, 1, 1, 1, 1));


    GuiView view = new CompositeView(m);

    IController c = new Controller(m, view);
  }
}
