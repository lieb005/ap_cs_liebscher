package pkg;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Vector;

import javax.swing.JApplet;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class Aboutme extends JApplet
{
	JTabbedPane	tabbedPane	= new JTabbedPane ();
	LinkedHashMap<String, Vector<Object>> contents = new LinkedHashMap<String, Vector<Object>> ();
	/**
	 * @param args
	 */
	public static void main (String[] args)
	{
		JFrame f = new JFrame ("About Me");
		Aboutme a = new Aboutme ();
		f.add (a);
		a.init ();
		a.start ();
		f.setVisible (true);
		f.pack ();
	}

	@Override
	public void init ()
	{
		super.init ();
		add (tabbedPane);
	}

	@Override
	public void start ()
	{
		super.start ();
		URL codeUrl = getCodeBase ();
		try
		{
			File contentFile = new File (
					new URL (codeUrl.getProtocol (), codeUrl.getHost (),
							codeUrl.getFile () + "pkg/content.txt").toURI ());
			BufferedReader contentReader = new BufferedReader (new FileReader (
					contentFile));
			String temp;
			boolean skip = false;
			Vector<Object> element = new Vector<Object> ();
			while (contentReader.ready ())
			{
				temp = contentReader.readLine ();
				if (temp == "" || temp.length () < 3 || skip)
					continue;
				else if (temp.substring (0, 3) == "...")
					skip = !skip;
				else if (temp.substring (0, 1) == "#")
					continue;
				else if (temp.substring (0, 1) == ".")
				{
					element = new Vector<Object> ();
					contents.put (temp.substring (1), element);
				}
				else if (temp.substring (0, 1) == "-")
				{
					element.add (temp.substring (1));
				}
			}
			Vector<Object>[] values = (Vector<Object>[]) contents.values ().toArray ();
			String[] keys = (String[]) contents.keySet ().toArray ();
			JComponent tempComp = null;
			JPanel tempPanel = null;
			for (int i = 0;i < keys.length;i++)
			{
				tempPanel = new JPanel ();
				for (int j = 0;j < values[i].size ();j++)
				{
					values[i].get (j);
				}
			}
		} catch (MalformedURLException e)
		{
			System.out
					.println ("There has been an internal error.  Please email me at mark005@pacbell.net with a full description of the problem and your OS.");
			return;
		} catch (URISyntaxException e)
		{
			System.out
					.println ("There has been an internal error.  Please email me at mark005@pacbell.net with a full description of the problem and your OS.");
			return;
		} catch (FileNotFoundException e)
		{
			System.out
					.println ("The File Cannot be Found.  Please email me at mark005@pacbell.net with a full description of the problem and your OS.");
			return;
		} catch (IOException e)
		{
			System.out
					.println ("There has been an internal error.  Please email me at mark005@pacbell.net with a full description of the problem and your OS.");
			return;
		}
	}
}
