import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.util.Delay;

public class MotorSpeed 
{
    public static void main(String[] args) {
		LCD.drawString("Motor Ident", 0, 0);
		Motor.A.resetTachoCount();
		Button.waitForAnyPress();
		LCD.clear();
		int speed = 720;
		
		Motor.A.setSpeed(speed);
		Motor.A.rotate(360,true);
		
		int x =0;
		int i=0;
		while(Motor.A.isMoving())
		{	
			if(i==8)
			{
				i=0;
				x+=3;		
			}
			LCD.drawInt(Motor.A.getTachoCount(), x,i);
			log.writeLog(Motor.A.getTachoCount());
			log.transmit(true);
			Delay.msDelay(10);	
			i++;
		}
		Button.waitForAnyPress();
      }
	  
 }