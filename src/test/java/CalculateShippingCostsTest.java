import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculateShippingCostsTest {

    @Test
    @DisplayName("Test calculation of shipping cost for regular cargo")
    void testCalculateDeliveryCostNormal() {
        int cost = CalculateShippingCosts.calculateDeliveryCost(15, CalculateShippingCosts.Size.SMALL,
                CalculateShippingCosts.Fragility.NON_FRAGILE, CalculateShippingCosts.LoadLevel.NORMAL);
        assertEquals(400, cost);
    }

    @Test
    @DisplayName("Large Cargo Shipping Cost Calculation Test")
    void testCalculateDeliveryCostLarge() {
        int cost = CalculateShippingCosts.calculateDeliveryCost(5, CalculateShippingCosts.Size.LARGE,
                CalculateShippingCosts.Fragility.NON_FRAGILE, CalculateShippingCosts.LoadLevel.NORMAL);
        assertEquals(400, cost);
    }

    @ParameterizedTest
    @MethodSource("provideDistanceAndExpectedCosts")
    @DisplayName("Parameterized test of shipping cost calculation")
    @Tag("Parameterized")
    void testCalculateDeliveryCostParameterized(int distance, int expectedCost) {
        int cost = CalculateShippingCosts.calculateDeliveryCost(distance, CalculateShippingCosts.Size.SMALL,
                CalculateShippingCosts.Fragility.FRAGILE, CalculateShippingCosts.LoadLevel.NORMAL);
        assertEquals(expectedCost, cost);
    }

    private static Object[][] provideDistanceAndExpectedCosts() {
        return new Object[][]{
                {1, 450},
                {2, 450},
                {5, 500},
                {10, 500},
                {15, 600},
                {29, 600}
        };
    }

    @Test
    @DisplayName("Fragile load over-distance test")
    void testFragileItemExceedsDistance() {
        Exception exception = org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> {
            CalculateShippingCosts.calculateDeliveryCost(40, CalculateShippingCosts.Size.SMALL,
                    CalculateShippingCosts.Fragility.FRAGILE, CalculateShippingCosts.LoadLevel.NORMAL);
        });
        assertEquals("Fragile goods cannot be transported over a distance of more than 30 km", exception.getMessage());
    }
}
