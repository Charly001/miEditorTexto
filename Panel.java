package editorTexto;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ForkJoinPool.ManagedBlocker;

import javax.swing.*;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Panel extends JFrame implements ActionListener {
	
	private JFrame frame;
	private JPanel panel;
	private JButton btn1;
	private JMenuBar menuBar;
	private JMenu menuFile;
	private JMenu menuEdit;
	private JMenuItem menuItemOpen;
	private JMenuItem menuItemSave;
	private JMenuItem menuItemSaveAs;
	private JMenuItem menuItemExit;
	private JMenuItem menuItemNewWindow;
	private JMenuItem menuItemCut;	
	private JMenuItem menuItemCopy;
	private JMenuItem menuItemPaste;
	private JMenuItem menuItemDelete;
	private JMenuItem menuItemSearch;
	private JTextArea userText;
	private JScrollPane scrollUserText;
	private File currentFile= null;
	public Panel() {
		
		activate();
		
	}
	
	public void activate() {
		
		createFrame();
		createMenus();
		addingMenuItems();
		frame.setVisible(true);
		
		
		
		
		
						
	}	

	
	private void createFrame() {
				//Window
				frame= new JFrame();
				frame.setSize(800, 600);
				frame.setTitle("Mi editor de texto");
				frame.setLocationRelativeTo(null);
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				frame.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Charly\\eclipse-workspace\\editorTexto\\src\\editorTexto\\bold.png"));			
				
				panel= new JPanel();
				panel.setLayout(new BorderLayout());
				
				
				//User text field
				userText= new JTextArea();
				scrollUserText= new JScrollPane(userText);
				panel.add(scrollUserText, BorderLayout.CENTER);
				//Window Bottom
				btn1= new JButton(" ");
				btn1.setEnabled(false);
								
				panel.add(btn1, BorderLayout.SOUTH);
				frame.add(panel);
					
	}
	
	private void createMenus() {
				
				//Menus
				menuBar= new JMenuBar();
				menuFile= new JMenu("Archivo");		
				menuEdit= new JMenu("Editar");
				
				
				//First Menu Items
				menuItemNewWindow= new JMenuItem("Nueva ventana");
				menuItemOpen= new JMenuItem("Abrir");
				menuItemSave= new JMenuItem("Guardar");
				menuItemSaveAs= new JMenuItem("Guardar como");
				menuItemExit= new JMenuItem("Salir");
				
				menuItemNewWindow.addActionListener(this);
				menuItemOpen.addActionListener(this);
				menuItemSave.addActionListener(this);
				menuItemSaveAs.addActionListener(this);
				menuItemExit.addActionListener(this);
				
				//Second Menu items
				menuItemCut= new JMenuItem("Cortar");
				menuItemCopy= new JMenuItem("Copiar");
				menuItemPaste= new JMenuItem("Pegar");
				menuItemDelete= new JMenuItem("Eliminar");
				menuItemSearch= new JMenuItem("Buscar");
				
				menuItemCut.addActionListener(this);
				menuItemCopy.addActionListener(this);
				menuItemPaste.addActionListener(this);
				menuItemDelete.addActionListener(this);
				menuItemSearch.addActionListener(this);
				
				menuFile.setMnemonic(KeyEvent.VK_A);
				menuEdit.setMnemonic(KeyEvent.VK_S);
				
				panel.add(menuBar, BorderLayout.NORTH);
	}
	private void addingMenuItems() {
		
		menuFile.add(menuItemNewWindow);
		menuFile.add(menuItemOpen);
		menuFile.add(menuItemSave);
		menuFile.add(menuItemSaveAs);
		menuFile.add(menuItemExit);
		menuEdit.add(menuItemCut);
		menuEdit.add(menuItemCopy);
		menuEdit.add(menuItemPaste);
		menuEdit.add(menuItemSearch);
		menuBar.add(menuFile);
		menuBar.add(menuEdit);

		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource().equals(menuItemExit)) {
			frame.dispose(); 													
		}
		
		if(e.getSource().equals(menuItemOpen)) {
			JFileChooser fileChooser= new JFileChooser();			
			int select= fileChooser.showOpenDialog(frame);
			
				if (select==JFileChooser.APPROVE_OPTION) {
					try {
						userText.selectAll();
						userText.replaceSelection("");
						BufferedReader selected= new BufferedReader(new FileReader(fileChooser.getSelectedFile()));
						
						String text; 
						do {							
							text= selected.readLine();
							if (text==null) break;
							userText.append(text + "\n");						
							
						}while (text!=null);
						selected.close();					
					
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						System.out.println("No se pudo cargar el archivo seleccionado");
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						System.out.println("No se pudo leer el archivo seleccionado");
					}
				}	
		}
		
		if(e.getSource().equals(menuItemSave)) {
			 	if(currentFile==null) {
			 		JFileChooser fileChooser= new JFileChooser();			
					int select= fileChooser.showSaveDialog(userText);
				
					if(select==JFileChooser.APPROVE_OPTION) {		
				
						currentFile= fileChooser.getSelectedFile();				
						try {
							if(currentFile.createNewFile()) {
								
								FileWriter fWriter= new FileWriter(currentFile);
								fWriter.write(userText.getText());
								fWriter.close();						
							};
							
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
					}
				
			 	}else {
			 		FileWriter fWriter;
					try {
						fWriter = new FileWriter(currentFile);
						fWriter.write(userText.getText());
						fWriter.close();	
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
			 	}
		}
		
		if(e.getSource().equals(menuItemSaveAs)) {
			JFileChooser fileChooser= new JFileChooser();
			
			int select= fileChooser.showSaveDialog(userText);
		
			if(select==JFileChooser.APPROVE_OPTION) {		
		
				File f= fileChooser.getSelectedFile();				
				try {
					if(f.createNewFile()) {
						
						FileWriter fWriter= new FileWriter(f);
						fWriter.write(userText.getText());
						fWriter.close();						
					};
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
			
		}
		
		if(e.getActionCommand().equals("Nueva ventana")) {
			Main.main(null);
		}
		
	}	
	
	
}
