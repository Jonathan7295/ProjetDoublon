import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class Main{
	private static Table t = new Table();
    private static JTable tableau;
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		// TODO Auto-generated method stub
		JFrame fenetre = new JFrame();
		fenetre.setVisible(true);
		fenetre.setTitle("Application File");
		fenetre.setSize(800, 400);
		fenetre.setLocation(100, 100);
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		ExecutorService execute = Executors.newFixedThreadPool(4);
		File[] racines = File.listRoots();
		List<File> res = null;
		List<Future<List<File>>> futures = new ArrayList<Future<List<File>>>();
		CompletionService<List<File>> completionService = new ExecutorCompletionService<List<File>>(execute);
		for (File racine : racines)
		{
			futures.add(completionService.submit(new NewCallable(racine.getAbsolutePath())));
		}
		execute.shutdown();
		
		int x = 0;
		for(int i = 0; i < racines.length; i++)
		{
			res = completionService.take().get();
			if(res != null)
			{
				Iterator<File> iterator = res.iterator();
				while(iterator.hasNext())
				{
					File re = iterator.next();
					t.addFile(new Fichier(re.getAbsolutePath(),re.getName(),re.lastModified(),re.length()));
					System.out.println(re.getAbsolutePath());
					iterator.remove();
				}
				/*for(File re : res)
				{
					t.addFile(new Fichier(re.getAbsolutePath(),re.getName(),re.lastModified(),re.length()));
					x = res.indexOf(re);
					System.out.println(x);
					res.remove(x);
				}*/
			}
		}
		tableau = new JTable(t);
		 
        fenetre.getContentPane().add(new JScrollPane(tableau), BorderLayout.CENTER);
 
        JPanel boutons = new JPanel();
 
        //boutons.add(new JButton(new AddAction()));
        //boutons.add(new JButton(new RemoveAction()));
 
        fenetre.getContentPane().add(boutons, BorderLayout.SOUTH);
 
        fenetre.pack();
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
