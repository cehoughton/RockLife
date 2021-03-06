import org.fluentlenium.adapter.FluentTest;
import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.junit.ClassRule;
import static org.assertj.core.api.Assertions.*;
import static org.fluentlenium.core.filter.FilterConstructor.*;
import static org.junit.Assert.*;


public class AppTest extends FluentTest {
  public WebDriver webDriver = new HtmlUnitDriver();

  @Override
  public WebDriver getDefaultDriver() {
    return webDriver;
  }

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @ClassRule
  public static ServerRule server = new ServerRule();

  @Test
    public void rootTest() {
      goTo("http://localhost:4567/");
      assertThat(pageSource()).contains("Rock On!");
    }

  @Test
  public void venueIsCreatedTest() {
    goTo("http://localhost:4567/");
    click("a", withText("Add or view a venue"));
    fill("#name").with("LaurelThirst");
    submit(".btn");
    assertThat(pageSource()).contains("LaurelThirst");
  }

  @Test
  public void bandIsAddedTest() {
    goTo("http://localhost:4567/");
    click("a", withText("Add or view a band"));
    fill("#name").with("LaurelThirst");
    submit(".btn");
    assertThat(pageSource()).contains("LaurelThirst");
  }

  @Test
  public void bandIsDeletedTest() {
    Band band = new Band("The Band");
    band.save();
    goTo("http://localhost:4567/bands/" + band.getId());
    submit(".deletebtn");
    assertEquals(0, Band.all().size());
  }

  @Test
    public void displayAllVenues() {
      Venue firstVenue = new Venue("First Ave");
      Venue secondVenue = new Venue("GoodFoot");
      firstVenue.save();
      secondVenue.save();
      goTo("http://localhost:4567/venues");
      assertThat(pageSource()).contains("GoodFoot");
      assertEquals(Venue.all().size(), 2);
    }

    @Test
      public void displayAllBands() {
        Band firstBand = new Band("The Arcs");
        Band secondBand = new Band("Tame Impalla");
        firstBand.save();
        secondBand.save();
        goTo("http://localhost:4567/bands");
        assertThat(pageSource()).contains("Tame Impalla");
        assertEquals(Band.all().size(), 2);
      }

      @Test
       public void addVenueToBand() {
         Band band = new Band("MarchFourth");
         band.save();
         Venue venue = new Venue("Alladin");
         venue.save();
         goTo("http://localhost:4567/bands/" + band.getId());
         click("option", withText("Alladin"));
         submit(".add-venue");
         assertThat(pageSource()).contains("Alladin");
       }

    @Test
     public void addBandToVenue() {
       Venue venue = new Venue("Alladin");
       venue.save();
       Band firstBand = new Band("MarchFourth");
       firstBand.save();
       goTo("http://localhost:4567/venues/" + venue.getId());
       click("option", withText("MarchFourth"));
       submit(".add-band");
       assertThat(pageSource()).contains("MarchFourth");
     }


  // @Test
  // public void venueIsDisplayedTest() {
  //   Venue myVenue = new Venue("Crystal Ballroom");
  //   String venuePath = String.format("http://localhost:4567/%d", myVenue.getId());
  //   goTo(venuePath);
  //   assertThat(pageSource()).contains("Crystal Ballroom");
  // }

}
