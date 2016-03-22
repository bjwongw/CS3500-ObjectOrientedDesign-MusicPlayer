package cs3500.music.view;

import cs3500.music.model.IMusicModel;
import cs3500.music.model.Note;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * A view for a range of Note.Pitches
 */
public class GuiViewPanel extends JPanel {

  //height and width in pixels of each square cell
  private static final int CELL_HEIGHT = 20;
  private static final int CELL_WIDTH = 80;
  private static final int SQUARE_DIM = CELL_HEIGHT;
  private static final int PITCH_COL_WIDTH = 50;
  private static final int BEAT_ROW_HEIGHT = 50;

  private final IMusicModel model;
  private final JPanel pitchPanel;
  private final JPanel beatPanel;
  private final JPanel notesPanel;

  /**
   * Constructs the GuiViewPanel
   *
   * @param model the music model to represent
   */
  GuiViewPanel(IMusicModel model) {
    super();
    GridBagLayout gridBag = new GridBagLayout();
    GridBagConstraints c = new GridBagConstraints();
    setLayout(gridBag);
    this.model = model;
    this.pitchPanel = createPitchPanel();
    this.beatPanel = createBeatPanel();
    this.notesPanel = createNotesPanel();

    c.gridx = 0;
    c.gridy = 1;
    c.anchor = GridBagConstraints.NORTH;
    gridBag.setConstraints(pitchPanel, c);
    this.add(pitchPanel);

    c.gridx = 1;
    c.gridy = 0;
    c.anchor = GridBagConstraints.SOUTHWEST;
    gridBag.setConstraints(beatPanel, c);
    this.add(beatPanel);

    c.gridx = 1;
    c.gridy = 1;
//    c.weighty = 1;
    c.anchor = GridBagConstraints.NORTHWEST;
    gridBag.setConstraints(notesPanel, c);
    this.add(notesPanel);
  }

  private JPanel createPitchPanel() {
    int dim = NoteSquares.PREF_H;
    JPanel pitchP = new JPanel();
    pitchP.setLayout(new BoxLayout(pitchP, BoxLayout.PAGE_AXIS));
    List<String> pitchRange = model.getPitchRange();
    for (int i = 0; i < pitchRange.size(); i++) {
      JLabel pitchLabel = new JLabel(pitchRange.get((pitchRange.size() - 1) - i));
      pitchLabel.setBorder(BorderFactory.createEmptyBorder(dim / 5, 0, 0, dim / 5));
      pitchP.add(pitchLabel);
    }
    return pitchP;
  }

  private JPanel createBeatPanel() {
    int dim = NoteSquares.PREF_W;
    JPanel beatP = new JPanel();
//    JPanel beatP = new JPanel(new FlowLayout(FlowLayout.LEFT, dim*16, 0));
    int lastBeat = model.finalBeat() / 16;
    for (int i = 0; i <= lastBeat; i++) {
      JLabel beatLabel = new JLabel(Integer.toString(i*16));
      beatLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, dim*15));
      beatP.add(beatLabel);
    }
    return beatP;
  }

  private JPanel createNotesPanel() {
    int pitches = model.getHighestNote().getMidiPitch() - model.getLowestNote().getMidiPitch() + 1;
    int beats = (model.finalBeat() / 4) + 1;
    JPanel notesP = new JPanel(new GridLayout(pitches, beats));
    notesP.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    List<List<NoteSquares>> panelHolder = new ArrayList<>();
    for (int i = 0; i < pitches; i++) {
      panelHolder.add(new ArrayList<>());
      for (int j = 0; j < beats; j++) {
        NoteSquares squares = new NoteSquares();
        squares.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panelHolder.get(i).add(squares);
        notesP.add(panelHolder.get(i).get(j));
      }
    }
    initializeNotesPanel(panelHolder);
    return notesP;
  }

  private void initializeNotesPanel(List<List<NoteSquares>> notesP) {
    List<Note> noteList = model.getNotes();
    Collections.sort(model.getNotes());
    int highestPitch = model.getHighestNote().getMidiPitch();
    for (Note n : noteList) {
      int pitchIndex = highestPitch - n.getMidiPitch();
      List<NoteSquares> pitchList = notesP.get(pitchIndex);
      int start = n.getStart();
      pitchList.get(start / 4).setNoteColor(start % 4, Color.BLACK);
      for (int i = start + 1; i < start + n.getDuration(); i++) {
        pitchList.get(i / 4).setNoteColor(i % 4, new Color(42, 255, 55));
      }
    }
  }
//
//  @Override
//  public void paintComponent(Graphics g){
//    super.paintComponent(g);
//  }
}
