
package cz.muni.fi.pb138.videokartoteka.dommanager;

/**
 * This interface describes functionality of the application
 * 
 * @author Lukáš Farkaš
 */
public interface DomLayout {
    
    /**
     * This method adds user defined category to the database
     * @param categoryName name of category to be added
     */
    public void addCategory(String categoryName);

    /**
     * This method removes category of given name from the database
     * @param categoryName name of category to be removed
     */
    public void removeCategory(String categoryName);

    /**
     * This method adds user defined record to the database 
     * under the category defined by the user
     * @param recordName name of record to be added
     * @param categoryName name of category under which record is added
     */
    public void addRecord(String recordName, String categoryName);

    /**
     * This method removes record with given name from the database 
     * (and subsequently from its category)
     * @param recordName name of record to be removed
     */
    public void removeRecord(String recordName);

    /**
     * This method moves existing record from original category under new,
     * user defined category
     * @param recordName name of record which category is to be changed
     * @param categoryName new category under which record is to be placed
     */
    public void editRecordCategory(String recordName, String categoryName);

    /**
     * This method searches the database for record defined by user 
     * @param recordName name of the record to be found
     * @return record in question
     */
    public Record searchForRecord(String recordName);
}
