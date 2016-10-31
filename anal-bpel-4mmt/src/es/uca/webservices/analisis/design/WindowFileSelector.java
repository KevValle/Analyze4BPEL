package es.uca.webservices.analisis.design;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import es.uca.webservices.analisis.*;
import es.uca.webservices.analisis.match.ReportMatcher;

public class WindowFileSelector extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private static final String userHome = System.getProperty("user.home");
	
	private JPanel principalPanel;
	private JPanel topPanel;
	private JPanel middlePanel;
	private JPanel southPanel;
	
	private JButton buttonMain;
	private JButton buttonHelp;
	private JButton buttonLoan;
	private JButton buttonSquare;
	private JButton buttonMetaSearch;
	
	private JCheckBox checkFunctionality;
	
	public WindowFileSelector() {
		
		//Se le da forma a la ventana y al primer boton
		super("Analyze4BPEL");
		
		//Funcionalidad de botones
		
		buttonMain = new JButton("Choose .bpel file");
		buttonMain.setForeground(Color.black);
		buttonMain.setBackground(Color.white);
		
		//Se le da funcionalidad al boton
		buttonMain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				JFileChooser selection = new JFileChooser(), selectionBPTS = new JFileChooser();
				selection.setDialogTitle("Choose .bpel file");
				int option = selection.showOpenDialog(buttonMain);
				String filePath = selection.getSelectedFile().getPath();

				if(filePath.substring(filePath.length() - 4, filePath.length()).equals("bpel")) {
					
					if ( option == JFileChooser.APPROVE_OPTION ) {
						//Se abre una segunda ventana que selecciona el BPTS
						selectionBPTS.setCurrentDirectory(selection.getCurrentDirectory());
						selectionBPTS.setDialogTitle("Choose .bpts file");
						int optionBPTS = selectionBPTS.showOpenDialog(buttonMain);
						String bptsPath = selectionBPTS.getSelectedFile().getPath();
						
						if(bptsPath.substring(bptsPath.length() - 4, bptsPath.length()).equals("bpts")) {

							if( optionBPTS == JFileChooser.APPROVE_OPTION ) {
								try {
									//String bptsPath = filePath.substring(0, filePath.length() - 4) + "bpts";
		
									
									//Se envia a la clase principal la actividad seleccionada
									BPELActivityAnalyzer bpelComposition = new BPELActivityAnalyzer(filePath);
									BPTSAnalyzer testCaseSuite = new BPTSAnalyzer(bptsPath);
									ReportMatcher finalResult;
									
									//Muestra mensaje de alerta
									bpelComposition.readVariables();
									JOptionPane.showMessageDialog(null, bpelComposition.readActivities());
									JOptionPane.showMessageDialog(null, testCaseSuite.readNodes());
									
									if(checkFunctionality.isSelected()) {
										finalResult = new ReportMatcher(bpelComposition.getJSONOutput().getPath(), testCaseSuite.getJSONoutput());
										
										JOptionPane.showMessageDialog(null, finalResult.getFinalResult());
										if(bpelComposition.getOutput() != null) Desktop.getDesktop().open(finalResult.getOutput());
									} else {
										if(bpelComposition.getOutput() != null) Desktop.getDesktop().open(bpelComposition.getOutput());
									}
									
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
							
						} else {
							JOptionPane.showMessageDialog(null, "ERROR: Must select a BPTS file.");
						}
					}
				} else {
					JOptionPane.showMessageDialog(null, "ERROR: Must select a BPEL file.");
				}
			}
		});
		
		
		
		//Se le da forma al boton del prestamo
		buttonLoan = new JButton("LoanApprovalRPC");
		buttonLoan.setForeground(Color.black);
		buttonLoan.setBackground(Color.white);
		
		//Se le da funcionalidad al boton del prestamo
		buttonLoan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {
					//Se envia a la clase principal la actividad seleccionada
					BPELActivityAnalyzer bpelComposition = new BPELActivityAnalyzer("./test/LoanApprovalRPC/loanApprovalProcess.bpel");
					BPTSAnalyzer testCaseSuite = new BPTSAnalyzer("./test/LoanApprovalRPC/loanApprovalProcess.bpts");
					ReportMatcher finalResult = new ReportMatcher(userHome + "/metamorphic/json/loanApprovalProcess.json",
							userHome + "/metamorphic/json/loanApprovalProcess_testCases.json");

					
					//Muestra mensaje de alerta
					bpelComposition.readVariables();
					JOptionPane.showMessageDialog(null, bpelComposition.readActivities());
					JOptionPane.showMessageDialog(null, testCaseSuite.readNodes());
					
					if(checkFunctionality.isSelected()) {
						JOptionPane.showMessageDialog(null, finalResult.getFinalResult());
						if(bpelComposition.getOutput() != null) Desktop.getDesktop().open(finalResult.getOutput());
					} else {
						if(bpelComposition.getOutput() != null) Desktop.getDesktop().open(bpelComposition.getOutput());
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		});

		
		
		//Se le da forma al boton de la suma de cuadrados
		buttonSquare = new JButton("SquaresSum_3");
		buttonSquare.setForeground(Color.black);
		buttonSquare.setBackground(Color.white);
		
		//Se le da funcionalidad al boton del prestamo
		buttonSquare.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {
					//Se envia a la clase principal la actividad seleccionada
					BPELActivityAnalyzer bpelComposition = new BPELActivityAnalyzer("./test/squaresSum_3/squaresSum_3.bpel");
					BPTSAnalyzer testCaseSuite = new BPTSAnalyzer("./test/squaresSum_3/squaresSum.bpts");
					ReportMatcher finalResult;

					
					//Muestra mensaje de alerta
					bpelComposition.readVariables();
					JOptionPane.showMessageDialog(null, bpelComposition.readActivities());
					JOptionPane.showMessageDialog(null, testCaseSuite.readNodes());
					
					if(checkFunctionality.isSelected()) {
						finalResult = new ReportMatcher(userHome + "/metamorphic/json/squaresSum.json",
								userHome + "/metamorphic/json/squaresSum_testCases.json");
						
						JOptionPane.showMessageDialog(null, finalResult.getFinalResult());
						if(bpelComposition.getOutput() != null) Desktop.getDesktop().open(finalResult.getOutput());
					} else {
						if(bpelComposition.getOutput() != null) Desktop.getDesktop().open(bpelComposition.getOutput());
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}		
		});
		

		//Se le da forma al boton de la suma de cuadrados
		buttonMetaSearch = new JButton("MetaSearch");
		buttonMetaSearch.setForeground(Color.black);
		buttonMetaSearch.setBackground(Color.white);
		
		//Se le da funcionalidad al boton del prestamo
		buttonMetaSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {
					//Se envia a la clase principal la actividad seleccionada
					
					BPELActivityAnalyzer bpelComposition = new BPELActivityAnalyzer("./test/MetaSearch/MetaSearchBPEL2-ForEach.bpel");
					BPTSAnalyzer testCaseSuite = new BPTSAnalyzer("./test/MetaSearch/MetaSearch-original.bpts");
					ReportMatcher finalResult;

					
					//Muestra mensaje de alerta
					bpelComposition.readVariables();
					JOptionPane.showMessageDialog(null, bpelComposition.readActivities());
					JOptionPane.showMessageDialog(null, testCaseSuite.readNodes());
					
					if(checkFunctionality.isSelected()) {
						finalResult = new ReportMatcher(userHome + "/metamorphic/json/MetaSearch.json",
								userHome + "/metamorphic/json/MetaSearch-original_testCases.json");
						JOptionPane.showMessageDialog(null, finalResult.getFinalResult());
						if(bpelComposition.getOutput() != null) Desktop.getDesktop().open(finalResult.getOutput());
					} else {
						if(bpelComposition.getOutput() != null) Desktop.getDesktop().open(bpelComposition.getOutput());
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		buttonHelp = new JButton("Help");
		buttonHelp.setForeground(Color.black);
		buttonHelp.setBackground(Color.white);
		
		buttonHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {
					//Utilizamos html para mostrar un texto en condiciones
					String primera = "<html><body width='";
					/*String segunda = "'><h1>Instrucciones de Uso</h1>" +
									 "<p>Para analizar la composición pulse el botón " +
									 "'Choose .bpel file' y seleccione el fichero .bpel " +
									 "deseado. Una vez seleccionado, utilice la ventana similar " +
									 "para seleccionar el fichero .bpts. <br>" +
									 "Los informes se habrán generado en '/home/usuario/metamorphic'." +
									 "<p><br><br>Kevin Jesús Valle Gómez - 2016</p>";*/
					
					String segunda = "'><h1>Help</h1>" +
							 "<p>To start the analysis click on " +
							 "'Choose .bpel file' and select a .bpel file. " +
							 "After that selection, please select a .bpts file " +
							 "in the new window to indicate the test cases file. <br>" +
							 "All reports will be stored in '/home/user/metamorphic'." +
							 "<p><br><br>Kevin Jesús Valle Gómez - 2016</p>";


		                JPanel p = new JPanel( new BorderLayout() );

		                int width = 350;
		                String s = primera + width + segunda;

		                JOptionPane.showMessageDialog(null, s);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		//Ventana principal a la que añadiremos los paneles
		principalPanel = new JPanel();
		principalPanel.setLayout( new BorderLayout() );
		
		middlePanel = new JPanel();
		topPanel =  new JPanel();
		southPanel = new JPanel();

		
		/*topPanel.setBackground(Color.black);
		middlePanel.setBackground(Color.green);
		southPanel.setBackground(Color.blue);*/

		topPanel.setPreferredSize(new Dimension(10, 40));
		middlePanel.setPreferredSize(new Dimension(10, 40));
		southPanel.setPreferredSize(new Dimension(10,40));

		buttonSquare.setPreferredSize(new Dimension(160, 30));
		buttonLoan.setPreferredSize(new Dimension(160, 30));
		buttonMetaSearch.setPreferredSize(new Dimension(160, 30));
		
		buttonMain.setPreferredSize(new Dimension(400,30));
		
		middlePanel.add(buttonMain);				
		topPanel.add(buttonHelp);
			
		southPanel.add(buttonSquare, BorderLayout.EAST);
		southPanel.add(buttonLoan, BorderLayout.CENTER);
		southPanel.add(buttonMetaSearch, BorderLayout.WEST);
		
		principalPanel.add(topPanel, BorderLayout.NORTH);
		principalPanel.add(middlePanel);
		principalPanel.add(southPanel, BorderLayout.AFTER_LAST_LINE);
		
		add(principalPanel, BorderLayout.AFTER_LAST_LINE);
		
		//Selector para funcionalidad completa
		checkFunctionality = new JCheckBox("Complete analysis");
		topPanel.add(checkFunctionality, BorderLayout.EAST);
		checkFunctionality.setSelected(true);	
	}
}
