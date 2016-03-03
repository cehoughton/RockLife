import org.junit.*;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.List;

public class BandTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
 public void all_emptyAtFirst() {
   assertEquals(Band.all().size(), 0);
 }

 @Test
 public void equals_returnsTrueIfNamesAretheSame() {
   Band firstBand = new Band("Black Keys");
   Band secondBand = new Band("Black Keys");
   assertTrue(firstBand.equals(secondBand));
 }

 @Test
 public void save_savesIntoDatabase_true() {
   Band myBand = new Band("JackStraw");
   myBand.save();
   assertTrue(Band.all().get(0).equals(myBand));
 }

 @Test
  public void find_findBandInDatabase_true() {
    Band myBand = new Band("Frank Black");
    myBand.save();
    Band savedBand = Band.find(myBand.getId());
    assertTrue(myBand.equals(savedBand));
  }

  @Test
    public void update_changesBandInDatabase_true() {
      Band myBand = new Band("Frank Black");
      myBand.save();
      myBand.update("Pert'near Sandstone");
      assertEquals("Pert'near Sandstone", myBand.getName());
    }

  @Test
  public void delete_removesBandInDatabase_true() {
    Band myBand = new Band("Justin Bieber");
    myBand.save();
    myBand.delete();
    assertEquals(0, Band.all().size());
  }

 //  @Test
 // public void getVenues_retrievesAllVenuesFromDatabase_VenuesList() {
 //   Venue myVenue = new Venue("GoodFoot");
 //   myVenue.save();
 //
 //   Band myBand = new Band("JuJuba");
 //   myBand.save();
 //
 //   myBand.addVenue(myVenue);
 //   List savedVenues = myBand.getVenues();
 //   assertEquals(savedVenues.size(), 1);
 // }
}
