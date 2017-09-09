import java.awt.event.*;
//class IndentTextEvent implements TextListener
/*{
	NotePad ref;
	IndentTextEvent(NotePad ob)
	{
		ref=ob;
	}
	public void textValueChanged(TextEvent te)
	{
		
	}
}*/
class IndentItemListener implements ItemListener
{
	NotePad ref;
	IndentItemListener(NotePad ob)
	{
		ref=ob;
	}
	public void itemStateChanged(ItemEvent ie)
	{
		if(ref.indent.getState())
		{
			System.out.println("iil");
			ref.text.addKeyListener(ref.ike);
			ref.text.addTextListener(ref.ike);
			System.out.println("was i executed after textlistener?");
		}
		else
		{
			ref.text.removeKeyListener(ref.ike);
			ref.text.removeTextListener(ref.ike);
			System.out.println("was i executed remove?");
		}
	}
}
class IndentKeyEvent extends KeyAdapter implements TextListener
{
	int tab=0;
	NotePad ref;
	IndentKeyEvent(NotePad ob)
	{
		ref=ob;
	}
	public void keyPressed(KeyEvent ke)
	{
		if(ke.getKeyCode()==ke.VK_BACK_SPACE)
		{
			if(ref.text.getCaretPosition()==0)
				return;
			if(ref.text.getText().charAt(ref.text.getText().length()-1)=='\t')
				ref.tab--;
			return;
		}
		/*if(ke.getKeyCode()==ke.VK_ENTER)
		{
			ke.setKeyChar((char)ke.VK_CONTROL);
			ref.text.append("\r\n");
			for(int i=0;i<tab;i++)
			{
				ref.text.append("\t");
				System.out.println("TAB WAS CALLED");
			}
		}*/
		else if(ke.getKeyCode()==ke.VK_TAB && (ref.text.getCaretPosition()==1 || ref.text.getText().charAt(ref.text.getCaretPosition()-1)=='\n' || ref.text.getText().charAt(ref.text.getCaretPosition()-1)=='\t' ))
			tab++;
	}
	public void textValueChanged(TextEvent te)
	{
		String temp="";
		System.out.println("was i executed lol?");
		System.out.println("tab= "+tab);
		temp=ref.text.getText();
		if(temp.length()==0)
			return;
		if(temp.charAt(temp.length()-1)=='\n')
			for(int i=0;i<tab;i++)
				ref.text.append("\t");
	}
}