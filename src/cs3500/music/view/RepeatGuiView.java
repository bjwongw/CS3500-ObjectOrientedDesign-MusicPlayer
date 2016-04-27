package cs3500.music.view;

import cs3500.music.model.IMusicModel;
import cs3500.music.model.IRepeatModel;

/**
 * Created by Alex on 4/26/2016.
 */
public class RepeatGuiView extends GuiViewImpl {

  private IRepeatModel model;

  public RepeatGuiView(IRepeatModel m) {
    this.model = m;
  }

  @Override
  public void initialize(IMusicModel m) {
    super.initialize(this.model);
  }

  @Override
  public void moveBeatIndicator() {
    this.currentTime = this.model.nextBeat(currentTime) - 1;
    super.moveBeatIndicator();
  }
}
