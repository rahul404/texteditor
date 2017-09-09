import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
class FindReplaceDialog extends Dialog implements ActionListener//,Runnable
{	
	int startIndex=0,foundAt=-1,nLine=0,nLineStart=0;
	boolean all=false;
	Checkbox caseInsensitive,wholeWordOnly;
	Panel p;
	public NotePad ref;
	Label find_What ,replace_What;
	TextField find_TextField,replace_TextField;
	Button replace,findNext,replaceAll,cancel;
	//public Thread t;
	public String dTitle="";
	FindReplaceDialog(NotePad parent,String title)
	{
		super(parent,title,false);
		ref=parent;
		dTitle=title;
		//t=new Thread(this);
		ref=parent;
		dTitle=title;
		//t.start();
		init();	
		setResizable(false);
	}
	public void run()
	{
		init();
	}
	void init()
	{
		//ref=parent;
		setLayout(new BorderLayout(3,3));
		System.out.println("i was called i am mr constructor");
		if(dTitle.equalsIgnoreCase("Find"))
		{
			initFind();
			setSize(300,170);
		}
		else
		{
			initReplace();
			setSize(410,300);
		}
		addWindowListener(new FindReplaceClosure());
		centerFrame();
		setBackground(new Color(255,255,255));
		ImageIcon ic=new ImageIcon("icon.PNG");
		setIconImage(ic.getImage());
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

	void initFind()
	{
		p=new Panel();
		p.setBackground(new Color(176,226,255));
		p.setLayout(new GridBagLayout());
		GridBagConstraints c=new GridBagConstraints();
		find_What=new Label("Find_What: ",Label.LEFT);
		find_TextField=new TextField(15);
		cancel=new Button("cancel");
		findNext=new Button("find next");
		c.gridx=0;
		c.gridy=0;
		c.fill=GridBagConstraints.HORIZONTAL;
		c.insets=new Insets(5,5,5,5);
		c.gridwidth=2;
		p.add(findNext,c);
		c.gridy=1;
		p.add(cancel,c);
		add(p,BorderLayout.EAST);
		p=new Panel();
		p.setBackground(new Color(176,226,255));
		p.setLayout(new GridBagLayout());
		c.gridx=0;
		c.gridy=0;
		p.add(find_What,c);
		c.gridx=2;
		p.add(find_TextField,c);
		c.gridwidth=3;
		c.gridy=2;
		c.gridx=0;
		caseInsensitive=new Checkbox("Case Insensitive");
		wholeWordOnly=new Checkbox("Whole word");
		c.gridwidth=0;
		p.add(caseInsensitive,c);
		c.gridy=3;
		p.add(wholeWordOnly,c);
		add(p);
		findNext.addActionListener(this);
		cancel.addActionListener(this);
		MyTextListener mtl =new MyTextListener();
		find_TextField.addTextListener(mtl);
		findNext.setEnabled(false);
	}
	void initReplace()
	{
		p=new Panel();
		p.setBackground(new Color(176,226,255));
		p.setLayout(new GridBagLayout());
		//p.setLayout(new GridLayout(4,1));
		GridBagConstraints c=new GridBagConstraints();
		find_What=new Label("Find_what:");
		replace_What=new Label("Replace_what:",Label.LEFT);
		find_TextField=new TextField(15);
		replace_TextField=new TextField(15);
		replace=new Button("replace");
		replaceAll=new Button("replace all");
		cancel=new Button("cancel");
		findNext=new Button("find next");
		wholeWordOnly=new Checkbox("Whole Word Only");
		caseInsensitive=new Checkbox("Case Insensitive");
		c.fill=GridBagConstraints.HORIZONTAL;
		c.insets=new Insets(5,5,5,5);
		c.gridwidth=2;
		//c.fill=GridBagConstraints.VERTICAL;
		c.gridx=0;
		c.gridy=0;
		p.add(findNext,c);
		c.gridy=1;
		p.add(replace,c);
		c.gridy=2;
		p.add(replaceAll,c);
		c.gridy=3;
		p.add(cancel,c);
		add(p,BorderLayout.EAST);
		p=new Panel();
		p.setLayout(new GridBagLayout());
		c.insets= new Insets(0,0,0,0);
		c.anchor=c.LAST_LINE_START;
		c.gridwidth=1;
		c.gridx=0;
		c.gridy=0;
		p.add(find_What,c);
		c.gridy=1;
		p.add(replace_What,c);
		c.gridwidth=2;
		c.gridx=1;
		c.gridy=0;
		p.add(find_TextField,c);
		c.gridy=1;
		p.add(replace_TextField,c);
		c.gridx=0;
		c.gridy=2;
		p.add(wholeWordOnly,c);
		c.gridy=3;
		p.add(caseInsensitive,c);
		add(p);
		/*p.setLayout(new GridLayout(2,2,5,5));
		p.add(find_What);
		p.add(find_TextField);
		p.add(replace_What);
		p.add(replace_TextField);
		add(p);*/
		findNext.addActionListener(this);
		replace.addActionListener(this);
		replaceAll.addActionListener(this);
		cancel.addActionListener(this);
		MyTextListener mtl =new MyTextListener();
		find_TextField.addTextListener(mtl);
		replace_TextField.addTextListener(mtl);
		replace_TextField.setText("");
		replace_TextField.setEditable(false);
		replace.setEnabled(false);
		replaceAll.setEnabled(false);
		findNext.setEnabled(false);
	}
	/*void addEventHandlers(int i)
	{
		findNext.addActionListener(this);
		replace.addActionListener(this);
		replaceAll.addActionListener(this);
		cancel.addActionListener(this);
	}*/
	public void actionPerformed(ActionEvent ae)
	{
		Button src=(Button)ae.getSource();
		if(src==cancel)
		{
			dispose();
			ref.findReplaceDialog=null;
			//ref.replaceDialog=null;
		}
		else if(src==findNext)
		{
			find();
			return;
		}
		else if(src==replaceAll)
			all=true;
		System.out.println(all);
		do
		{
			find();
			replace();
		}
		while (all);
	}

	void find()
	{
		String msg=ref.text.getText();
		String f=find_TextField.getText();
		int i;
		boolean res=false;
		char ch1=' ',ch2=ch1;
		System.out.println("STate = "+wholeWordOnly.getState());
		for(i=startIndex;i<=(msg.length()-f.length());i++)
		{
			if(caseInsensitive.getState())
				res=msg.substring(i,i+f.length()).equalsIgnoreCase(f);
			else
				res=msg.substring(i,i+f.length()).equals(f);
			if(res)
			{
				if(wholeWordOnly.getState())
				{
					try
					{
						if(i==0)
							ch1=' ';
						else
							ch1=msg.charAt(i-1);
						if(i+f.length()==msg.length())
							ch2=' ';
						else
							ch2=msg.charAt(i+f.length());
						if( !( (ch1=='\n'||ch1=='\t'||ch1==' ') && (ch2=='\n'||ch2=='\t'||ch2=='\r'||ch2==' ') ))
						{
							System.out.println("i was executed");
							continue;
						}
					}
					catch (StringIndexOutOfBoundsException sioobe)
					{
					}
				}
				startIndex=i+f.length();
				foundAt=i-nLine;
				break;
			}
			if(msg.charAt(i)=='\n')
			{
				//nLineStart=i+1;
				nLine++;
			}
		}
		//System.out.println("i= "+i+" msg.length= "+msg.length()+" last index ="+msg.lastIndexOf(f));
		if(i>msg.length()-f.length())
		{
			startIndex=0;
			foundAt=-1;
			nLine=0;
			nLineStart=0;
		}
		else
		{
			//System.out.println("Found at="+foundAt+" till= "+find_TextField.getText().length()+foundAt);
			ref.text.select(foundAt,find_TextField.getText().length()+foundAt);
			ref.text.requestFocus();
		}
	}
	void replace()
	{
		if(foundAt==-1)
		{
			all=false;
			//ref.t.setEditable(true);
			return;
		}
		String text=ref.text.getText(),f=find_TextField.getText(),r=replace_TextField.getText();
		ref.text.replaceRange(r,foundAt,foundAt+f.length());
		startIndex+=r.length()-f.length();
	}
	public Insets getInsets()
	{
		return new Insets(10,10,10,10);
	}
	class MyTextListener implements TextListener
	{
		void changeReplace(TextField src)
		{
			if(src==find_TextField )
			{
				if(src.getText().length()==0)
				{
					replace_TextField.setText("");
					replace_TextField.setEditable(false);
					replace.setEnabled(false);
					replaceAll.setEnabled(false);
					findNext.setEnabled(false);
				}
				else
				{
					findNext.setEnabled(true);
					replace_TextField.setEditable(true);
				}
				return;
			}
			{
				if(src.getText().length()==0)
				{
					replace.setEnabled(false);
					replaceAll.setEnabled(false);
				}
				else if(! replace.isEnabled())
				{
					replace.setEnabled(true);
					replaceAll.setEnabled(true);
				}
			}
		}
		void changeFind(TextField src)
		{
			if (src.getText().length()==0)
			{
				findNext.setEnabled(false);
			}
			else 
			{
				findNext.setEnabled(true);
			}
		}
		public void textValueChanged(TextEvent te)
		{
			TextField src=(TextField)te.getSource();
			if(getTitle().equalsIgnoreCase("replace"))
				changeReplace(src);
			else
				changeFind(src);
			startIndex=0;
			foundAt=-1;
			nLine=0;
			nLineStart=0;
		}
	}
	class FindReplaceClosure extends WindowAdapter
	{
		public void windowClosing(WindowEvent we)
		{
			ref.findReplaceDialog=null;
			dispose();
		}	
	}
}
