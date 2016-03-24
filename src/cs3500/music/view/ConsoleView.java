package cs3500.music.view;

import com.sun.corba.se.spi.ior.Writeable;

import java.io.PrintStream;

import cs3500.music.model.IMusicModel;

/**
 * A view to put the text version of the given composition to console.
 */
public class ConsoleView implements IMusicView {
  private IMusicModel model;

  PrintStream output;

  public ConsoleView(PrintStream w) {
    this.output = w;
  }

  @Override
  public void initialize(IMusicModel m) {
    this.model = m;
    output.print(this.model.printMusic());
  }

  // NOTE: currently does nothing for this implementation
  @Override
  public void update(int beat) { }
}
