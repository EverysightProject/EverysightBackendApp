/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Servlet Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloWorld
*/

package everysight.backend;

import com.google.gson.Gson;
import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GaeRequestHandler;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.LatLng;
import com.google.maps.model.TransitMode;
import com.google.maps.model.TransitRoutingPreference;

import org.apache.commons.io.IOUtils;
import org.joda.time.DateTime;
import org.joda.time.ReadableInstant;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import everysight.backend.DirectionsAPI.DirectionRequest;
import everysight.backend.DirectionsAPI.RouteParameters;
import everysight.backend.PlacesAPI.PlacesRequest;

public class MyServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        System.out.print("hey");
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String uri = req.getRequestURI();

        if (uri.equals("/directions"))
        {
            DirectionRequest directionRequest = new DirectionRequest();
            directionRequest.Send(req,resp);
        }
        else if (uri.equals("/places"))
        {
            PlacesRequest placesRequest = new PlacesRequest();
            placesRequest.Send(req,resp);
        }

    }


}
