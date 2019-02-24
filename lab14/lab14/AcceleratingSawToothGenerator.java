package lab14;

import lab14lib.Generator;

public class AcceleratingSawToothGenerator implements Generator {
    private int period;
    private int state;
    private double factor;

    public AcceleratingSawToothGenerator(int period, double factor) {
        state = 0;
        this.period = period;
        this.factor = factor;
    }

    @Override
    public double next() {
        if (state < period) {
            state += 1;
            return normalize(state);
        }
        state = 0;
        period = (int) (period * factor);
        return normalize(0);
    }

    private double normalize(int value) {
        double half = (period - 1) / 2.0;
        return (value - half) / half;
    }
}
