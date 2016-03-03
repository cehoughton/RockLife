import java.util.HashMap;
import java.util.List;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
    public static void main(String[] args) {
        staticFileLocation("/public");
        String layout = "templates/layout.vtl";

        get("/", (request, response) -> {
          HashMap<String, Object> model = new HashMap<String, Object>();
          model.put("template", "templates/index.vtl");
          return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        get("/venues", (request, response) -> {
          HashMap<String, Object> model = new HashMap<String, Object>();
          List<Venue> venues = Venue.all();
          model.put("venues", venues);
          model.put("template", "templates/venues.vtl");
          return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        post("/venues", (request, response) -> {
          HashMap<String, Object> model = new HashMap<String, Object>();
          String name = request.queryParams("name");
          Venue newVenue = new Venue(name);
          newVenue.save();
          response.redirect("/venues");
          return null;
        });

        get("/bands", (request, response) -> {
          HashMap<String, Object> model = new HashMap<String, Object>();
          List<Band> bands = Band.all();
          model.put("bands", bands);
          model.put("template", "templates/bands.vtl");
          return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        post("/bands", (request, response) -> {
          HashMap<String, Object> model = new HashMap<String, Object>();
          String name = request.queryParams("name");
          Band newBand = new Band(name);
          newBand.save();
          response.redirect("/bands");
          return null;
        });

        get("/venue/:id", (request, response) -> {
          HashMap<String, Object> model = new HashMap<String, Object>();
          model.put("venue", Venue.find(Integer.parseInt(request.params(":id"))));
          model.put("bands", Band.all());
          model.put("template", "templates/venue.vtl");
          return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        post("/venue/:id/add-band", (request, response) -> {
           Venue venue = Venue.find(Integer.parseInt(request.queryParams("venue_id")));
           venue.addBand(Integer.parseInt(request.queryParams("add_band")));
           response.redirect("/venue/" + venue.getId());
           return null;
         });







        //RESTful ARCHITECTURE
        //Use POST to create something on the server
        //Use GET to retrieve something from the server
        //Use PUT to change or update something on the server
        //Use DELETE to remove or delete something on the server
        //Keep URLs intuitive
        //Each request from client contains all info necessary for that request

        //ROUTES: Home Page

        // get("/", (request, response) -> {
        //     HashMap<String, Object> model = new HashMap<String, Object>();

        //     model.put("template", "templates/index.vtl");
        //     return new ModelAndView(model, layout);
        // }, new VelocityTemplateEngine());

        //ROUTES: Identification of Resources

        //ROUTES: Changing Resources

    }
}
