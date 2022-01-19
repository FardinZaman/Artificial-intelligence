public class TwoNodes {
    int one;
    int another;

    public TwoNodes(int one, int another) {
        this.one = one;
        this.another = another;
    }

    public boolean checkMatch(TwoNodes incoming)
    {
        if(this.one == incoming.one)
        {
            if(this.another == incoming.another)
                return true;
        }

        if(this.one == incoming.another)
        {
            if(this.another == incoming.one)
                return true;
        }

        return false;
    }
}
