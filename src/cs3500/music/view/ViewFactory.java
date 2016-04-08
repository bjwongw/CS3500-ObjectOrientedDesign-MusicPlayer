package cs3500.music.view;

import javax.sound.midi.MidiUnavailableException;

/**
 * A factory of the views this package provides.
 */
public class ViewFactory {

  /**
   * Constructs a View using the implementation specified by type.
   *
   * <ul> <li>console: a ConsoleViewImpl object</li> <li>midi: a MidiView object</li> <li>gui: a
   * GuiViewImpl object</li> <li>composite: a CompositeView object</li> </ul>
   *
   * @param type the implementation to use
   * @return an instance of the chosen implementation
   */
  public static View constructView(String type) {

    View view;

    switch (type) {
      case "console":
        view = new ConsoleViewImpl(System.out);
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
      case "composite":
        view = new CompositeView();
        break;
      default:
        throw new IllegalArgumentException(String.format("ViewFactory string not recognized: %s",
                type));
    }
    return view;
  }

  /**
   * Constructs a GuiView using the implementation specified by type.
   *
   * <ul> <li>gui: a GuiViewImpl object</li> <li>composite: a CompositeView object</li> </ul>
   *
   * @param type the implementation to use
   * @return an instance of the chosen implementation
   */
  public static GuiView constructGui(String type) {
    GuiView view;
    switch (type) {
      case "gui":
        view = new GuiViewImpl();
        break;
      case "composite":
        view = new CompositeView();
        break;
      default:
        throw new IllegalArgumentException(String.format("ViewFactory string not recognized: %s",
          type));
    }
    return view;
  }
}
