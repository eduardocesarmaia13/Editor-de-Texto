import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Editor extends JFrame implements ActionListener{

		JTextArea areaDoTexto;
		JScrollPane rolarPainel;
		JLabel fonteEtiqueta;
		JSpinner tamanhoDaFonte;
		JButton corDaFonte;
		JComboBox caixaDaFonte;
		
		JMenuBar barraDeMenu;
		JMenu arquivoMenu;
		JMenuItem abrirItem;
		JMenuItem salvarItem;
		JMenuItem sairItem;
	
	Editor(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Editor de Texto by Eduardo Maia");
		this.setSize(500, 500);
		this.setLayout(new FlowLayout());
		this.setLocationRelativeTo(null); //Aparecer no meio da tela.
		
		areaDoTexto = new JTextArea();
		areaDoTexto.setLineWrap(true);
		areaDoTexto.setWrapStyleWord(true); //O texto continua na linha de baixo
		areaDoTexto.setFont(new Font("Arial",Font.PLAIN,20));
		
		rolarPainel = new JScrollPane(areaDoTexto);
		rolarPainel.setPreferredSize(new Dimension(450, 450));
		rolarPainel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		fonteEtiqueta = new JLabel("Tamanho: ");
		
		tamanhoDaFonte = new JSpinner();
		tamanhoDaFonte.setPreferredSize(new Dimension(50,25));
		tamanhoDaFonte.setValue(20);
		tamanhoDaFonte.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				
				areaDoTexto.setFont(new Font(areaDoTexto.getFont().getFamily(),Font.PLAIN,(int) tamanhoDaFonte.getValue()));
				
			}
			
		});
		
		corDaFonte = new JButton("Cor");
		corDaFonte.addActionListener(this);
		
		String[] fonte = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		
		caixaDaFonte = new JComboBox(fonte);
		caixaDaFonte.addActionListener(this);
		caixaDaFonte.setSelectedItem("Arial");	
		
		// -------------barra de menu-----------
		
		barraDeMenu = new JMenuBar();
		arquivoMenu = new JMenu("Arquivo");
		abrirItem = new JMenuItem("Abrir");
		salvarItem = new JMenuItem("Salvar");
		sairItem = new JMenuItem("Sair");
		
		abrirItem.addActionListener(this);
		salvarItem.addActionListener(this);
		sairItem.addActionListener(this);
		
		arquivoMenu.add(abrirItem);
		arquivoMenu.add(salvarItem);
		arquivoMenu.add(sairItem);
		barraDeMenu.add(arquivoMenu);
		
		// -------------/barra de menu-----------
		
		this.setJMenuBar(barraDeMenu);
		this.add(fonteEtiqueta);
		this.add(tamanhoDaFonte);
		this.add(corDaFonte);
		this.add(caixaDaFonte);
		this.add(rolarPainel);
		this.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource()==corDaFonte) {
			JColorChooser escolherCor = new JColorChooser();
			
			Color cor = escolherCor.showDialog(null, "Escolha uma cor", Color.black);
			
			areaDoTexto.setForeground(cor);	
		}
		
		if(e.getSource()==caixaDaFonte) {
			areaDoTexto.setFont(new Font((String)caixaDaFonte.getSelectedItem(),Font.PLAIN,areaDoTexto.getFont().getSize()));
		}
		
		if(e.getSource()==abrirItem) {
			JFileChooser escolherArquivo = new JFileChooser();
			escolherArquivo.setCurrentDirectory(new File("."));
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Arquivos de Texto", "txt");
			escolherArquivo.setFileFilter(filter);
			
			int response = escolherArquivo.showOpenDialog(null);
			
			if(response == JFileChooser.APPROVE_OPTION) {
				File file = new File(escolherArquivo.getSelectedFile().getAbsolutePath());
				Scanner fileIn = null;
				
				try {
					fileIn = new Scanner(file);
					if(file.isFile()) {
						while(fileIn.hasNextLine()) {
							String line = fileIn.nextLine()+"\n";
							areaDoTexto.append(line);
						}
					}
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				finally {
					fileIn.close();
				}
			}
			
		}
		if(e.getSource()==salvarItem) {
			JFileChooser escolherArquivo = new JFileChooser();
			escolherArquivo.setCurrentDirectory(new File("."));
			
			int response = escolherArquivo.showSaveDialog(null);
			
			if(response == JFileChooser.APPROVE_OPTION) {
				File file;
				PrintWriter fileOut = null;
				
				file = new File(escolherArquivo.getSelectedFile().getAbsolutePath());
					
				try {
					fileOut = new PrintWriter(file);
					fileOut.println(areaDoTexto.getText());
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				finally {
					fileOut.close();
				}
			}
		}
		if(e.getSource()==sairItem) {
			System.exit(0);
		}
	}
}
