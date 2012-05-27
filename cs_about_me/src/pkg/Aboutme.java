package pkg;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Vector;

import javax.swing.BoxLayout;
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
	boolean									standalone	= false;

	/**
	 * @param args
	 */
	public static void main (String[] args)
	{
		JFrame f = new JFrame ("About Me");
		Aboutme a = new Aboutme (true);
		f.add (a);
		f.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		a.init ();
		a.start ();
		f.setVisible (true);
		f.pack ();
		f.setSize (900, 300);
	}

	public Aboutme (boolean bool)
	{
		standalone = bool;
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
			URL codeUrl = null;
			if (!standalone)
			{
				codeUrl = getCodeBase ();
			}
			else
			{
				codeUrl = Aboutme.class.getProtectionDomain ().getCodeSource ()
						.getLocation ();
			}
			if (codeUrl == null) { throw new FileNotFoundException (); }
			URL contentUrl = new URL("http", "mark005.hostoi.com", "/cs/applet/contents.conf");
			BufferedReader contentReader = new BufferedReader (new InputStreamReader (contentUrl.openStream ()));
			String temp;
			boolean skip = false;
			Vector<Object> element = new Vector<Object> ();
			while (contentReader.ready ())
			{
				temp = contentReader.readLine ();
				// System.out.println (temp);
				if (temp == "" || temp.length () < 3)
				{
					System.out.println ("Skipping " + temp);
					continue;
				}
				else if (temp.substring (0, 3).contains ("..."))
				{
					System.out.println ("... seen");
					skip = !skip;
				}
				else if (temp.substring (0, 1) == "#" || skip)
				{
					System.out.println ("Skipping " + temp);
					continue;
				}
				else if (temp.charAt (0) == '.')
				{
					System.out.println ("Found category " + temp);
					element = new Vector<Object> ();
					contents.put (temp.substring (1), element);
				}
				else if (temp.charAt (0) == '-')
				{
					System.out.println ("Found content " + temp);
					element.add (temp.substring (1));
				}
			}
			Vector<Vector<Object>> values = new Vector<Vector<Object>> (
					contents.values ());
			String[] keys = new String[contents.keySet ().toArray ().length];
			keys = contents.keySet ().toArray (keys);
			JComponent tempComp = null;
			JPanel tempPanel = null;
			for (int i = 0; i < keys.length; i++)
			{
				tempPanel = new JPanel ();
				tempPanel
						.setLayout (new BoxLayout (tempPanel, BoxLayout.Y_AXIS));
				for (int j = 0; j < values.get (i).size (); j++)
				{
					String rawContent = (String) values.get (i).get (j);
					System.out.println (rawContent);
					if (rawContent.contains ("text:"))
					{
						BufferedReader read = new BufferedReader (
								new FileReader (new File (
										rawContent.substring ("text:"
												.length ()))));
						String textContents = "";
						while (read.ready ())
						{
							textContents.concat (read.readLine () + "\n");
						}
						contents.get (keys[i]).set (j, textContents);
					}
					else if (rawContent.contains ("image:"))
					{
						String urlString = rawContent.substring ("image:".length ());
						URL imageURL = new URL (urlString);
						ImageIcon img = new ImageIcon (imageURL);
						contents.get (keys[i]).set (j, img);
						System.out.println ("added image " + img.getDescription ());
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
