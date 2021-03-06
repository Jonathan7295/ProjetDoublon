import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class NewCallable implements Callable<List<File>>{
	private String lecteur;
	private static List<File> tabfile = new ArrayList<File>();
	
	public NewCallable(String lecteur) {
		super();
		this.lecteur = lecteur;
	}

	public String getLecteur() {
		return lecteur;
	}

	public void setLecteur(String lecteur) {
		this.lecteur = lecteur;
	}

	@Override
	public List<File> call() throws Exception {
		// TODO Auto-generated method stub
		String rep =  "D:/CoursCDI/ProjetDoublon/Test/Rep1/Texte1.txt";
		File fichierMaitre = new File (rep);
		String extension = getExtension(fichierMaitre);
		String contenuFichierMaitre = loadFile(fichierMaitre);
		String md5Maitre = encode(contenuFichierMaitre);
		long taille = 0;
		if(fichierMaitre.isFile())
		{
			taille = fichierMaitre.length();
		}
		// TODO Auto-generated method stub
		System.out.println(lecteur);
		File lec = new File(lecteur);
		File[] docs = lec.listFiles();
		if(docs != null)
		{
			for(File doc : docs)
			{
				parcourir(doc.toString(), 0, taille, extension, fichierMaitre, md5Maitre);
			}
		}
		return tabfile;
	}
	
	public static void parcourir(String file, int niveau, long taille, String extension, File fichierMaitre, String md5Maitre)
	{
		String contenuFichier = "";
		String md5 = "";
		boolean identique = true;
		File[] fichiers = new File(file).listFiles();
		if(fichiers != null)
		{
			for (File fichier : fichiers)
			{
				if (fichier.isDirectory())
				{
					parcourir(fichier.getPath(), niveau++, taille, extension, fichierMaitre, md5Maitre);
				}
				else
				{
					if (fichier.isFile() && fichier.length() == taille && fichier.getName().endsWith(extension) && !fichier.getPath().equals(fichierMaitre.getPath()))
					{
						contenuFichier = loadFile(fichier);
						md5 = encode(contenuFichier);
						if(md5.equals(md5Maitre))
						{
							byte buffer[] = new byte[50];

							try {

								FileInputStream fis = new FileInputStream(fichier);
								FileInputStream fis2 = new FileInputStream(fichierMaitre);

								ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
								ByteArrayInputStream bais2 = new ByteArrayInputStream(buffer);

								int i = fis.read(buffer);
								int i2 = fis2.read(buffer);

								while (i!=-1 && i2 !=1 && identique == true) {

									int a = bais.read();
									int a2 = bais2.read();

									while (a != -1 && a2 != -1)
									{
										if((char) a == (char) a2)
										{
											a = bais.read();
											a2 = bais2.read();
											identique = true;
										} else {
											a = -1;
											a2 = -1;
											identique = false;
										}
									}

									i = fis.read(buffer);
									i2 = fis.read(buffer);

									bais.reset();
									bais2.reset();

								}

								bais.close();
								bais2.close();

								fis.close();
								fis2.close();
								
								if(identique == true)
								{
									//System.out.println(fichier.getPath());
									tabfile.add(fichier);
								}

							} catch (FileNotFoundException e){

								e.printStackTrace();

							} catch (IOException e) {

								e.printStackTrace();

							}
						}
					}
				}
			}
		}
	}
	
	public static String getExtension(File f)
	{ 
		if(f != null)
		{ 
			String filename = f.getName(); 
			int i = filename.lastIndexOf('.'); 
			if(i>0 && i<filename.length()-1) 
			{ 
				return filename.substring(i+1).toLowerCase(); 
			} 
		} 
		return null;  
	} 
	
	private static String encode(String contenu)
    {
        byte[] uniqueKey = contenu.getBytes();
        byte[] hash = null;

        try
        {
            hash = MessageDigest.getInstance("MD5").digest(uniqueKey);
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new Error("No MD5 support in this VM.");
        }

        StringBuilder hashString = new StringBuilder();
        for (int i = 0; i < hash.length; i++)
        {
            String hex = Integer.toHexString(hash[i]);
            if (hex.length() == 1)
            {
                hashString.append('0');
                hashString.append(hex.charAt(hex.length() - 1));
            }
            else
                hashString.append(hex.substring(hex.length() - 2));
        }
        return hashString.toString();
    }
	
	public static String loadFile(File f) 
	{
	    try {
	       BufferedInputStream in = new BufferedInputStream(new FileInputStream(f));
	       StringWriter out = new StringWriter();
	       int b;
	       while ((b=in.read()) != -1)
	           out.write(b);
	       out.flush();
	       out.close();
	       in.close();
	       return out.toString();
	    }
	    catch (IOException ie)
	    {
	         ie.printStackTrace(); 
	    }
		return "";
	}
}
