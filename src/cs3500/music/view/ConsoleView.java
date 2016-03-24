package cs3500.music.view;

import cs3500.music.model.IMusicModel;

import java.io.IOException;

/**
 * A view to put the text version of the given composition to console.
 */
public class ConsoleView implements IMusicView {
  private IMusicModel model;
  private Appendable output;

  /**
   * Constructor for the ConsoleView
   * @param output the output of this view
   */
  public ConsoleView(Appendable output) {
    this.output = output;
  }

  @Override
  public void initialize(IMusicModel m) {
    this.model = m;
    try {
      output.append(this.model.printMusic());
    } catch (IOException io) {
      io.printStackTrace();
    }
  }

  // NOTE: currently does nothing for this implementation
  @Override
  public void update(int beat) { }
}
