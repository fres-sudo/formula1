package it.unicam.cs.oop.formulauno.model.parser;

import it.unicam.cs.oop.formulauno.model.mapper.TrackMapper;
import it.unicam.cs.oop.formulauno.model.track.Track;
import it.unicam.cs.oop.formulauno.model.track.TrackJTO;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileReader;
import java.io.IOException;


/**
 * This class is responsible for parsing a JSON file into a Track object.
 * It uses a TrackMapper to map the parsed data from a TrackJTO to a Track.
 */
public class TrackParser implements Parser<Track> {

    private final TrackMapper trackMapper;

    /**
     * Constructs a TrackParser with a given TrackMapper.
     *
     * @param trackMapper the TrackMapper to use for mapping TrackJTO to Track
     */
    public TrackParser(TrackMapper trackMapper) {
        this.trackMapper = trackMapper;
    }

    /**
     * Parses a JSON file into a Track object.
     *
     * @param filePath the path of the JSON file to parse
     * @return the Track object parsed from the JSON file
     * @throws IOException if an I/O error occurs reading from the file
     */
    @Override
    public Track parse(String filePath) throws IOException {
        JSONObject jsonObject;
        try (FileReader reader = new FileReader(filePath)) {
            JSONTokener tokener = new JSONTokener(reader);
            jsonObject = new JSONObject(tokener);
        }

        TrackJTO trackJTO = TrackJTO.fromJson(jsonObject);
        return trackMapper.fromDTO(trackJTO);
    }

}
