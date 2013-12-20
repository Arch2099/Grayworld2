package nonBiological;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

import bioSimulation.World;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import java.awt.Font;

public class PhLab extends JFrame {
	private JTextField Volume;


	public PhLab() {
		JFrame frame = new JFrame("Lab");
		JPanel panel = (JPanel)frame.getContentPane();
		panel.setPreferredSize(new Dimension(300,300));
		frame.getContentPane().setLayout(null);

		JButton btnDecreaseVolume = new JButton("Decrease Volume");
		btnDecreaseVolume.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				World.setWbound(-6);
				World.setHbound(-4);
				System.out.println("volume down");
				updateVolume();
			}
		});
		
		btnDecreaseVolume.setBounds(10, 21, 150, 23);
		frame.getContentPane().add(btnDecreaseVolume);
		
		JButton btnIncreaseVolume = new JButton("Increase Volume");
		btnIncreaseVolume.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				World.setWbound(6);
				World.setHbound(4);
				System.out.println("volume up");
				updateVolume();
			}
		});
		btnIncreaseVolume.setBounds(10, 55, 150, 23);
		frame.getContentPane().add(btnIncreaseVolume);
		
		JButton btnDecreaseTemperature = new JButton("Decrease Temperature");
		btnDecreaseTemperature.setBounds(10, 120, 150, 23);
		frame.getContentPane().add(btnDecreaseTemperature);
		
		JButton btnIncreaseTemperature = new JButton("Increase Temperature");
		btnIncreaseTemperature.setBounds(10, 160, 150, 23);
		frame.getContentPane().add(btnIncreaseTemperature);
		
		Volume = new JTextField();
		Volume.setFont(new Font("Tahoma", Font.PLAIN, 12));
		Volume.setText("Volume: " + (World.getHbound()*World.getWbound()));
		Volume.setEditable(false);
		Volume.setBounds(170, 21, 104, 56);
		frame.getContentPane().add(Volume);
		Volume.setColumns(10);
		frame.setBounds(800,0,300,300);
		frame.setVisible(true);
		updateVolume();

	}
	
	private void updateVolume()
	{
		Volume.setText("Volume: " + World.getWbound()+ "x" + World.getHbound());
	}
}
