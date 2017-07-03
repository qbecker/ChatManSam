package client.GUI;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JList;

public class ChatWindow extends JFrame {

	private JPanel contentPane;

	public ChatWindow() {
		guiInit();
	}
	private void guiInit() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton sendButton = new JButton("Send");
		sendButton.setBounds(344, 243, 100, 29);
		contentPane.add(sendButton);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(6, 226, 338, 46);
		contentPane.add(textArea);
		
		JTextPane chatPane = new JTextPane();
		chatPane.setBounds(87, 26, 271, 199);
		contentPane.add(chatPane);
		
		JList list = new JList();
		list.setBounds(6, 6, 74, 219);
		contentPane.add(list);
		
		JList list_1 = new JList();
		list_1.setBounds(365, 6, 79, 219);
		contentPane.add(list_1);
	}
}
