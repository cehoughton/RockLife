import org.sql2o.*;
import java.util.List;

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
}
