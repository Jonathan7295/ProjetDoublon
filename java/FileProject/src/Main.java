import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class Main extends JFrame {
    private Table t = new Table();
    private JTable tableau;
 
    public Main() {
        super();
 
        setTitle("Application File");
        this.setSize(800, 400);
        this.setLocation(100, 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
		int nombre_coeurs = Runtime.getRuntime().availableProcessors();
		String rep =  "C:/Users/Jonathan/Documents/Photo/Test/Rep1/text.txt";
		System.out.println(nombre_coeurs);
		ExecutorService pool = Executors.newFixedThreadPool(nombre_coeurs);
		File[] racines = File.listRoots();
		String ret = "";
		for (File racine : racines)
		{
			//pool.submit(new NewRunnableService(racine.getAbsolutePath(), rep));
			pool.submit(new NewRunnableService(racine.getAbsolutePath(), rep), t);
		}
		pool.shutdown();
		
        /*t.addFile(new Fichier(fichierMaitre.getPath(),fichierMaitre.getName(),fichierMaitre.lastModified(),fichierMaitre.length()));
        t.addFile(new Fichier(fichierMaitre.getPath(),fichierMaitre.getName(),fichierMaitre.lastModified(),fichierMaitre.length()));
        t.addFile(new Fichier(fichierMaitre.getPath(),fichierMaitre.getName(),fichierMaitre.lastModified(),fichierMaitre.length()));*/
        
        for(int i = 0; i<t.getRowCount();i++)
        {
        	System.out.println(t.getValueAt(i, 0));
        }
        
        tableau = new JTable(t);
 
        getContentPane().add(new JScrollPane(tableau), BorderLayout.CENTER);
 
        JPanel boutons = new JPanel();
 
        boutons.add(new JButton(new AddAction()));
        boutons.add(new JButton(new RemoveAction()));
 
        getContentPane().add(boutons, BorderLayout.SOUTH);
 
        pack();
    }
 
    public static void main(String[] args) {
        new Main().setVisible(true);
    }
 
    private class AddAction extends AbstractAction {
        private AddAction() {
            super("Ajouter");
        }

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			
		}
    }
 
    private class RemoveAction extends AbstractAction {
        private RemoveAction() {
            super("Supprimmer");
        }
 
        public void actionPerformed(ActionEvent e) {
           
        }
    }
}