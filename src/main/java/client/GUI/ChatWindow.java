package client.GUI;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultCaret;

import client.Client;
import messaging.Message;
import messaging.Message.Type;
import utils.Logger.Log;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JList;
import java.awt.event.ActionListener;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;


public class ChatWindow extends JFrame {

	private JPanel contentPane;
	private JTextArea textArea;
	private JTextArea chatArea;
	public JFrame frame;
	
	
	
	public ChatWindow() {
		guiInit();
	}
	private void guiInit() {
		System.out.println("In here creating shit");
		frame = new JFrame();
		frame.setBounds(100, 100, 517, 336);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    	Client.ImLoggingOff();
		    	System.exit(0);
		    }
		});
		JTextArea textArea = new JTextArea();
		textArea.setLineWrap(true);
		JList list = new JList();
		
		JButton sendButton = new JButton("Send");
		textArea.setWrapStyleWord(true);
		
		JScrollPane scrollPane = new JScrollPane();
		chatArea = new JTextArea();
		scrollPane.setViewportView(chatArea);
		
		
		DefaultCaret caret = (DefaultCaret) chatArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(textArea)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(sendButton, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(list, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 399, GroupLayout.PREFERRED_SIZE)))
					.addGap(0))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(34)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(list, GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 178, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(textArea, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
						.addComponent(sendButton, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE))
					.addGap(4))
		);
		
		frame.getContentPane().setLayout(groupLayout);
		
		sendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Log.debug("Sending...");
				Message msg = new Message(Message.Type.ChatMessage,
						textArea.getText().toString(),
						new String[] {"all"},
						Client.instance.userName);
				Log.debug(msg.messageToString());
				Client.instance.sendMessage(msg);
				textArea.setText("");
				
			}
		});
	}
	
	public void insertChatMessage(Message msg) {
		Log.debug(msg.getSender());
		chatArea.append("\n" + msg.getSender() +" : "+ msg.getMessage());
	}
}
