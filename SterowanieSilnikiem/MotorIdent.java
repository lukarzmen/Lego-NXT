import lejos.nxt.*;
import java.lang.*;
import lejos.util.Delay;

public class MotorIdent
{
	private int timeMs=0;
	private int power=0;
		
	public static void main(String[] args)
	{
	
	NXTMotor MA = new NXTMotor(MotorPort.A);	

	}
	
	public void setPower(int power)
	{
		if(power>100)
			power=100;
		this.power=power;
	}
	public int getPower()
	{
		return this.power;
	}
	public void setTimeMs(int timeMs)
	{
		this.timeMs=timeMs;
	}
	public int getTimeMs()
	{
		return this.timeMs;
	}
	
	public void measureOnLCD(NXTMotor MA, int power, int periodMs)
	{
		MotorIdent motor = new MotorIdent();
		motor.setPower(power);
		motor.setTimeMs(periodMs);
		
		LCD.clear();
		LCD.drawString("Power: " + motor.getPower(), 0, 0);
		LCD.drawString("Time period: " + motor.getPower(), 0, 0);
		int i=0;
		int x=0;		
		Delay.msDelay(1000);		
		
		while(MA.isMoving())
		{	
			LCD.drawInt(MA.getTachoCount(), x,i);
			i++;
			if(i==8)
			{
				i=0;
				x++;		
			}	
			Delay.msDelay(motor.getTimeMs());	
			if(Button.readButtons()>0)
				MA.stop();

			Button.waitForAnyPress();

			LCD.clear();
			LCD.drawString("Power: 40", 0, 0);

			MA.stop();
			Button.waitForAnyPress();
		}
	}
	
	public void sendLogToDataLogger(NXTMotor MA, int power, int periodMs)
	{
		NXTDataLogger dlog = new NXTDataLogger();
		
	}

}
	
