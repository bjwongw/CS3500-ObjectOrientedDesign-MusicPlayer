package cs3500.music.view.repeats;

import java.awt.*;
import java.util.List;
import java.util.Map;

import cs3500.music.model.repeats.IRepeatModel;
import cs3500.music.model.repeats.Repeat;
import cs3500.music.view.ConcreteGuiView;
import cs3500.music.view.NoteSquares;

/**
 * Created by Alex on 4/27/2016.
 */
public class RepeatConcreteGuiView extends ConcreteGuiView {

  IRepeatModel model;

  /**
   * Constructs a ConcreteGuiView
   *
   * @param model               the music model to represent
   * @param externalHorizBuffer the horizontal buffer surrounding this panel from components
   *                            holding this panel
   * @param externalVertBuffer  the vertical buffer surrounding this panel from components holding
   */
  RepeatConcreteGuiView(IRepeatModel model, int externalHorizBuffer, int externalVertBuffer) {
    super(model);
    this.model = model;
    this.externalHorizontalBuffer = externalHorizBuffer;
    this.externalVerticalBuffer = externalVertBuffer;
    try {
      this.rowStartMidi = model.getHighestNote().getMidiPitch();
    } catch (IllegalStateException e) {
      this.rowStartMidi = defaultStartMidi;
    }
    this.columnStart = 0;
    this.currentTime = 0;
    this.updatePanel();
    this.setDoubleBuffered(true);
  }


  @Override
  protected void initializeNotesPanel(List<List<NoteSquares>> notesP) {
    if (!(this.model == null)) {
      for (Repeat r : this.model.getBasicRepeats()) {
        if (r.getBeat() >= columnStart && r.getBeat() < columnStart + displayedBeats) {
          for (int i = 0; i < notesP.size(); i++) {
            notesP.get(i).get((r.getBeat() - columnStart) / beatsPerCell).setNoteColor((r.getBeat()
                            - columnStart) % beatsPerCell,
                    new Color(255, 0, 0));
          }
        }
      }

      for (Map.Entry<Integer, Repeat> e : this.model.getAlternateRepeats().entrySet()) {
        if (e.getKey() >= columnStart && e.getKey() < columnStart + displayedBeats) {
          for (int i = 0; i < notesP.size(); i++) {
            notesP.get(i).get((e.getKey() - columnStart) / beatsPerCell).setNoteColor((e.getKey()
                            - columnStart) % beatsPerCell,
                    new Color(0, 0, 255));
          }
        }
      }
    }

    super.initializeNotesPanel(notesP);
  }

}
