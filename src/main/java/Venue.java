import java.util.List;
import org.sql2o.*;
import java.util.ArrayList;

public class Venue {
  private int id;
  private String name;

  public Venue(String name) {
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public static List<Venue> all() {
    String sql = "SELECT id , name  FROM venues";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Venue.class);
    }
  }

  @Override
  public boolean equals(Object otherVenue){
    if (!(otherVenue instanceof Venue)) {
      return false;
    } else {
      Venue newVenue = (Venue) otherVenue;
      return this.getName().equals(newVenue.getName()) &&
        this.getId() == newVenue.getId();
    }
  }

  public void save() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO venues (name) VALUES (:name)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .executeUpdate()
        .getKey();
    }
  }

  public static Venue find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT id, name FROM venues WHERE id = :id";
      Venue venue = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Venue.class);
      return venue;
    }
  }

  //DELETE
  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM venues WHERE id = :id";
      con.createQuery(sql)
        .addParameter("id", this.id)
        .executeUpdate();

      String joinDeleteQuery = "DELETE FROM venues_played WHERE venue_id = :venueId";
        con.createQuery(joinDeleteQuery)
          .addParameter("venueId", this.getId())
          .executeUpdate();
      }
    }



  public void addBand(int bandId) {
  try(Connection con = DB.sql2o.open()) {
    String sql = "INSERT INTO venues_played (band_id, venue_id) VALUES (:band_id, :venue_id)";
    con.createQuery(sql)
      .addParameter("venue_id", this.getId())
      .addParameter("band_id", bandId)
      .executeUpdate();
    }
  }

  public List<Band> getBands() {
  try(Connection con = DB.sql2o.open()){
    String sql = "SELECT band_id FROM venues_played WHERE venue_id = :venue_id";
    List<Integer> bandIds = con.createQuery(sql)
      .addParameter("venue_id", this.getId())
      .executeAndFetch(Integer.class);

    ArrayList<Band> bands = new ArrayList<Band>();

    for (Integer bandId : bandIds) {
        String bandQuery = "Select * From bands WHERE id = :bandId";
        Band band = con.createQuery(bandQuery)
          .addParameter("bandId", bandId)
          .executeAndFetchFirst(Band.class);
        bands.add(band);
    }
    return bands;
  }
 }
}
