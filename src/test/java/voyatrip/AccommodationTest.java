package voyatrip;


import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import voyatrip.model.Accommodation;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AccommodationTest {

    @Test
    public void toFromJson_fullParm_success() {
        Accommodation accommodation = new Accommodation("Hotel", 100, new ArrayList<>(List.of(1, 2, 3)));
        JSONObject json = accommodation.toJson();
        Accommodation newAccommodation = Accommodation.fromJson(json);

        assertEquals(accommodation, newAccommodation);
    }
}
