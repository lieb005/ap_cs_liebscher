package pkg;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BoxLayout;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Two extends JApplet
{
	int		in;
	int[]	out	= new int[12];

	/**
	 * @param args
	 */
	public static void main (String[] args)
	{
		JFrame f = new JFrame ("Two");
		Two t = new Two ();
		f.add (t);
		t.init ();
		t.start ();
		f.setVisible (true);
		f.setSize (600, 200);
		f.setVisible (true);
		f.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
	}

	@Override
	public void init ()
	{
		super.init ();
		final JTextField inTextField = new JTextField ();
		final JTextField outTextField = new JTextField ();
		final JButton calculateButton = new JButton ("Calculate");
		setLayout (new BoxLayout (this.getContentPane (), BoxLayout.Y_AXIS));
		add (new JLabel ("In Number"));
		add (inTextField);
		add (new JLabel ("Out Numbers"));
		add (outTextField);
		add (calculateButton);
		calculateButton.addActionListener (new ActionListener () {

			@Override
			public void actionPerformed (ActionEvent e)
			{
				String outText = "";
				in = Integer.valueOf (inTextField.getText ());
				int sofar = 0;
				for (int j = out.length - 1; j >= 0; j--)
				{
					out[j] = Math.pow (2, j) <= in - sofar ? (int) Math.pow (2,
							j) : 0;
					sofar += out[j];
				}
				for (int i = 0; i < 11; i++)
				{
					outText += String.valueOf (out[i]) + "    ";
				}
				outTextField.setText (outText);
			}
		});
		inTextField.addKeyListener (new KeyAdapter () {
			@Override
			public void keyReleased (KeyEvent e)
			{
				super.keyReleased (e);
				if (e.getKeyChar () == '\n')
				{
					e.consume ();
					calculateButton.getActionListeners ()[0]
							.actionPerformed (new ActionEvent (inTextField,
									ActionEvent.ACTION_LAST, null));
				}
			}
		});
	}

	@Override
	public void start ()
	{
		super.start ();
	}
}
