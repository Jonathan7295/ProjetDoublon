import java.text.SimpleDateFormat;
import java.util.Date;

public class Fichier {
	private String path;
	private String name;
	private long date;
	private long size;

	public Fichier(String path, String name, long date, long size) {
		super();
		this.path = path;
		this.name = name;
		this.date = date;
		this.size = size;
	}
	
	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}
	
	public String getFormatedSize(long size) {
        int taille = (int) (size / 1024) + 1;
        if (taille > 1024) {
            return (taille / 1024) + " Mo";
        } else {
            return taille + " ko";
        }
    }
	
	public long getDate() {
		return date;
	}
	
    public String getFormatedDate(long date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy H:mm:ss");
        Date d = new Date(date);
        return sdf.format(d);
    }

	public void setDate(long date) {
		this.date = date;
	}

	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
