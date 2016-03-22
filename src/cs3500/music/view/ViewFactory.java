package cs3500.music.view;

/**
 * A factory of the views this package provides.
 */
public class ViewFactory {

  public static IMusicView construct(String type) {

    IMusicView view;

    switch(type) {
      case "console":
        //TODO fill this out
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
