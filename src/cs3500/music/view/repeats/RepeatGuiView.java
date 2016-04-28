package cs3500.music.view.repeats;

import cs3500.music.model.IMusicModel;
import cs3500.music.model.repeats.IRepeatModel;
import cs3500.music.view.GuiViewImpl;

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
    super.model = m;
    this.displayPanel = new RepeatConcreteGuiView(model, borderBuffer, borderBuffer);
    getContentPane().add(displayPanel);
    this.setResizable(false);
    pack();
    this.setSize(this.getWidth() + this.borderBuffer, this.getHeight() + this.borderBuffer);
    this.setVisible(true);
    this.displayPanel.addMouseListener(this.mouseListener);
  }

  @Override
  public void moveBeatIndicator() {
    this.currentTime = this.model.nextBeat(currentTime) - 1;
    super.moveBeatIndicator();
  }
}
