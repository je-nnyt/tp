package voyatrip;

import org.json.JSONObject;

import java.util.Optional;

public class EmptyTripListStub extends TripList {
    /**
     * Constructor for EmptyTripListStub.
     * This constructor creates an empty TripList object.
     */
    public EmptyTripListStub() {
    }

    @Override
    public Optional<JSONObject> toJson() {
        // Return an empty JSON object
        return Optional.empty();
    }
}
