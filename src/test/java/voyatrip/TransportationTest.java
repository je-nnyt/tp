package voyatrip;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TransportationTest {
    @Test
    void toFromString_fullParm_success() {
        Transportation transportation = new Transportation("VietJet Air", "Plane", 200, 2, 4);

        JSONObject jsonObject = transportation.toJson();
        Transportation newTransportation = Transportation.fromJson(jsonObject);

        assertEquals(transportation, newTransportation);
    }
}
