import java.util.List;
import org.sql2o.*;
import java.util.ArrayList;

public class Venue {
  private int mId;
  private String mName;

  public Venue(String name) {
    this.mName = name;
  }

  public int getId() {
    return mId;
  }

  public String getName() {
    return mName;
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
      String sql = "INSERT INTO venues(name) VALUES (:name)";
      this.mId = (int) con.createQuery(sql, true)
        .addParameter("name", this.mName)
        .executeUpdate()
        .getKey();
    }
  }

  public static Venue find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM venues where id=:id";
      Venue venue = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Venue.class);
      return venue;
    }
  }

  public void addBand(Band band) {
  try(Connection con = DB.sql2o.open()) {
    String sql = "INSERT INTO venues_played (band_id, venue_id) VALUES (:band_id, :venue_id)";
    con.createQuery(sql)
      .addParameter("venue_id", this.getId())
      .addParameter("band_id", band.getId())
      .executeUpdate();
    }
  }

  public ArrayList<Band> getBands() {
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