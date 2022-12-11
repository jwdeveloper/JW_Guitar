package jw.instruments.core.extentions;

import jw.fluent.plugin.api.FluentApiSpigotBuilder;
import jw.fluent.plugin.api.FluentApiExtension;
import jw.fluent.plugin.implementation.FluentApi;
import jw.fluent.plugin.implementation.FluentApiSpigot;
import jw.instruments.core.data.chords.Chord;
import jw.instruments.core.builders.chord.ChordBuilder;
import jw.instruments.core.data.Consts;
import jw.instruments.core.services.ChordService;
import jw.fluent.api.files.implementation.json.JsonUtility;
import jw.fluent.api.utilites.java.StringUtils;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

public class ChordExtention implements FluentApiExtension {



    @Override
    public void onConfiguration(FluentApiSpigotBuilder builder) {

    }

    @Override
    public void onFluentApiEnable(FluentApiSpigot fluentAPI) {
        var chordsService =  FluentApi.container().findInjection(ChordService.class);
        var chords = loadChords();
        chordsService.add(chords);
    }

    @Override
    public void onFluentApiDisabled(FluentApiSpigot fluentAPI) {

    }


    private List<Chord> loadChords() {
        var file = FluentApi.plugin().getResource("chords_output.json");
        var chordsDto = JsonUtility.loadList(file, ChordDto.class);
        return chordsDto.stream().map(this::mapChord).toList();
    }

    private Chord mapChord(ChordDto chordDto) {
        var builder = ChordBuilder.create(chordDto.getKey(), chordDto.getSuffix(), chordDto.getFret());
        for(var i = 0; i< Consts.STRINGS; i++)
        {
            var finger = chordDto.getFrets().get(i);
            var stringId = Consts.STRINGS -1-i;
            if(finger == 0)
            {
                builder.addNote(finger, stringId,0.4f);
            }
            else
            {
                builder.addNote(finger, stringId);
            }

        }
        return builder.build();
    }



    @Data
    public class ChordDto
    {
        private Integer fret = 0;
        private String suffix = StringUtils.EMPTY_STRING;
        private String key = StringUtils.EMPTY_STRING;
        private ArrayList<Integer> frets = new ArrayList<>();
    }
}
