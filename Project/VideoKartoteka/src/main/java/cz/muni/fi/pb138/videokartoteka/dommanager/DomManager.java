package cz.muni.fi.pb138.videokartoteka.dommanager;

import java.util.List;

/**
 *
 * @author Michal, Lukas
 */
public interface DomManager {
    void addMediaType(String name, List<String> attributes);
    void deleteMediaType(String name);
    List<String> getMediaNames();
    void addRecord(String media, List<String> attributes);
    void deleteRecord(String media, int id);
    void editRecord(String media, int id, List<String> attributes);
    List<Integer> searchRecord(String searchValue, String media);
    MediaType loadTableToMediaType(String media);
}