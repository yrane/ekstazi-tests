public abstract MyAbstractClass {

    @Autowire
    private MyMock myMock;

    protected String sayHello() {
            return myMock.getHello() + ", " + getName();
    }

    public abstract String getName();
}
