import java.io.File;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	       // Pool avec 4 threads
		   /*NewRunnableService run = new NewRunnableService("C:/");
		   NewRunnableService run2 = new NewRunnableService("D:/");
	       ExecutorService pool = Executors.newFixedThreadPool(4); 
	       pool.submit(run);
	       pool.submit(run2);
	       pool.shutdown();*/
		int nombre_coeurs = Runtime.getRuntime().availableProcessors();
		System.out.println(nombre_coeurs);
		ExecutorService pool = Executors.newFixedThreadPool(nombre_coeurs);
		Arrays.stream(File.listRoots())
		      .map(File::getAbsolutePath)
		      .map(NewRunnableService::new)
		      .forEach(pool::submit);
		pool.shutdown();
	}
}
