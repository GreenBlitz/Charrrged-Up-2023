package edu.greenblitz.utils.breakCoastToggle;

public class BreakCoastRunnables {

    private Runnable breakRunnable;
    private Runnable coastRunnable;

    public BreakCoastRunnables(Runnable breakRunnable, Runnable coastRunnable) {
        this.breakRunnable = breakRunnable;
        this.coastRunnable = coastRunnable;
    }

    public Runnable getBreakRunnable() {
        return breakRunnable;
    }

    public void setBreakRunnable(Runnable breakRunnable) {
        this.breakRunnable = breakRunnable;
    }

    public Runnable getCoastRunnable() {
        return coastRunnable;
    }

    public void setCoastRunnable(Runnable coastRunnable) {
        this.coastRunnable = coastRunnable;
    }

}
