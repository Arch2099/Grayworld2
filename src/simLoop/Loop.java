package simLoop;

public class Loop <T extends BaseLoop>{
    
    private T program;
    private int fps;
    private long period;
    private int frameSkips;
    private int delaysPerYield;
    private boolean running = false;
    private boolean render = true;
    
    public Loop(){
    }
    
    public Loop(T t){
        this(t,30,5,16);
    }
    
    public Loop(T t, int fps){
        this(t,fps,5,16);
    }
    
    public Loop(T t, int fps,int skips, int delays){
        program = t;
        this.fps = fps;
        period = (long) 1000.0 / this.fps * 1000000L;
        frameSkips = skips;
        delaysPerYield = delays;
    }
    
    public void init(T t, int fps){
        init(t,fps,5,16);
    }
    
    public void init(T t, int fps,int skips, int delays){
        program = t;
        this.fps = fps;
        period = (long) 1000.0 / this.fps * 1000000L;
        frameSkips = skips;
        delaysPerYield = delays;
    }
    
    public void begin(){
        run();
    }
    
    public void setFPS(int fps)
    	{
    	this.fps = fps;
    	period = (long) 1000.0 / this.fps * 1000000L;
    	
    	}
    public void setSkips(int skips){frameSkips = skips;}
    public void setDelays(int delays){delaysPerYield = delays;}
    public int getFPS(){return fps;}
    public int getSkips(){return frameSkips;}
    public int getDelays(){return delaysPerYield;}

    public void run() {
        program.initWorld();
      
        long beforeTime, afterTime, timeDiff, sleepTime;
        int noDelays = 0;
        long excess = 0L, overSleepTime = 0L;
        beforeTime = System.nanoTime();
          
        running = true;
        while(running) {
            program.updateWorld();
            if (fps < 100)
            {
            	program.renderWorld();
            }

            afterTime = System.nanoTime();
            timeDiff = afterTime - beforeTime;
            sleepTime = (period - timeDiff) - overSleepTime;

            if (sleepTime > 0) {
                try { Thread.sleep(sleepTime/1000000L); }
                catch(InterruptedException ex){ex.printStackTrace();}
                overSleepTime = System.nanoTime() - afterTime - sleepTime;
            }
            else {
                excess -= sleepTime;
                overSleepTime = 0L;
                if (++noDelays >= delaysPerYield) {
                    Thread.yield( );
                    noDelays = 0;
                }
            }
            beforeTime = System.nanoTime();
            int skips = 0;
            while((excess > period) && (skips < frameSkips)) {
                excess -= period;
                program.updateWorld();
                skips++;
            }
        }
        System.exit(0);
    }
}