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

import everysight.backend.DirectionsAPI.RouteParameters;

public class MyServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        System.out.print("hey");
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        try {
            String parameters = IOUtils.toString(req.getInputStream(), "UTF-8");

            Gson gson = new Gson();
            RouteParameters routeParameters = gson.fromJson(parameters,RouteParameters.class);
            resp.setContentType("application/json");
            GeoApiContext context = new GeoApiContext(new GaeRequestHandler()).setApiKey("AIzaSyBnZO0bEmFgi3XN64zeafdAQSurfdfe4F8");

            DirectionsApiRequest request = DirectionsApi.getDirections(context,
                    routeParameters.getOriginName(), routeParameters.getDestinationName());

            if (routeParameters.isSetOrigin) {
                request.origin(routeParameters.getOrigin());
            }
            if (routeParameters.isSetDestination) {
                request.origin(routeParameters.getDestination());
            }
            if (routeParameters.isSetTravelMode) {
                request.mode(routeParameters.getTravelMode());
            }
            if (routeParameters.isSetAvoid) {
                for (DirectionsApi.RouteRestriction pref: routeParameters.getAvoid()) {
                    request.avoid(pref);
                }
            }
            if (routeParameters.isSetUnits) {
                request.units(routeParameters.getUnits());
            }
            if (routeParameters.isSetRegion)
            {
                request.region(routeParameters.getRegion());
            }
            if(routeParameters.isSetArrivalTime)
            {
                ReadableInstant arrivalTime = new DateTime(routeParameters.getArrivalTime());
                request.arrivalTime(arrivalTime);
            }
            if(routeParameters.isSetDepartureTime)
            {
                ReadableInstant departureTime = new DateTime(routeParameters.getDepartureTime());
                request.arrivalTime(departureTime);
            }
            if(routeParameters.isSetPlaces)
            {
                List<String> places = routeParameters.getPlaces();
                for (String place: places) {
                    request.waypoints(place);
                }
            }
            if(routeParameters.isSetWaypoints)
            {
                List<LatLng> waypoints = routeParameters.getWaypoints();
                for (LatLng waypoint: waypoints) {
                    request.waypoints(waypoint);
                }
            }
            if(routeParameters.isSetAlternatives)
            {
                request.alternatives(routeParameters.getAlternatives());
            }
            if(routeParameters.isSetTransmitMode)
            {
                for (TransitMode transit: routeParameters.getTransmitMode()) {
                    request.transitMode(transit);
                }
            }
            if(routeParameters.isSetTransitRoutingPreference)
            {
                for(TransitRoutingPreference pref: routeParameters.getTransitRoutingPreference())
                {
                    request.transitRoutingPreference(pref);
                }
            }
            if(routeParameters.isSetTraficModel)
            {
                request.trafficModel(routeParameters.getTraficModel());
            }
            DirectionsResult result = request.await();

            resp.getWriter().println(gson.toJson(result));
        }
        catch(Exception e)
        {
            resp.getWriter().println(e.toString());
        }
    }


}
