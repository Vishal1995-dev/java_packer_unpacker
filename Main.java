import java.io.*;
import java.util.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;

class Main
{
	public static void main(String args[])
	{	
		Scanner sobj = new Scanner(System.in);
		
		while(true)
		{
			System.out.println("Enter choice");
			System.out.println("1.Packing");
			System.out.println("2.Unpacking");
			System.out.println("3.Exit");
			int choice=0;
			choice=sobj.nextInt();
			
			String dir,filename;
			switch(choice)
			{
				case 1:
					System.out.println("Enter directory name");
					dir=sobj.next();
					
					System.out.println("Enter packing file name");
					filename=sobj.next();
					
					Packer pobj=new Packer(dir,filename);
					break;
				
				case 2:
					System.out.println("Enter packed file name");
					String Filename =sobj.next();
					Unpacker obj = new Unpacker(Filename);
					break;
					
				case 3:
					System.out.println("Thank you");
					System.exit(0);
				
				default:
					System.out.println("Wrong choice");
					break;
			}
		}
	}
}


class Packer
{
	public FileOutputStream outstream = null;
	public Packer(String foldername,String filename)
	{
		try
		{
			//Create new file for packing
			File outfile = new File(filename);
			outstream = new FileOutputStream(filename);
			//Set current working directory
			//System.setProperty("user.dir",foldername);
			TravelDir(foldername);
		}
		catch(Exception obj)
		{
			System.out.println(obj);
		}		
	}
	
	public void TravelDir(String path)
	{
		File directoryPath = new File(path);
		
		//Get all file names form directory
		File arr[] = directoryPath.listFiles();
		
		for(File filename:arr)
		{
			//System.out.println(filename.getName());
			//System.out.println(filename.getAbsolutePath());
			if(filename.getName().endsWith(".txt"))
			{
				PackFile(filename.getAbsolutePath());
			}
		}	
	}
	
	public void PackFile(String FilePath)
	{
		byte Header[] = new byte[100];
		byte Buffer[] = new byte[1024];
		int length = 0;

		FileInputStream istream = null;
		
		File fobj=null;
		fobj=new File(FilePath);
		
		String temp = FilePath+" "+fobj.length();
		
		for(int i=temp.length();i<100;i++)
		{
			temp=temp+" ";
		}
		
		Header = temp.getBytes();
		try
		{
			istream = new FileInputStream(FilePath);

			outstream.write(Header,0,Header.length);
			while((length = istream.read(Buffer)) > 0)
			{
				outstream.write(Buffer,0,length);
			}

			istream.close();
 		}
 		catch(Exception obj)
 		{
 			System.out.println(obj);
 		}
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
