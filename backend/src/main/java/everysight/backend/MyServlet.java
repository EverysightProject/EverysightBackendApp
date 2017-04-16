/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Servlet Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloWorld
*/

package everysight.backend;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.DirectionsApi;
import com.google.maps.GaeRequestHandler;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.EncodedPolyline;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.*;

public class MyServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.setContentType("text/plain");
        resp.getWriter().println("Please use the form to POST to this url");
        try {
            GeoApiContext context = new GeoApiContext(new GaeRequestHandler()).setApiKey("AIzaSyBnZO0bEmFgi3XN64zeafdAQSurfdfe4F8");
            //GeoApiContext context = new GeoApiContext().setApiKey("AIzaSyBnZO0bEmFgi3XN64zeafdAQSurfdfe4F8");

            DirectionsResult result = DirectionsApi.getDirections(context, "Sydney, AU",
                    "Melbourne, AU").await();
            //GeocodingResult[] results = GeocodingApi.geocode(context,"1600 Amphitheatre Parkway Mountain View, CA 94043").await();
            for (int i=0;i<result.routes.length;i++ ) {
                //for (int j=0;j<result.routes[i].legs.length;j++)
                    resp.getWriter().println(result.routes[i].summary);
            }
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String name = req.getParameter("name");
        resp.setContentType("text/plain");
        if (name == null) {
            resp.getWriter().println("Please enter a name");
        }
        resp.getWriter().println("Hello " + name);
    }
}
