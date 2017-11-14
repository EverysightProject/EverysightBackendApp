package everysight.backend.PlacesAPI;

import com.google.gson.annotations.SerializedName;
import com.google.maps.model.LatLng;
import com.google.maps.model.PlaceType;
import com.google.maps.model.PriceLevel;
import com.google.maps.model.RankBy;

import java.util.List;


/**
 * Created by t-aryehe on 11/10/2017.
 */

public class NearByParameters {

    /**
     * location is the latitude/longitude around which to retrieve place information.
     */
    @SerializedName("location")
    public LatLng location;

    /**
     * radius defines the distance (in meters) within which to return place results. The maximum
     * allowed radius is 50,000 meters. Note that radius must not be included if rankby=DISTANCE is
     * specified.
     */
    @SerializedName("radius")
    public int radius;

}
