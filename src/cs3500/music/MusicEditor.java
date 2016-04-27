package cs3500.music;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

import javax.sound.midi.InvalidMidiDataException;

import cs3500.music.controller.GuiController;
import cs3500.music.controller.IController;
import cs3500.music.model.GenericMusicModel;
import cs3500.music.model.IMusicModel;
import cs3500.music.model.IRepeatModel;
import cs3500.music.model.RepeatModel;
import cs3500.music.util.MusicReader;
import cs3500.music.util.RepeatMusicReader;
import cs3500.music.view.*;
import cs3500.music.view.GuiView;

public class MusicEditor {

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
  public static void main2(String[] args) throws IOException, InvalidMidiDataException {

    if (args.length < 3) {
      System.out.println("Arguments: file, view type, explicit view");
      System.out.println("Recommended usage: java -jar [jar] [file] gui composite");
      System.out.println("file is a path to a music file");
      System.out.println("view type is one of: generic, gui, other");
      System.out.println("explicit view (under generic) is one of: console, midi, gui, composite");
      System.out.println("explicit view (under gui) is one of: gui, composite");
      System.out.println("explicit view (under other) is one of: composite --ONLY SONG THAT " +
              "WORKS: mary-little-lamb.txt");
      return;
    }
    try {
      FileReader file = new FileReader(args[0]);
      IMusicModel m = MusicReader.parseFile(file, new GenericMusicModel.Builder());
      String viewType = args[1];
      String explicitView = args[2];
      if (Objects.equals(viewType, "generic")) {
        View view = ViewFactory.constructView(explicitView);
        view.initialize(m);
        view.play();
      } else if (Objects.equals(viewType, "gui")) {
        GuiView guiView = ViewFactory.constructGui(explicitView);
        if (Objects.equals(explicitView, "composite")) {
          IController c = new GuiController(m, guiView);
          c.start();
        } else {
          guiView.initialize(m);
        }
      } else if (Objects.equals(viewType, "other")) {
        GuiView view = ViewFactory.constructOther(explicitView);
        IController c = new GuiController(m, view);
        c.start();
      }

    } catch (FileNotFoundException | IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }
  }

  public static void main(String[] args) throws IOException, InvalidMidiDataException {
    FileReader file = new FileReader("mary-repeat.txt");
    IRepeatModel m = RepeatMusicReader.parseFile(file, new RepeatModel.Builder());

    GuiView view = ViewFactory.constructRepeatCompositeView(m);
    IController controller = new GuiController(m, view);
    controller.start();
  }
}
