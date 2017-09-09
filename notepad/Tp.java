import java.awt.*;
import java.applet.*;
import java.awt.event.*;
//<applet code="Tp" height=400 width=400></applet>
public class Tp extends Applet
{
	
	TextArea t=new TextArea();
	public void init()
	{
		add(t);
		t.addKeyListener(new mkl());
		t.addTextListener(new tkl());
	}
	class mkl extends KeyAdapter
	{
		public void keyTyped(KeyEvent ke)
		{
			System.out.println("TYPED");
		}
		public void keyPressed(KeyEvent ke)
		{
			if(ke.getKeyCode()==ke.VK_CONTROL)
				System.out.println("caret= "+t.getCaretPosition());
			System.out.println("PRESSED");
		}
	}
	class tkl implements TextListener
	{
		public void textValueChanged(TextEvent te)
		{
			System.out.println("CHANGED");
		}
	}
}