// Dount:=events n thread 
import java.awt.*;
import java.io.*;
import java.awt.event.*;
class FontDialog extends Dialog implements ActionListener,ItemListener
{
	int w=410,h=300;
	String font="";//to save the selected font
	int size;//to save size
	int style=Font.PLAIN;//to save bold and italic config
	NotePad ref;//reference to NotPad to change font
	List selectFont,selectStyle,selectSize;//display list of font
	TextField showFont,showStyle,showSize;//display the selected font type, style and size
	//Panel center;//the center panel
	Panel p;//to fit sample 
	GridBagConstraints c=new GridBagConstraints();
	Label sampleFont;
	Button ok,cancel;
	FontDialog(NotePad parent)
	{
		super(parent,"Font",true);
		ref=parent;
		p=new Panel();
		p.setBackground(new Color(176,226,255));
		c.insets=new Insets(3,3,3,3);
		p.setLayout(new GridBagLayout());
		setLabels();
		setTextFields();
		setLists();
		setSample();
		add(p);
		p=new Panel();
		p.setBackground(new Color(176,226,255));
		p.setLayout(new GridLayout(1,2,10,10));
		setButtons();
		setSize(w,h);
		addWindowListener(new FontClosure());
		ok.requestFocus();
		centerFrame();
		setResizable(false);
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

	//adds all the labels
	void setLabels()
	{
		c.anchor=c.LAST_LINE_START;
		p.add(new Label("Font:",Label.LEFT),c);
		c.gridx=1;
		c.anchor=c.LAST_LINE_START;
		p.add(new Label("Type:",Label.LEFT));
		c.gridx=2;
		c.anchor=c.LAST_LINE_START;
		p.add(new Label("Size:",Label.LEFT));
	}
	//adds all the textfields
	void setTextFields()
	{
		showFont=new TextField(15);
		showSize=new TextField(2);
		showStyle=new TextField(12);
		c.gridx=0;
		c.gridy=1;
		c.fill=c.HORIZONTAL;
		p.add(showFont,c);
		c.gridx=1;
		p.add(showStyle,c);
		c.gridx=2;
		p.add(showSize,c);
		FontKeyListener fkl=new FontKeyListener(this);
		showSize.addKeyListener(fkl);
		showStyle.addKeyListener(fkl);
		showFont.addKeyListener(fkl);
		showFont.addTextListener(fkl);
	}
	//adds all the lists
	void setLists()
	{
		selectFont=new List(8);
		initializeFontList();
		selectStyle=new List(8);
		initializeStyleList();
		selectSize=new List(8);
		initializeSizeList();
		selectFont.addItemListener(this);
		selectStyle.addItemListener(this);
		selectSize.addItemListener(this);
		c.gridy=2;
		c.gridx=0;
		p.add(selectFont,c);
		c.gridx=1;
		p.add(selectStyle,c);
		c.gridx=2;
		p.add(selectSize,c);
		font=ref.text.getFont().getName();
		int ind=font.indexOf(".");
		if(ind!=-1)
			font=font.substring(0,ind);
		System.out.println("Font name="+font);
		style=ref.text.getFont().getStyle();
		size=ref.text.getFont().getSize();
		search(font,selectFont);
		search(String.valueOf(size),selectSize);
		selectStyle.select(style);
		showSize.setText(selectSize.getSelectedItem());
		showStyle.setText(selectStyle.getSelectedItem());
		showFont.setText(selectFont.getSelectedItem());
		showFont.select(0,showFont.getText().length());
		System.out.println(" is Dialog selected :"+selectFont.getSelectedItem());
	}
	//adds options in size list
	void initializeSizeList()
	{
		int i;
		for(i=8;i<13;i++)
			selectSize.add(""+i);
		for(i=14;i<30;i=i+2)
			selectSize.add(""+i);
		selectSize.add("36");
		selectSize.add("48");
		selectSize.add("72");
	}
	//adds options in style list
	void initializeStyleList()
	{
		selectStyle.add("PLAIN");
		selectStyle.add("BOLD");
		selectStyle.add("ITALIC");
		selectStyle.add("BOLD ITALIC");
	}
	//following methods adds all the font  to the list
	void initializeFontList()
	{
		Font f;
		String fonts[]=GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		for(int i=0;i<fonts.length;i++)
		{	
			f=new Font(fonts[i],Font.PLAIN,12);
			selectFont.setFont(f);
			selectFont.add(fonts[i]);
		}
	}
	//changes  the sample label after each item event 
	void showSample()
	{
		Font t=new Font(font,style,size);
		sampleFont.setFont(new Font(font,style,size));
		setSize(w,h+1);
		setSize( w,h);
	
	}
	//adds the sample label to the frame
	void setSample()
	{
		sampleFont=new Label("Aa Bb Cc");
		//sampleFont.setFont( new Font("Times New Roman",Font.BOLD,11) );
		showSample();
		c.gridy=3;
		c.gridx=0;
		//p.add(sampleFont);
		c.gridwidth=3;
		c.gridheight=2;
		p.add(sampleFont,c);
		c.gridwidth=1;
		Font f=ref.text.getFont();
		System.out.println(f);
	}
	//adds the button sto the frame
	void setButtons()
	{
		ok=new Button("OK");
		cancel=new Button("Cancel");
		c.gridy=6;
		c.gridx=1;
		p.add(ok,c);
		c.gridx=2;
		p.add(cancel,c);
		ok.addActionListener(this);
		cancel.addActionListener(this);
		add(p,BorderLayout.SOUTH);
	}
	/*public Insets getInsets()
	{
		return new Insets(4,20,20,20);
	}*/

	public void actionPerformed(ActionEvent ae)
	{
		Button source=(Button)ae.getSource();
		if(source==ok)
		{
			size=Integer.parseInt(showSize.getText());
			ref.text.setFont(new Font(font,style,size));
			try
			{
				FileWriter f=new FileWriter("fontfile.fl");
				String font="";
				int style=0,size=0;
				font=ref.text.getFont().getFontName();
				style=ref.text.getFont().getStyle();
				size=ref.text.getFont().getSize();
				f.write(font+"\r\n");
				f.write(String.valueOf(style)+"\r\n");
				f.write(String.valueOf(size));
				f.close();
			}
			catch (Exception e)
			{
			}
		}
		dispose();
	}
	public void itemStateChanged(ItemEvent ie)
	{
		String temp;
		List source=(List)ie.getSource();
		if(source==selectFont)
		{
			font=source.getSelectedItem();
			showFont.setText(font);
			showFont.setCaretPosition(showFont.getText().length());
			System.out.println("HI i m item ");
			showFont.select(0,font.length());
			//showFont.requestFocus();
		}
		else if(source==selectSize)
		{
			temp=source.getSelectedItem();
			showSize.setText(temp);
			showSize.setCaretPosition(2);
			showSize.select(0,temp.length());
			//showSize.requestFocus();
			size=Integer.parseInt(temp);
		}
		else 
		{
			temp=source.getSelectedItem();
			showStyle.setText(temp);
			showStyle.select(0,temp.length());
			//showStyle.requestFocus();
			if(temp.equalsIgnoreCase("PLAIN"))
				style=Font.PLAIN;
			else if(temp.equalsIgnoreCase("ITALIC"))
				style=Font.ITALIC;
			else if(temp.equalsIgnoreCase("BOLD"))
				style=Font.BOLD;
			else if(temp.equalsIgnoreCase("BOLD ITALIC"))
				style=Font.ITALIC+Font.BOLD;
			showStyle.setCaretPosition(showStyle.getText().length());
		}
		showSample();
	}
	void search(String key,List arr)
		{
			int mid,low,high;
			high=arr.getItemCount();
			low=0;
			mid=(high+low)/2;
			while(low<=high)
			{
				System.out.println("HI i ma binarysearch mid="+mid+"  low="+low+"  high="+high);
				if(key.equalsIgnoreCase(arr.getItem(mid)))
				{
					arr.select(mid);
					System.out.println("break "+arr.getItem(mid));
					break;
				}
				else if(key.compareTo(arr.getItem(mid))>0)
					low=mid+1;
				else
					high=mid-1;
				mid=(high+low)/2;
			}
			
		}
	class FontClosure extends WindowAdapter
	{
		public void windowClosing(WindowEvent we)
		{
			System.out.println("i am fontclosure");
			dispose();
			ref.fontDialog=null;
		}
	}
}
class FontKeyListener extends KeyAdapter implements TextListener
{
	FontDialog ref;
	FontKeyListener(FontDialog ob)
	{
		ref=ob;
	}
	public void keyPressed(KeyEvent ke)
	{
		TextField src=(TextField)ke.getSource();
		if(src==ref.showSize)
		{
			char ch=ke.getKeyChar();
			if( !(ch>47&&ch<58) )
			{
				ke.setKeyCode(ke.VK_BACK_SPACE);
			}
		}
		else 
		{
			char ch=ke.getKeyChar();
			if( ! ((ch>=65&&ch<=90) || (ch>=97&&ch<=122)))
				ke.setKeyCode(8);
			else
			{
			}
		}
	}
	//why is this called?
	void search(String key,List l)
	{
		int i;
		try
		{
			for(i=0;i<l.getItemCount();i++)
			{
				//System.out.println("HI i ma search called by textValueChanged");
				if(key.equalsIgnoreCase(l.getItem(i).substring(0,key.length())))
				{
					l.select(i);
					break;
				}
			}
		}
		catch (IndexOutOfBoundsException ioobe)
		{
		}
	}
	public void textValueChanged(TextEvent te)
	{
		System.out.println("HI i ma  textValueChanged");
		TextField src=(TextField)te.getSource();
		if(src==ref.showFont)
			{
				search(ref.showFont.getText(),ref.selectFont);
			}
			else
			{
				search(ref.showStyle.getText(),ref.selectStyle);
			}
	}
}