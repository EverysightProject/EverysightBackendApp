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

            Gson gson = new Gson();

            RouteParameters routeParameters = gson.fromJson(parameters,RouteParameters.class);
            resp.setContentType("application/json");
            GeoApiContext context = new GeoApiContext(new GaeRequestHandler()).setApiKey("AIzaSyBWyD-yoElojJ8uo5uG7XSZzJOSWqKx6SI");

            DirectionsApiRequest request = DirectionsApi.getDirections(context,
                    routeParameters.getOriginName(), routeParameters.getDestinationName());

                request.mode(TravelMode.WALKING);

            DirectionsResult result = request.await();

            resp.getWriter().println(gson.toJson(result));
        }
        catch(Exception e)
        {
            int x = 0;
        }
    }
}
