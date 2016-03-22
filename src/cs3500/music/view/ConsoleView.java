package cs3500.music.view;

import cs3500.music.model.IMusicModel;

/**
 * A view to put the text version of the given composition to console.
 */
public class ConsoleView implements IMusicView {
  private IMusicModel model;

  @Override
  public void initialize(IMusicModel m) {
    this.model = m;
    System.out.print(this.model.printMusic());
  }

  // NOTE: currently does nothing for this implementation
  @Override
  public void update(int beat) { }
}
