import lejos.util.Delay;
import java.io.*;
import lejos.nxt.*;

public class MotorIdent 
{
	public static final double Kp = 0.2654;
	public static final double Ti = 0.0648;
	public static final double Td = 0.0091;
	
	public static void controllerPID(int referenceValue)
	{
		double eeSum = 0;
		double power = 0;
		double power_ = 0;
		int tc = 10;
		double error=0;
		double error_= 0;
		double error__ = 0;
		double refValue = (double)referenceValue;
		drawInfo("Wartosc zadana" + referenceValue);
		NXTMotor m1 = new NXTMotor(MotorPort.A);
		m1.resetTachoCount();
		
		while(true)
		{
			if(Button.ENTER.isDown())
				break;
			error = refValue - m1.getTachoCount();
			power = power_ + Kp * (error - error_) + Kp * tc/Ti*error_ + Kp * Td/tc*(error+error__-2.0*error_);
			power_ = power;
			error__ = error_;
			error_ = error;
			
			if(power>100)
				power=100;
			else if(power<0)
				power=0;
			m1.setPower((int)power);
		}
	}
	
    public static void main(String[] args) 
	{
		// drawInfo("Identtyfikacja silnika");
		// FileOutputStream out = null;
		// File data = new File("log2.txt");
		
		// try 
		// {
			// out = new FileOutputStream(data);
		// } 
		// catch(IOException e) 
		// {
			// drawInfo("IOException");
			// Button.waitForAnyPress();
			// System.exit(1);
		// }
		
		// DataOutputStream dataOut = new DataOutputStream(out);
		
		// NXTMotor m1 = new NXTMotor(MotorPort.A);
		// m1.resetTachoCount();
		
		// Button.waitForAnyPress();
		// Delay.msDelay(1000);
		// drawInfo("PWM: 10%");
		// int power = 10;
		
		// m1.setPower(20);
		// //dot¹d dzia³a
		
		// try
		// {			
			// int measurmentValue[] = new int[2000];
			// int measureTime[] = new int[2000];
			// for(int i=0; i<2000; i++)
			// {
				// Delay.msDelay(20);
				// int angle = m1.getTachoCount();
				// dataOut.writeInt(angle);
				// measurmentValue[i] = angle;
				// measureTime[i] = i*10;
			// }
			// out.close(); // flush the buffer and write the file
		// } 	
		// catch (IOException e) 
		// {
			// drawInfo("IOException");
		// }
		
		// Sound.beep();
		controllerPID(180);
		Delay.msDelay(50);
		Button.waitForAnyPress();
      }
	public static void drawInfo(String info)
	{
		LCD.clear();
		LCD.drawString(info, 0, 0);
	}
	  
 }