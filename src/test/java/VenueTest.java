import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.Arrays;
import java.util.List;

public class VenueTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
      assertEquals(0, Venue.all().size());
  }

  @Test
  public void equals_returnsTrueIfNamesAreTheSame() {
    Venue firstVenue = new Venue("First ave");
    Venue secondVenue = new Venue("First ave");
    assertTrue(firstVenue.equals(secondVenue));
  }

  @Test
  public void save_savesIntoDatabase_true() {
    Venue myVenue = new Venue("First ave");
    myVenue.save();
    assertTrue(Venue.all().get(0).equals(myVenue));
}

@Test
 public void getBands_retrievesAllBandsFromDatabase_BandsList() {
   Band myBand = new Band("Ween");
   myBand.save();
   Venue firstVenue = new Venue("First ave");
   firstVenue.save();
   Venue secondVenue = new Venue("LaurelThirst");
   secondVenue.save();
   Venue[] Venue = new Venue[] { firstVenue, secondVenue};
   assertFalse(myBand.getVenues().containsAll(Arrays.asList(Venue)));
 }
}
