package cs3500.music.model.repeats;

/**
 * Created by Alex on 4/26/2016.
 */
public class GoToBeatOnce extends ARepeat {

  private int goTo;

  public GoToBeatOnce(int beat, int goTo) {
    super(beat);
    this.goTo = goTo;
  }

  @Override
  public int nextBeat() {
    if(this.timesVisited == 0) {
      this.visit();
      return this.goTo;
    } else {
      return super.nextBeat();
    }
  }
}
