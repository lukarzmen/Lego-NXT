import lejos.util.Delay;
import lejos.nxt.*;

public class MotorIdent 
{
    public static void main(String[] args) 
	{
		LCD.clear();
		LCD.drawString("Motor Ident", 0, 0);
		
		NXTMotor m1 = new NXTMotor(MotorPort.A);
		m1.resetTachoCount();
		
		Button.waitForAnyPress();
		LCD.clear();
		int power = 10;
		
		m1.setPower(20);
		
		int x =0;
		int i=0;
		while(m1.isMoving())
		{	
			if(i==8)
			{
				i=0;
				x+=3;		
			}
			LCD.drawInt(m1.getTachoCount(), x,i);
			Delay.msDelay(10);	
			i++;
		}
		Button.waitForAnyPress();
      }
	  
 }