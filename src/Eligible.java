public interface Eligible {
    /* Any entity sitting for PS must implement these methods.  */
    public void accept();
    public void reject();
    public void withdraw();
    public boolean isEligible(Station station);
}
