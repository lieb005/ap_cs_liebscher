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

import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

public class Aboutme extends JApplet
{
	JTabbedPane								tabbedPane	= new JTabbedPane ();
	LinkedHashMap<String, Vector<Object>>	contents	= new LinkedHashMap<String, Vector<Object>> ();

	/**
	 * @param args
	 */
	public static void main (String[] args)
	{
		JFrame f = new JFrame ("About Me");
		Aboutme a = new Aboutme ();
		f.add (a);
		f.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
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
		try
		{
			URL codeUrl = getCodeBase ();
			File contentFile = new File (
					new URL (codeUrl.getProtocol (), codeUrl.getHost (),
							codeUrl.getFile () + "pkg/contents.txt").toURI ());
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
			Vector<Vector<Object>> values = new Vector<Vector<Object>> (
					contents.values ());
			String[] keys = new String[contents.keySet ().toArray().length];
			keys = contents.keySet ().toArray (keys);
			JComponent tempComp = null;
			JPanel tempPanel = null;
			for (int i = 0; i < keys.length; i++)
			{
				tempPanel = new JPanel ();
				for (int j = 0; j < values.get (i).size (); j++)
				{
					String rawContent = (String) values.get (i).get (j);
					if (rawContent.substring (1, 5).contains ("text"))
					{
						BufferedReader read = new BufferedReader (
								new FileReader (new File (
										rawContent.substring ("-text:"
												.length () - 1))));
						String textContents = "";
						while (read.ready ())
						{
							textContents.concat (read.readLine () + "\n");
						}
						contents.get (keys[i]).set (j, textContents);
					}
					else if (rawContent.substring (1, 5).contains ("image"))
					{
						ImageIcon img = new ImageIcon (
								rawContent.substring ("-image:".length () - 1));
						contents.get (keys[i]).set (j, img);
					}
				}
				Vector<Object> vect = contents.get (keys[i]);
				for (int k = 0; k < vect.size (); k++)
				{
					if (! (vect.get (k) instanceof String))
					{
						tempComp = new JLabel ((ImageIcon) vect.get (k));
						tempPanel.add (tempComp);
					}
					else if ( ((String) vect.get (k)).contains ("\n"))
					{
						tempComp = new JTextArea ((String) vect.get (k));
						((JTextArea) tempComp).setLineWrap (true);
						tempPanel.add (tempComp);
					}
					else
					{
						tempComp = new JLabel ((String) vect.get (k));
						tempPanel.add (tempComp);
					}
				}
				tabbedPane.addTab (keys[i], tempPanel);
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
		invalidate ();
		validate ();
	}
}
