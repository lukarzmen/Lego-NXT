import lejos.util.Delay;
import java.io.*;
import lejos.nxt.*;

public class MotorIdent 
{
	public static final double Kp = 0.58;
	public static final double Ti = 3;
	public static final double Td = 0.42;
	
    public static void main(String[] args) 
	{
		decision();
		Delay.msDelay(50);
		Button.waitForAnyPress();
    }
	
	//regulator PID
	public static void controllerPID(int referenceValue)
	{
		double eeSum = 0;
		double power = 0;
		double power_ = 0;
		double tc = 0.01;
		int delay = (int)(tc*1000); //delay in ms
		double error = 0;
		double error_ = 0;
		double error__ = 0;
		double refValue = (double)referenceValue;
		
		drawInfo("Wartosc zadana" + referenceValue);
		NXTMotor m1 = new NXTMotor(MotorPort.A);
		m1.resetTachoCount();
		
		LCD.clear();
		LCD.drawString("Wartosc zadana: ", 0, 0);
		LCD.drawString("" + referenceValue,6,2);
		
		while(true)
		{	
			//przerwij
			if(Button.ESCAPE.isDown())
				{
					m1.stop();
					Delay.msDelay(100);
					break;
				}
			LCD.drawString("Wyjscie: " + m1.getTachoCount(), 0, 4);
			Delay.msDelay(delay); //czas cyklu ok. tc
			
			//obliczanie sterowania
			error = refValue - m1.getTachoCount();
			power = power_ + Kp * (error - error_) + Kp * tc/Ti*error_ + Kp * Td/tc*(error+error__-2.0*error_);
			power_ = power;
			error__ = error_;
			error_ = error;
			
			//ograniczenia sterowania
			if(power>100)
				power = 100;
			else if(power<0)
			{
				m1.backward();
				power = -power;
			}
			else
				m1.forward();
			m1.setPower((int)power);	
		}
	}
	
	//regulator dwustanowy
	public static void bistableController(int referenceValue, int offset, int power)
	{
		LCD.clear();
		LCD.drawString("Wartosc zadana: ", 0, 0);
		LCD.drawString("" + referenceValue,6,2);
		NXTMotor m1 = new NXTMotor(MotorPort.A);
		m1.resetTachoCount();
		m1.forward();
		
		while(true)
		{
			LCD.drawString("Wyjscie: " + m1.getTachoCount(), 0, 4);
			if(Button.ESCAPE.isDown())
				{
					m1.stop();
					Delay.msDelay(200);
					break;
				}
			if(m1.getTachoCount() < referenceValue + offset)
			{
				m1.forward();
				m1.setPower(power);
			}
			else if(m1.getTachoCount() > referenceValue - offset)
			{
				m1.backward();
				m1.setPower(power);
			}
			else if((m1.getTachoCount() < referenceValue + offset) && (m1.getTachoCount() > referenceValue - offset))
				m1.setPower(0);
			Delay.msDelay(50);
		}
	}
	
	//glowna petla obslugi zdarzen
	public static void decision()
	{
		info();
		Delay.msDelay(200);
		while(true)
		{
			if(Button.RIGHT.isDown())
				bistableController(360, 1, 10);
			if(Button.LEFT.isDown())
				controllerPID(360);
			if(Button.ENTER.isDown())
				identToFile(20);
			if(Button.ESCAPE.isDown())
			{	
				info();
				continue;
			}
		}
	}
	
	//wyswietlanie informacji w menu glownym
	public static void info()
	{
	LCD.clear();
	LCD.drawString("Lewy: PID", 0, 0);
	LCD.drawString("Prawy: dwustawny",0,2);
	LCD.drawString("Enter: pomiar",0,4);
	}
	
	//identyfikacja obiektu i zapis do pliku
	public static void identToFile(int referenceVal)
	{
		drawInfo("Identyfikacja");
		LCD.drawString("do pliku",0,2);
		FileOutputStream out = null;
		File data = new File("log2.txt");

		try 
		{
			out = new FileOutputStream(data);
		} 
		catch(IOException e) 
		{
			drawInfo("IOException");
			Button.waitForAnyPress();
			System.exit(1);
		}

		DataOutputStream dataOut = new DataOutputStream(out);

		NXTMotor m1 = new NXTMotor(MotorPort.A);
		m1.resetTachoCount();

		Button.waitForAnyPress();
		Delay.msDelay(1000);
		drawInfo("Wartosc zadana: ");
		LCD.drawString("" + referenceVal,6,2);

		m1.setPower(referenceVal);

		try
		{			
			int measurmentValue[] = new int[2000];
			int measureTime[] = new int[2000];
			int delay = 10;
			for(int i=0; i<1000; i++)
			{
				Delay.msDelay(delay);
				int angle = m1.getTachoCount();
				dataOut.writeInt(angle);
				measurmentValue[i] = angle;
				measureTime[i] = i*delay;
				
				if(Button.ESCAPE.isDown())
				{
					m1.stop();
					break;
				}
					
			}
			m1.stop();
			out.close(); 
		} 	
		catch (IOException e) 
		{
			drawInfo("IOException");
		}
		Delay.msDelay(200);
	}
	
	//wyswietlanie prostych informacji na ekranie
	public static void drawInfo(String info)
	{
		LCD.clear();
		LCD.drawString(info, 0, 0);
	}
	  
 }