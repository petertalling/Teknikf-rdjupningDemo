public class CurrentState {
    public int quantityInStock = 0;
    public int sequenceNumber = 0;

    public CurrentState() {
    }
    public CurrentState clone(){
        CurrentState cs = new CurrentState();
        cs.quantityInStock = this.quantityInStock;
        cs.sequenceNumber = this.sequenceNumber;
        return cs;
    }
}
