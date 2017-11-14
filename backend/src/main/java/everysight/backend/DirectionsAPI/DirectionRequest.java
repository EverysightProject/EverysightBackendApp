package everysight.backend.DirectionsAPI;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GaeRequestHandler;
import com.google.maps.GeoApiContext;
import com.google.maps.internal.LatLngAdapter;
import com.google.maps.internal.SafeEnumAdapter;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.LatLng;
import com.google.maps.model.TransitMode;
import com.google.maps.model.TransitRoutingPreference;
import com.google.maps.model.TravelMode;

import org.apache.commons.io.IOUtils;
import org.joda.time.DateTime;
import org.joda.time.ReadableInstant;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by t-aryehe on 11/10/2017.
 */

public class DirectionRequest {

    public void Send(HttpServletRequest req, HttpServletResponse resp)
    {
        try {
            String parameters = IOUtils.toString(req.getInputStream(), "UTF-8");

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LatLng.class, new LatLngAdapter())
                    .registerTypeAdapter(TravelMode.class, new SafeEnumAdapter<TravelMode>(TravelMode.UNKNOWN))
                    .create();
            RouteParameters routeParameters = gson.fromJson(parameters,RouteParameters.class);
            resp.setContentType("application/json");
            GeoApiContext context = new GeoApiContext(new GaeRequestHandler()).setApiKey("AIzaSyBnZO0bEmFgi3XN64zeafdAQSurfdfe4F8");

            DirectionsApiRequest request = DirectionsApi.getDirections(context,
                    routeParameters.getOriginName(), routeParameters.getDestinationName());

            if (routeParameters.isSetOrigin) {
                request.origin(routeParameters.getOrigin());
            }
            if (routeParameters.isSetDestination) {
                request.destination(routeParameters.getDestination());
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
        }
    }
}
