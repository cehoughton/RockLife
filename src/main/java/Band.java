import org.sql2o.*;
import java.util.List;
import java.util.ArrayList;

public class Band {
  private int mId;
  private String mName;


  public Band (String name) {
    this.mName = name;

  }

  public int getId() {
    return mId;
  }

  public String getName() {
    return mName;
  }

  @Override
  public boolean equals(Object otherBand){
    if (!(otherBand instanceof Band)) {
      return false;
    } else {
      Band newBand = (Band) otherBand;
      return this.getName().equals(newBand.getName()) &&
             this.getId() == newBand.getId();
    }
  }

  public void save() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO bands (name) VALUES (:name)";
      this.mId = (int) con.createQuery(sql, true)
        .addParameter("name", this.mName)
        .executeUpdate()
        .getKey();
    }
  }

  //READ
  public static List<Band> all() {
    String sql = "SELECT id AS mId, name AS mName FROM bands";
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Band.class);
    }
  }

  //FIND
   public static Band find(int id) {
     try (Connection con = DB.sql2o.open()) {
       String sql = "SELECT id AS mId, name AS mName FROM bands WHERE id=:id";
       Band myBand = con.createQuery(sql)
         .addParameter("id", id)
         .executeAndFetchFirst(Band.class);
       return myBand;
     }
   }

   //UPDATE
    public void update(String newName) {
      this.mName = newName;
      try(Connection con = DB.sql2o.open()) {
        String sql = "UPDATE bands SET name = :newName WHERE id = :id";
        con.createQuery(sql)
          .addParameter("newName", newName)
          .addParameter("id", this.mId)
          .executeUpdate();
      }
    }

    //DELETE
    public void delete() {
      try(Connection con = DB.sql2o.open()) {
        String sql = "DELETE FROM bands WHERE id = :id";
        con.createQuery(sql)
          .addParameter("id", this.mId)
          .executeUpdate();
      }
    }

    public void addVenue(Venue venue) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO venues_played (band_id, venue_id) VALUES (:band_id, :venue_id)";
      con.createQuery(sql)
        .addParameter("band_id", this.getId())
        .addParameter("venue_id", venue.getId())
        .executeUpdate();
      }
    }

    public ArrayList<Venue> getVenues() {
    try(Connection con = DB.sql2o.open()){
      String sql = "SELECT venue_id FROM venues_played WHERE band_id = :band_id";
      List<Integer> venueIds = con.createQuery(sql)
        .addParameter("band_id", this.getId())
        .executeAndFetch(Integer.class);

      ArrayList<Venue> venues = new ArrayList<Venue>();

      for (Integer venueId : venuesIds) {
          String venueQuery = "Select * From venues WHERE id = :venueId";
          Venue venue = con.createQuery(venueQuery)
            .addParameter("venueId", venueId)
            .executeAndFetchFirst(Venue.class);
          venues.add(venue);
      }
      return venues;
    }
  }
}
