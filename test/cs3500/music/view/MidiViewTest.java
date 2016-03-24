package cs3500.music.view;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;

import cs3500.music.model.GenericMusicModel;
import cs3500.music.model.IMusicModel;
import cs3500.music.model.Note;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests for the MidiView class using its mock synthesizer/receiver.
 */
public class MidiViewTest {

  StringBuilder log = new StringBuilder();
  StringBuilder expected = new StringBuilder();
  Synthesizer synth = new MidiView.MockSynthesizer(log);
  MidiView view = new MidiView(synth);
  IMusicModel model = new GenericMusicModel(0);

  /**
   * Adds a note to the end of both the model and the expected log of notes played
   *
   * @param p     pitch of the note
   * @param o     octave of the note
   * @param d     duration of the note in beats
   * @param i     instrument value of the note
   * @param v     volume of the note
   * @param model model to add the note to
   * @param log   log to add the send calls to
   */
  void addNote(Note.Pitch p, int o, int s, int d, int i, int v, IMusicModel model,
    StringBuilder log) {
    Note n = new Note(p, o, s, d, i, v);
    model.addNote(n);
    log.append(String.format("send call: Command %d, MIDIPitch %d; Volume %d; " +
            "timeStamp %d\n", ShortMessage.NOTE_ON, n.getMidiPitch(), v, -1));
    log.append(String.format("send call: Command %d, MIDIPitch %d; Volume %d; " +
            "timeStamp %d\n", ShortMessage.NOTE_OFF, n.getMidiPitch(), v, n.getDuration() *
            model.getTempo()));
  }

  /**
   * Checks if log1 contains every line in log2, possibly out of order
   *
   * @param log1 the first log
   * @param log2 the second log
   * @return true if log1 has all of the lines in log2
   */
  boolean compareLogs(StringBuilder log1, StringBuilder log2) {
    ArrayList<String> list1 = new ArrayList<>(Arrays.asList(log1.toString().split("\n")));
    ArrayList<String> list2 = new ArrayList<>(Arrays.asList(log2.toString().split("\n")));

    for (String s : list2) {
      if (!list1.contains(s)) {
        return false;
      }
    }
    return true;
  }

  @Test
  public void melodyTest() {
    expected.append("Synthesizer opened\n");
    for (int p = 0; p < 12; p++) {
      for (int o = 0; o < 3; o++) {
        for (int d = 1; d < 3; d++) {
          for (int i = 0; i < 2; i++) {
            for (int v = 0; v < 2; v++) {
              addNote(Note.Pitch.values()[p], o, p+o+d+i+v, d, i, v, this.model, this.expected);
            }
          }
        }
      }
    }
    this.view.initialize(this.model);
    assertTrue(compareLogs(log, expected));
    assertTrue(compareLogs(expected, log));
  }

  @Test
  public void melodyPlus1Test() {
    expected.append("Synthesizer opened\n");
    for (int p = 0; p < 12; p++) {
      for (int o = 0; o < 3; o++) {
        for (int d = 1; d < 3; d++) {
          for (int i = 0; i < 2; i++) {
            for (int v = 0; v < 1; v++) {
              addNote(Note.Pitch.values()[p], o, p+o+d+i+v, d, i, v, this.model, this.expected);
            }
          }
        }
      }
    }
    model.addNote(new Note(Note.Pitch.A, 6, 3, 2, 1, 0));
    this.view.initialize(this.model);
    assertFalse(compareLogs(expected, log));
  }

  @Test
  public void harmonyTest() {
    expected.append("Synthesizer opened\n");
    for(int s = 0; s < 4; s ++) {
      for (int p = 0; p < 12; p++) {
        for (int o = 0; o < 3; o++) {
          for (int d = 1; d < 3; d++) {
            for (int i = 0; i < 2; i++) {
              for (int v = 0; v < 1; v++) {
                addNote(Note.Pitch.values()[p], o, s, d, i, v, this.model, this.expected);
              }
            }
          }
        }
      }
    }
    this.view.initialize(this.model);
    assertTrue(compareLogs(expected, log));
    assertTrue(compareLogs(log, expected));
  }
}
