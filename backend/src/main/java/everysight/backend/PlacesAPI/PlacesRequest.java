package everysight.backend.PlacesAPI;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.maps.GaeRequestHandler;
import com.google.maps.GeoApiContext;
import com.google.maps.NearbySearchRequest;
import com.google.maps.PlacesApi;
import com.google.maps.internal.LatLngAdapter;
import com.google.maps.model.PlacesSearchResponse;
import com.google.maps.model.LatLng;
import com.google.maps.model.PlacesSearchResult;

import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by t-aryehe on 11/10/2017.
 */

public class PlacesRequest {
    public void Send(HttpServletRequest req, HttpServletResponse resp) {
        try
        {
            GeoApiContext context = new GeoApiContext(new GaeRequestHandler()).setApiKey("AIzaSyCadFFcx87Nqf3A2EEARaRNLMvjLzzu6yU");

            String parameters = IOUtils.toString(req.getInputStream(), "UTF-8");

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LatLng.class, new LatLngAdapter())
                    .create();
            NearByParameters nearByParameters = gson.fromJson(parameters,NearByParameters.class);
            resp.setContentType("application/json");

            NearbySearchRequest nearbySearchRequest = PlacesApi.nearbySearchQuery(context, nearByParameters.location)
                    .radius(nearByParameters.radius);

            PlacesSearchResponse response = nearbySearchRequest.await();

            JsonArray places = new JsonArray();
            for(PlacesSearchResult res : response.results)
            {
                MyPlace p = new MyPlace();
                p.name = res.name;
                p.location = res.geometry.location;
                places.add(gson.toJson(p));
            }
            resp.getWriter().println(places);
        }
        catch(Exception e)
        {
            int x = 0;
        }
    }
}
