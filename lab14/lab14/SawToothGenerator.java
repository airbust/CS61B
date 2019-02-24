package lab14;

import lab14lib.Generator;

public class SawToothGenerator implements Generator {
    private int period;
    private int state;

    public SawToothGenerator(int period) {
        state = 0;
        this.period = period;
    }

    @Override
    public double next() {
        state = (state + 1) % period;
        return normalize(state);
    }

    private double normalize(int value) {
        double half = (period - 1) / 2.0;
        return (value - half) / half;
    }
}
