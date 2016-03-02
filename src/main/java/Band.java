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
}
