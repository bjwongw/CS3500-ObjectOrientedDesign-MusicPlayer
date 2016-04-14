package cs3500.music.other.view;

import cs3500.music.model.IMusic;

/**
 * Created by renyuan on 3/16/16.
 */
public class TextView implements IMusicView {


  public TextView() {
  }


  @Override
  public void initialize(IMusic m) {
    System.out.println(m.print());
  }

  public String showTextView(IMusic m) {
    return m.print();
  }


}
