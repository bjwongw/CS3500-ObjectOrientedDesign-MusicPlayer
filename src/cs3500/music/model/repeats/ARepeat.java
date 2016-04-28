package cs3500.music.model.repeats;

/**
 * Created by Alex on 4/26/2016.
 */
public abstract class ARepeat implements Repeat{

  protected int beat;
  protected int timesVisited;

  /**
   * Constructs a repeat at the given beat.
   * @param beat the home of the repeat
   */
  public ARepeat(int beat) {
    this.beat = beat;
    this.timesVisited = 0;
  }

  @Override
  public int nextBeat() {
    return this.beat + 1;
  }

  @Override
  public void reset() {
    this.timesVisited = 0;
  }

  @Override
  public int getBeat() {
    return this.beat;
  }

  @Override
  public boolean isBasic() {
    return true;
  }

  protected void visit() {
    this.timesVisited += 1;
  }
}
