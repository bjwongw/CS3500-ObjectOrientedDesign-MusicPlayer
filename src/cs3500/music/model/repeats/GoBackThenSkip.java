package cs3500.music.model.repeats;

/**
 * Created by Alex on 4/26/2016.
 */
public class GoBackThenSkip extends ARepeat {

  private int goTo;
  private int skipAt;

  public GoBackThenSkip(int goBackAt, int goBackTo, int skipAt) {
    super(goBackAt);
    this.goTo = goBackTo;
    this.skipAt = skipAt;
  }

  @Override
  public int nextBeat() {
    if(this.timesVisited == 1) {
      this.visit();
      return this.goTo;
    } else if(this.timesVisited >= 2){
      this.visit();
      return this.beat + 1;
    } else {
      this.visit();
      return this.skipAt + 1;
    }
  }

  @Override
  public boolean isBasic() {
    return false;
  }
}
