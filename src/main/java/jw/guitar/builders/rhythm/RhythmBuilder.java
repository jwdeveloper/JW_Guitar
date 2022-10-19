package jw.guitar.builders.rhythm;

import jw.guitar.rhythms.CustomRhythm;
import jw.guitar.rhythms.Rhythm;
import jw.guitar.rhythms.events.ChordChangeEvent;
import jw.guitar.rhythms.events.NoteEvent;

import java.util.function.Consumer;

public class RhythmBuilder {

    private RhythmModel model;

    public static RhythmBuilder create() {
        return new RhythmBuilder();
    }


    public RhythmBuilder() {
        model = new RhythmModel();
    }

    public RhythmBuilder setName(String name) {
        model.setName(name);
        return this;
    }

    public RhythmBuilder onChordChanged(Consumer<ChordChangeEvent> event) {
        model.setOnChordChanged(event);
        return this;
    }

    public RhythmBuilder setSpeed(Integer speed) {
        model.setSpeed(speed);
        return this;
    }

    public RhythmBuilder onNote(Consumer<NoteEvent> event) {
        model.getEvents().add(event);
        return this;
    }


    public Rhythm build() {
        return new CustomRhythm(model);
    }
}
