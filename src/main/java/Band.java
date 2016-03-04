import org.sql2o.*;
import java.util.List;
import java.util.ArrayList;

public class Band {
  private int id;
  private String name;


  public Band (String name) {
    this.name = name;

  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
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
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .executeUpdate()
        .getKey();
    }
  }

  //READ
  public static List<Band> all() {
    String sql = "SELECT id , name FROM bands";
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Band.class);
    }
  }

  //FIND
   public static Band find(int id) {
     try (Connection con = DB.sql2o.open()) {
       String sql = "SELECT id , name  FROM bands WHERE id=:id";
       Band myBand = con.createQuery(sql)
         .addParameter("id", id)
         .executeAndFetchFirst(Band.class);
       return myBand;
     }
   }

   //UPDATE
    public void update(String newName) {
      this.name = newName;
      try(Connection con = DB.sql2o.open()) {
        String sql = "UPDATE bands SET name = :newName WHERE id = :id";
        con.createQuery(sql)
          .addParameter("newName", newName)
          .addParameter("id", this.id)
          .executeUpdate();
      }
    }

    //DELETE
    public void delete() {
      try(Connection con = DB.sql2o.open()) {
        String sql = "DELETE FROM bands WHERE id = :id";
        con.createQuery(sql)
          .addParameter("id", this.id)
          .executeUpdate();
      }
    }

    public void addVenue(int venueId) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO venues_played (band_id, venue_id) VALUES (:band_id, :venue_id)";
      con.createQuery(sql)
        .addParameter("band_id", this.getId())
        .addParameter("venue_id", venueId)
        .executeUpdate();
      }
    }

    // public List<Venue> getAllVenues() {
    // try (Connection con = DB.sql2o.open()) {
    //   String sql = "SELECT stores.id AS mId, stores.name AS mName FROM carries INNER JOIN stores ON carries.store_id = stores.id WHERE carries.brand_id = :id ORDER BY stores.name";
    //   return con.createQuery(sql)
    //     .addParameter("id", mId)
    //     .executeAndFetch(Store.class);
    // }

    public ArrayList<Venue> getVenues() {
    try(Connection con = DB.sql2o.open()){
      String sql = "SELECT venue_id  FROM venues_played WHERE band_id = :band_id";
      List<Integer> venueIds = con.createQuery(sql)
        .addParameter("band_id", this.getId())
        .executeAndFetch(Integer.class);

      ArrayList<Venue> venues = new ArrayList<Venue>();

      for (Integer venueId : venueIds) {
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
