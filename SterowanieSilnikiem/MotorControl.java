import lejos.nxt.NXTMotor;
import java.lang.*;


public class MotorControl extends BasicMotor implements Runnable
{
	
	private int measureTime;
	private int tachoDegrees;

	public setMeasureTime(int measureTime)
	{
		this.measureTime = measureTime;
	}
	public int getMeasureTime()
	{
		return this.measureTime;
	}
	/**
	make step into 
	*/
	protected void step(int powerValue)
	{
		setPower(int powerValue);
	}
	
	private void setHalfPower()
	{
		setPower(50);
	}
	
	private void setFullPower()
	{
		setPower(100);
	}
	
	private void setZeroPower()
	{
		stop();
	}
	
	/**
	Propably error's here. Tacho measures in Thread.
	*/
	@Override 
	public void run()
	{
		tachoCount();
	}
	
	/**
	*start measure 
	*/
	protected int tachoCount(int measureTime)
	{
		//start measure 
		long time = System.currentTimeMillis() + measureTime; //get time
		while(System.currentTimeMillis() < time)
			getTachoCount();	//measure	
	}
	
	private int getTachoDegrees()
	{
		return this.tachoDegrees;
	}
	
	private void setTachoDegrees(int tachoDegrees)
	{
		this.tachoDegrees = tachoDegrees;
	}
	
	/**
	*set stepValue and watch results
	*/
	private void experiment(int stepValue, int measureTime)
	{
		step(stepValue);
		resetTachoCount();
		setTachoDegrees(0);
		tachoCount(measureTime); //get into Thread
	}
}

