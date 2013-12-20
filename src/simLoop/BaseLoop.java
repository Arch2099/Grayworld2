package simLoop;

public interface BaseLoop {
    
    Loop runner = new Loop();
    
    public void initWorld();
    public void updateWorld();
    public void renderWorld();

}