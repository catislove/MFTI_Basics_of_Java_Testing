public class CalculateShippingCosts {
    public static final int MIN_DELIVERY_COST = 400;

    public enum Size {
        SMALL, LARGE
    }

    public enum Fragility {
        FRAGILE, NON_FRAGILE()
    }

    public enum LoadLevel {
        VERY_HIGH(1.6),
        HIGH(1.4),
        INCREASED(1.2),
        NORMAL(1.0);

        private final double coefficient;

        LoadLevel(double coefficient) {
            this.coefficient = coefficient;
        }
        public double getCoefficient() {
            return coefficient;
        }
    }

    public static int calculateDeliveryCost(int distance, Size size, Fragility fragility, LoadLevel loadLevel) {
        int cost = 0;
        int distanceCost = 0;
        int fragilityCost = 0;
        int sizeCost = 0;
        // Расчет стоимости по расстоянию
        if (distance > 30) {
            distanceCost += 300;
        } else if (distance > 10) {
            distanceCost += 200;
        } else if (distance > 2) {
            distanceCost += 100;
        } else {
            distanceCost += 50;
        }

        // Проверка на хрупкость
        if (fragility == Fragility.FRAGILE) {
            if (distance > 30) {
                throw new IllegalArgumentException("Fragile goods cannot be transported over a distance of more than 30 km");
            }
            fragilityCost += 300;
        }

        // Расчет стоимости по габаритам
        if (size == Size.LARGE) {
            sizeCost += 200;
        } else {
            sizeCost += 100;
        }

        // Применение коэффициента загруженности
        cost = (int) ((distanceCost + fragilityCost + sizeCost) * loadLevel.getCoefficient());

        // Проверка минимальной стоимости
        if (cost < 400) {
            return MIN_DELIVERY_COST;
        } else {
            return cost;
        }
    }
}