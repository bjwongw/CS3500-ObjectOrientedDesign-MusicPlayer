package cs3500.music.view;

import javax.sound.midi.MidiUnavailableException;

/**
 * A factory of the views this package provides.
 */
public class ViewFactory {

  /**
   * Constructs an IMusicView using the implementation specified by type.
   *
   * <ul> <li>console: a ConsoleView object</li> <li>midi: a MidiView object</li> <li>gui: a
   * GuiViewImpl object</li> </ul>
   *
   * @param type the implementation to use
   * @return an instance of the chosen implementation
   */
  public static IMusicView construct(String type) {

    IMusicView view;

    switch (type) {
      case "console":
        view = new ConsoleView(System.out);
        break;
      case "midi":
        try {
          view = new MidiView();
        } catch (MidiUnavailableException e) {
          throw new RuntimeException(e);
        }
        break;
      case "gui":
        view = new GuiViewImpl();
        break;
      default:
        throw new IllegalArgumentException(String.format("ViewFactory string not recognized: %s",
                type));
    }

    return view;
  }
}
