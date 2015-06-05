/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.videokartoteka.dommanager;

import java.util.List;

/**
 *
 * @author Michal, Lukas
 */
public interface DomManager {
    void addMediaType(String name, List<String> attributes);
    void deleteMediaType(String name);
    List<String> listMediaTypes();
    void addRecord(String media, List<String> attributes);
    void deleteRecord(String media, int id);
    void editRecord(String media, int id, List<String> attributes);
    String getRecord(String media, int id);
    List<String> listRecords(String media);
    List<Integer> searchRecord(String searchValue, String media);
}