package pkg;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
		JButton caclulateButton = new JButton ("Calculate");
		setLayout (new BoxLayout (this.getContentPane (), BoxLayout.Y_AXIS));
		add (new JLabel ("In Number"));
		add (inTextField);
		add (new JLabel ("Out Numbers"));
		add (outTextField);
		add (caclulateButton);
		caclulateButton.addActionListener (new ActionListener () {

			@Override
			public void actionPerformed (ActionEvent e)
			{
				String outText = "";
				in = Integer.valueOf (inTextField.getText ());
				int sofar = 0;
				for (int j = out.length - 1; j >= 0; j--)
				{
						out[j] = Math.pow (2, j) <= in - sofar ? (int) Math.pow (2, j) : 0;
						sofar += out[j];
				}
				for (int i = 0; i < 11; i++)
				{
					outText += String.valueOf (out[i]) + "    ";
				}
				outTextField.setText (outText);
			}
		});
	}

	@Override
	public void start ()
	{
		super.start ();
	}
}
