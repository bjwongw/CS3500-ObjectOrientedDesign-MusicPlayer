package cs3500.music.view;

/**
 * A factory of the views this package provides.
 */
public class ViewFactory {

  /**
   * Constructs an IMusicView using the implementation specified by type.
   *
   * <ul>
   *   <li>console: a ConsoleView object</li>
   *   <li>midi: a MidiView object</li>
   *   <li>gui: a GuiView object</li>
   * </ul>
   * @param type the implementation to use
   * @return an instance of the chosen implementation
   */
  public static IMusicView construct(String type) {

    IMusicView view;

    switch(type) {
      case "console":
        view = new ConsoleView();
        break;
      case "midi":
        view = new MidiView();
        break;
      case "gui":
        view = new GuiView();
        break;
      default:
        throw new IllegalArgumentException(String.format("ViewFactory string not recognized %s", type));
    }

    return view;
  }
}
