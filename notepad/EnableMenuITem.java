class EnableMenuItem extends Thread
{
	NotePad ref;
	EnableMenuItem(NotePad ob)
	{
		ref=ob;
		start();
	}
	public void run()
	{
		while(true)
		{
			if(ref.text.getText().length()>0)
				ref.search.setEnabled(true);
			else
				ref.search.setEnabled(true);
			if(ref.text.getSelectedText().equals(""))
			{
				System.out.println("inside the thread");
				ref.cut.setEnabled(false);
				ref.copy.setEnabled(false);
				ref.paste.setEnabled(false);
			}
			else
			{
				ref.cut.setEnabled(true);
				ref.copy.setEnabled(true);
			}
			try
			{
				//Thread.sleep(1000);
			}
			catch (Exception e)
			{
			}
		}
	}
}