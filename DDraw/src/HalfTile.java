
public class HalfTile {

    public String domainA;
    public String domainB;

    public HalfTile(String domainA, String domainB) {
        this.domainA = domainA;
        this.domainB = domainB;
    }

    public String getDomainA() {
        return domainA;
    }

    public String getDomainB() {
        return domainB;
    }

    @Override
    public String toString() {
        return this.domainA + this.domainB;
    }
}
