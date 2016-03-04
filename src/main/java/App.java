import java.util.ArrayList;
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
          Band band = new Band(name);
          band.save();
          response.redirect("/bands");
          return null;
        });

        get("/bands/:id", (request,response) -> {
          HashMap<String, Object> model = new HashMap<String, Object>();
          int id = Integer.parseInt(request.params("id"));
          Band band = Band.find(id);
          model.put("band", band);
          model.put("allVenues", Venue.all());
          model.put("template", "templates/band.vtl");
          return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        // get("/bands/:id/edit", (request, response) -> {
        //   HashMap<String, Object> model = new HashMap<String, Object>();
        //
        //   Band thisBand = Band.find(
        //     Integer.parseInt(
        //     request.params("id")));
        //
        //   model.put("band", thisBand);
        //   model.put("allVenues", Venue.all());
        //   model.put("template", "templates/edit-band.vtl");
        //   return new ModelAndView(model, layout);
        // }, new VelocityTemplateEngine());

        // post("/bands/:id/change-description", (request, response) -> {
        //   HashMap<String, Object> model = new HashMap<String, Object>();
        //
        //   Band thisBand = Band.find(
        //     Integer.parseInt(
        //     request.params("id")));
        //
        //   thisBand.update(request.queryParams("newdescription"));
        //
        //   response.redirect("/bands/" + thisBand.getId());
        //   return null;
        //   });




        get("/bands/:id", (request, response) -> {
          HashMap<String, Object> model = new HashMap<String, Object>();
          Band band = Band.find(Integer.parseInt(request.params("id")));
          model.put("band", band);
          model.put("venue", Venue.all());
          model.put("template", "templates/band.vtl");
          return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        post("bands/:id/add_venues", (request, response) -> {
          Band band = Band.find(Integer.parseInt(request.params("id")));
          band.addVenue(Integer.parseInt(request.queryParams("venue_id")));
          response.redirect("/bands/" + band.getId());
          return null;
        });

        // post("/add_venues", (request, response) -> {
        //   HashMap<String, Object> model = new HashMap<String, Object>();
        //
        //   Venue newVenue = Venue.find(
        //     Integer.parseInt(
        //     request.queryParams("venue_id")));
        //
        //   Band thisBand = Band.find(
        //     Integer.parseInt(
        //     request.queryParams("band_id")));
        //
        //   thisBand.addVenue(newVenue);
        //
        //   response.redirect("/bands/" + thisBand.getId());
        //   return null;
        // });
        //

        post("/bands/deleteband", (request, response) -> {
          Band band = Band.find(Integer.parseInt(request.queryParams("band-id")));
          band.delete();
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
           Venue venue = Venue.find(Integer.parseInt(request.queryParams("id")));
           venue.addBand(Integer.parseInt(request.queryParams("add_band")));
           response.redirect("/venue/" + venue.getId());
           return null;
         });

        // post("/add_venues", (request, response) -> {
        //   int bandId = Integer.parseInt(request.queryParams("band_id"));
        //   int venueId = Integer.parseInt(request.queryParams("venue_id"));
        //   Venue venue = Venue.find(venueId);
        //   Venue band = Venue.find(bandId);
        //   band.addVenue(venue);
        //   response.redirect("/bands/" + bandId);
        //   return null;
        // });








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
