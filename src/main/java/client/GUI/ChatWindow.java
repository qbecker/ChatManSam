package client.GUI;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import client.Client;
import messaging.Message;
import messaging.Message.Type;
import utils.Logger.Log;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;


public class ChatWindow extends JFrame {

	private JPanel contentPane;
	private JTextArea ChatArea;
	private JTextArea textArea;

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
		
		
		DefaultListModel<String> dlm = new DefaultListModel<String>();
		
		
		JButton sendButton = new JButton("Send");
		
		sendButton.setBounds(344, 243, 100, 29);
		contentPane.add(sendButton);
		
		textArea = new JTextArea();
		textArea.setBounds(6, 226, 338, 46);
		contentPane.add(textArea);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(79, 24, 274, 201);
		contentPane.add(scrollPane);
		
		
		ChatArea = new JTextArea();
		scrollPane.setViewportView(ChatArea);
		
		
		JList ChatList = new JList(dlm);
		ChatList.setBounds(6, 6, 74, 219);
		contentPane.add(ChatList);
		
		JList ChatRoomList = new JList();
		ChatRoomList.setBounds(365, 6, 79, 219);
		contentPane.add(ChatRoomList);
		
		JButton addChatBtn = new JButton("+");
		addChatBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ChatArea.append("Hello" + "\n");
			}
		});
		addChatBtn.setBounds(79, 1, 25, 29);
		contentPane.add(addChatBtn);
		
		
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
		ChatArea.append(msg.getSender() +" : "+ msg.getMessage() + "\n");
	}
}
