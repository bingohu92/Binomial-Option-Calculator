package pricer;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class americanPricer extends JFrame{
	public static void main(String[] args) {
		new americanPricer();
	}
	
	americanPricer(){
		super("Bingo's Binomial Pricing GUI");
		setSize(720,400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		JPanel group = new JPanel(new GridLayout(1,2));
		JPanel inputPanel = new JPanel(new GridLayout(0,1));
		JPanel outputPanel = new JPanel(new GridLayout(0,1));
		
		/* Input components */
		JPanel p0 = new JPanel(new GridLayout(1,0));
		JPanel p1 = new JPanel(new GridLayout(1,2));
		JPanel p2 = new JPanel(new GridLayout(1,2));
		JPanel p3 = new JPanel(new GridLayout(1,2));
		JPanel p4 = new JPanel(new GridLayout(1,2));
		JPanel p5 = new JPanel(new GridLayout(1,2));
		JPanel p6 = new JPanel(new GridLayout(1,2));
		
		final JRadioButton rb1 = new JRadioButton("UD = 1");
		rb1.setSelected(true);
		final JRadioButton rb2 = new JRadioButton("UD = R");
		final JRadioButton rbCall = new JRadioButton("Call");
		rbCall.setSelected(true);
		final JRadioButton rbPut = new JRadioButton("Put");
		ButtonGroup bGroup1 = new ButtonGroup();
		bGroup1.add(rb1);
		bGroup1.add(rb2);
		ButtonGroup bGroup2 = new ButtonGroup();
		bGroup2.add(rbCall);
		bGroup2.add(rbPut);
		p0.add(rb1);
		p0.add(rb2);
		p0.add(rbCall);
		p0.add(rbPut);
		
		final JTextField underlying = new JTextField();
		final JTextField strike = new JTextField();
		final JTextField maturity = new JTextField();
		final JTextField step = new JTextField();
		final JTextField volatility = new JTextField();
		final JTextField interestRate = new JTextField();
		
		JPanel bottons = new JPanel(new GridLayout(1,3));
		JButton myDefault = new JButton("Default value");
		JButton reset = new JButton("Reset");
		
		JButton calculate = new JButton("Calculate");
		bottons.add(myDefault);
		bottons.add(reset);
		bottons.add(calculate);

		myDefault.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				underlying.setText("100");
				strike.setText("110");
				maturity.setText("1");
				step.setText("26");
				volatility.setText("0.2");
				interestRate.setText("0.05");
			}
		});
		
		reset.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				underlying.setText("");
				strike.setText("");
				maturity.setText("");
				step.setText("");
				volatility.setText("");
				interestRate.setText("");
			}
		});
		
		p1.add(new JLabel("Underlying price S0:"));
		p1.add(underlying);
		p2.add(new JLabel("Strike price K:"));
		p2.add(strike);
		p3.add(new JLabel("Maturity T:"));
		p3.add(maturity);
		p4.add(new JLabel("Number of steps N:"));
		p4.add(step);
		p5.add(new JLabel("Volatility σ:"));
		p5.add(volatility);
		p6.add(new JLabel("Interest rate r:"));
		p6.add(interestRate);
		
		inputPanel.add(p0);
		inputPanel.add(p1);
		inputPanel.add(p2);
		inputPanel.add(p3);
		inputPanel.add(p4);
		inputPanel.add(p5);
		inputPanel.add(p6);
		inputPanel.add(bottons);
		group.add(inputPanel);
		
		/* Output components */
		
		JPanel out0 = new JPanel(new GridLayout(1,0));
		JPanel out1 = new JPanel(new GridLayout(1,0));
		JPanel out2 = new JPanel(new GridLayout(1,0));
		JPanel out3 = new JPanel(new GridLayout(1,0));
		JPanel out4 = new JPanel(new GridLayout(1,0));
		JPanel out5 = new JPanel(new GridLayout(1,0));
		JPanel out6 = new JPanel(new GridLayout(1,0));
		
		
		final JTextField up = new JTextField();
		final JTextField down = new JTextField();
		final JTextField price = new JTextField();
		final JTextField delta = new JTextField();
		final JTextField gamma = new JTextField();
		final JTextField vega = new JTextField();
		final JTextField theta = new JTextField();
		
		out0.add(new JLabel("U:"));
		out0.add(up);
		out1.add(new JLabel("D:"));
		out1.add(down);
		out2.add(new JLabel("Option price:"));
		out2.add(price);
		out3.add(new JLabel("Delta Δ:"));
		out3.add(delta);
		out4.add(new JLabel("Gamma Γ:"));
		out4.add(gamma);
		out5.add(new JLabel("Vega ϒ:"));
		out5.add(vega);
		out6.add(new JLabel("Theta Θ:"));
		out6.add(theta);
		
		outputPanel.add(new JLabel("Outputs display:"));
		outputPanel.add(out0);
		outputPanel.add(out1);
		outputPanel.add(out2);
		outputPanel.add(out3);
		outputPanel.add(out4);
		outputPanel.add(out5);
		outputPanel.add(out6);
		
		outputPanel.setBorder(BorderFactory.createRaisedBevelBorder());
		
		
		calculate.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!underlying.getText().equals("") && !strike.getText().equals("") && !maturity.getText().equals("")
						&& !step.getText().equals("") && !volatility.getText().equals("") && !interestRate.getText().equals("")){
					if (rb1.isSelected() && rbCall.isSelected()){
						callOption c = new callOption(Double.parseDouble(underlying.getText()), Double.parseDouble(volatility.getText()),
								Double.parseDouble(strike.getText()), Double.parseDouble(maturity.getText()),
								Integer.parseInt(step.getText()), Double.parseDouble(interestRate.getText()), 1);
						up.setText(String.valueOf(c.getUp()));
						down.setText(String.valueOf(c.getDown()));
						price.setText(String.valueOf(c.getOptionPrice()));
						delta.setText(String.valueOf(c.getDelta()));
						gamma.setText(String.valueOf(c.GetGammaCal()));
						vega.setText(String.valueOf(c.GetVega()));
						theta.setText(String.valueOf(c.GetTheta()));
					}
					else if (rb2.isSelected() && rbCall.isSelected()){
						callOption d = new callOption(Double.parseDouble(underlying.getText()), Double.parseDouble(volatility.getText()),
								Double.parseDouble(strike.getText()), Double.parseDouble(maturity.getText()),
								Integer.parseInt(step.getText()), Double.parseDouble(interestRate.getText()), 2);
						up.setText(String.valueOf(d.getUp()));
						down.setText(String.valueOf(d.getDown()));
						price.setText(String.valueOf(d.getOptionPrice()));
						delta.setText(String.valueOf(d.getDelta()));
						gamma.setText(String.valueOf(d.GetGammaCal()));
						vega.setText(String.valueOf(d.GetVega()));
						theta.setText(String.valueOf(d.GetTheta()));
					}
					else if (rb1.isSelected() && rbPut.isSelected()){
						putOption f = new putOption(Double.parseDouble(underlying.getText()), Double.parseDouble(volatility.getText()),
								Double.parseDouble(strike.getText()), Double.parseDouble(maturity.getText()),
								Integer.parseInt(step.getText()), Double.parseDouble(interestRate.getText()), 1);
						up.setText(String.valueOf(f.getUp()));
						down.setText(String.valueOf(f.getDown()));
						price.setText(String.valueOf(f.getOptionPrice()));
						delta.setText(String.valueOf(f.getDelta()));
						gamma.setText(String.valueOf(f.GetGammaCal()));
						vega.setText(String.valueOf(f.GetVega()));
						theta.setText(String.valueOf(f.GetTheta()));
					}
					else if (rb2.isSelected() && rbPut.isSelected()){
						putOption h = new putOption(Double.parseDouble(underlying.getText()), Double.parseDouble(volatility.getText()),
								Double.parseDouble(strike.getText()), Double.parseDouble(maturity.getText()),
								Integer.parseInt(step.getText()), Double.parseDouble(interestRate.getText()), 2);
						up.setText(String.valueOf(h.getUp()));
						down.setText(String.valueOf(h.getDown()));
						price.setText(String.valueOf(h.getOptionPrice()));
						delta.setText(String.valueOf(h.getDelta()));
						gamma.setText(String.valueOf(h.GetGammaCal()));
						vega.setText(String.valueOf(h.GetVega()));
						theta.setText(String.valueOf(h.GetTheta()));
					}
				}
				
			}
		});
		
		
		group.add(outputPanel);
		
		
		add(group);
		setVisible(true);
		
	}
}
