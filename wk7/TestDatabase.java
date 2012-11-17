/* CPAC I, Evan Korth
 * Assignment #6
 * 19 Nov 12
 * Victor (Ben) Turner, vt520@nyu.edu, N15271750
 *
 * Write sort() and input functions to sort given text db file.
 * Added sort() and promptUserField() but the rest was given by professor.
 *
 * TODO: Sanitize and error-check input.
*/

import java.util.*;
import java.io.*;

public class TestDatabase
{ 
  final static int BIG = 20;
  final static int MAX_FIELDS = 4;

  /**
   * Program entry point.
   *
   * 1st argument - file path to the database file.
   * the rest - ignored
   */
  public static void main(String[] args)
  {
    if (args.length < 1) {
      System.err.println("Please enter the path to the database file as the first argument.");
      System.exit(0);
    }
    
    final String filename = args[0];
    File file = new File(filename);

    try {
      Database[] dv = new Database[BIG];
      int count = read(new FileInputStream(file), dv);

      System.out.println("Original database:");
      printArray(dv, count);
      System.out.println();
      
      // BEGIN: Added by BT
      int field = promptUserSort();
      
      System.out.println("Sorted database:");
      sort(dv, count, field);
      // END: Added by BT
      
      printArray(dv, count);
    }
    catch (FileNotFoundException ex) {
      System.err.println("file " + file + " does not exist.");
    }
  }

  /**
   * Reads a database file into the array.
   *
   * @param input The source of database information.
   * @param dv An array for storing the read database. The capacity should be large
   *   enough to contain all entries from the input source.
   *
   * @return the number of records read.
   */
  public static int read(InputStream input, Database[] dv)
  {
    Scanner scanner = new Scanner(input);
    int count = 0;

    while (scanner.hasNextLine())
    {
      String line = scanner.nextLine();
      
      if (line != null && !line.trim().isEmpty()) //&& short circuits
      {
        Database data = new Database(MAX_FIELDS);
        readRecord(line, data);//deconstruct the line into elements of item   
        dv[count] = data;
        count++;
      }     
    } 
    
    return count;
  }

  /**
   * Parses a single line entry and populates the field data to a database entry.
   *
   * @param line A single line entry from a database. It should follow the format:
   *   <last name> <first name> <SSN> <age>
   * @param data The database entry to populate.
   */
  private static void readRecord(String line, Database data)
  {
    StringTokenizer toke = new StringTokenizer(line);
    int k = 0;
    
    while (toke.hasMoreTokens())
    {
      if (toke.countTokens() == 1) //this is the last token -- the integer
      {
        data.set(k, new Integer (Integer.parseInt(toke.nextToken())));
      }
      else
      {
        data.set(k, toke.nextToken());  //A string
      }
        
      k++;
    }
  }

  /**
   * Prints the contents of a database to standard out.
   *
   * @param dv The database to output.
   * @param count The number of valid entries inside the database.
   */
  public static void printArray(Database[] dv, int count)
  {
    for(int j = 0; j < count; j++)
    {
      System.out.println(dv[j]);//prints the four elements
    }
  }

  /**
   * Sorts the array of database entries according to a field.
   * 
   * ^BT: Big O(Math.pow(n, 2))? Sub-loop checks current element
   * with all elements after it. If it's greater, then they swap places. In other
   * words, for each position in the array, the record with the smallest value
   * is placed in it.
   *
   * @param dv The database to sort. This will be sorted after this method is executed.
   * @param count The number of valid entries inside the database.
   * @param field The field to sort.
   */
  public static void sort(Database[] dv, int count, int field)
  {
  	Database tmp = new Database(MAX_FIELDS); // to store overwritten value temporarily
  	int k = 0;
  	for (int i=0; i<count; i++) { // thru all records
  		k = i; // 
  		for (int j=i; j<count; j++) { // writeable loop thru all records
  			Comparable a = dv[k].select(field);
  			Comparable b = dv[j].select(field);
  			if (a.compareTo(b) > 0) {
  				// swap below
  				tmp = dv[k];
  				dv[k] = dv[j];
  				dv[j] = tmp;
  			}
  		}
  	}
  }
  
  // TODO: invalid input checking
  public static int promptUserSort() {
  	Scanner scanner = new Scanner(System.in);
  	
  	String promptFieldSort = "Which field should the db be sorted on?\n";
    promptFieldSort += "1) Last Name\n";
    promptFieldSort += "2) First Name\n";
    promptFieldSort += "3) SSN\n";
    promptFieldSort += "4) Age\n";
    System.out.println(promptFieldSort);
    return scanner.nextInt()-1; // fix for choice=0..3;
  }
}

/**
 * A simple representation of a database entry with a fix amount of fields.
 */
class Database
{ 
  private final Comparable[] item;

  /**
   * Creates a new database instance.
   *
   * @param fieldCount The maximum number of fields this database entry can have.
   */
  public Database(int fieldCount)
  {
    item = new Comparable[fieldCount];
  }

  /**
   * @param fieldIndex The given index.
   *
   * @return true if the given index is within range.
   */
  private boolean isValidIndex(int fieldIndex) {
    return (fieldIndex >= 0 && fieldIndex < item.length);
  }
  
  /**
   * Gets the contents of a field from this entry.
   *
   * @param fieldIndex The index of the field to select.
   *
   * @return the contents of the field if the index is valid, null otherwise.
   */
  public Comparable select(int fieldIndex)
  {
    if (isValidIndex(fieldIndex))
    {
      return item[fieldIndex];      
    }
    else
    {
      return null;
    }
  }

  /**
   * Sets the field of the database with a new data. Nothing happens when if the fieldIndex
   * is invalid.
   *
   * @param fieldIndex The index of the field to modify.
   * @param data The new data.
   */
  public void set(int fieldIndex, Comparable data) {
    if (isValidIndex(fieldIndex))
    {
      item[fieldIndex] = data;
    }    
  }
  
  @Override public String toString()
  {
    StringBuilder str = new StringBuilder();

    for (Comparable field: item) {
      str.append(field);
      str.append(" ");
    }

    return str.toString();
  }   
}

