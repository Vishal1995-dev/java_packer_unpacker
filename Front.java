import java.lang.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;

class Front
{
	public static void main(String arg[])
	{
		Window obj = new Window();	
	}
}

class Window //implements ActionListener
{
	public Window()
	{
		JFrame f = new JFrame("Login");
		
		JButton bobj = new JButton("Submit");
		bobj.setBounds(100,100,140,40);
		
		JLabel lobj = new JLabel("Enter file name");
		lobj.setBounds(10,10,100,100);
		
		JTextField tf = new JTextField();
		tf.setBounds(110,50,130,30);
		
		f.add(bobj);
		f.add(tf);
		f.add(lobj);
		
		f.setSize(300,300);
		f.setLayout(null);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		bobj.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent eobj)
			{
				System.out.println("Data entered : "+tf.getText());
				Unpacker pobj = new Unpacker(tf.getText());
				//f.setVisible(false);
			}
		});
	}
}


class Unpacker
{
	public FileOutputStream outstream=null;
	public Unpacker(String src)
	{
		unpackfile(src);
	}	
	
	public void unpackfile(String FilePath)
	{
		try
		{
			FileInputStream instream= new FileInputStream(FilePath);
			byte[] Header = new byte[100];
			int length=0;
			while((length=instream.read(Header,0,100))>0)
			{
				String str=new String(Header);
				String ext = str.substring(str.lastIndexOf("/"));
				ext=ext.substring(1);
				String words[]=ext.split("\\s");
				String name = words[0];
				int size=Integer.parseInt(words[1]);
				byte arr[]=new byte[size];
				instream.read(arr,0,size);
				
				FileOutputStream fout = new FileOutputStream(name);
				fout.write(arr,0,size);
			}
		}
		catch(Exception obj)
		{
			System.out.println(obj);
		}
	}
}
