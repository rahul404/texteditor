import java.awt.*;
import java.awt.event.*;
class Goto extends Dialog implements ActionListener,TextListener 
{
	NotePad ref;
	Button go_to,cancel;
	TextField lineNoTextField;
	Label lineNo;
	Goto(NotePad parent)
	{
		super(parent,"Goto",false);
		ref=parent;
		init();
	}
	void init()
	{
		go_to=new Button("Go to");
		cancel=new Button("Cancel");
		lineNoTextField=new TextField(18);
		lineNo=new Label("Line no:");
		setLayout(new GridBagLayout());
		GridBagConstraints c=new GridBagConstraints();
		c.insets=new Insets(5,0,5,0);
		c.gridx=0;
		c.gridy=0;
		add(lineNo,c);
		c.gridy++;
		c.gridwidth=3;
		add(lineNoTextField,c);
		c.gridwidth=1;
		c.gridy++;
		c.gridx++;
		add(go_to,c);
		c.gridx++;
		add(cancel,c);
		lineNoTextField.addActionListener(this);
		lineNoTextField.addTextListener(this);
		go_to.addActionListener(this);
		cancel.addActionListener(this);
		addWindowListener(new CloseGoto());
		setSize(200,160);
		setBackground(new Color(176,226,255));
		centerFrame();
		setVisible(true);
	}
	private void centerFrame() 
	{

            Dimension windowSize = getSize();
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            Point centerPoint = ge.getCenterPoint();

            int dx = centerPoint.x - windowSize.width / 2;
            int dy = centerPoint.y - windowSize.height / 2;    
            setLocation(dx, dy);
    }
	class CloseGoto extends WindowAdapter
	{
		public void windowClosing(WindowEvent we)
		{
			dispose();
		}
	}
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource()==cancel)
			dispose();
		else
			gotoLine();
	}
	void gotoLine()
	{
		int nLine=0,start=0,ind=0;;
		String temp=ref.text.getText();
		int target=Integer.parseInt( lineNoTextField.getText() );
		for(int i=1;i<target;i++)
		{
			ind=temp.indexOf("\n",start);
			System.out.println("ind ="+ind);
			if(ind==-1)
				break;
			start=ind+1;
			nLine++;
		}
		if(ind==-1)
			ind=temp.lastIndexOf("\n");
		{	
			if(ind!=-1)
				ind++;
			int end=temp.indexOf("\r",ind);
			if(end==-1)
				end=temp.length();
			/*else
				end--;*/
			ref.text.select(ind-nLine,end-nLine);
			System.out.println("Ind ="+ind+"\tend= "+end+"\tnLine= "+nLine);
			ref.text.requestFocus();
		}
	}
	public void textValueChanged(TextEvent te)
	{
		String temp=lineNoTextField.getText();
		if(temp.length()==0)
			return;
		char ch=temp.charAt(temp.length()-1);
		if(ch > (char)47 && ch < (char)58)
			;
		else 
		{
			System.out.println("i am inside else ch= "+(int)ch);
			lineNoTextField.setText( temp.substring(0,temp.length()-1) );
			lineNoTextField.setCaretPosition(lineNoTextField.getText().length());
		}
	}
	/*class mkl extends KeyAdapter
	{
		String temp=lineNoTextField.getText();
		public void keyTyped(KeyEvent ke)
		{
			int code=ke.getKeyCode();
			if(code> 0x2F && code< 0x3A)
				;
			else 
			{
				System.out.println("i am inside else ch= "+(int)code);
				lineNoTextField.setText( temp.substring(0,temp.length()-1) );
				lineNoTextField.setCaretPosition(lineNoTextField.getText().length());
			}
		}
	}*/
}