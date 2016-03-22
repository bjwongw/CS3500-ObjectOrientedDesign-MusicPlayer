package cs3500.music.view;

import cs3500.music.model.IMusicModel;

/**
 * A view to put the text version of the given song to console.
 */
public class ConsoleView implements IMusicView{
  private IMusicModel model;

  @Override
  public void initialize(IMusicModel m) {
    this.model = m;
    System.out.print(this.model.printMusic());
  }

  @Override
  public void update(int beat) {

  }
}
