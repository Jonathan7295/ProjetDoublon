import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class Table extends AbstractTableModel {
    private final List<Fichier> file = new ArrayList<Fichier>();
    private final String[] entetes = {"path","name","date","size"};
 
    public Table() { 

    }
 
    public int getRowCount() {
        return file.size();
    }
 
    public int getColumnCount() {
        return entetes.length;
    }
 
    public String getColumnName(int columnIndex) {
        return entetes[columnIndex];
    }
 
    public Object getValueAt(int rowIndex, int columnIndex) {
    	 switch(columnIndex){
         case 0:
             return file.get(rowIndex).getPath();
         case 1:
             return file.get(rowIndex).getName();
         case 2:
        	 String dateret ="";
        	 dateret = file.get(rowIndex).getFormatedDate(file.get(rowIndex).getDate());
        	 return dateret;
         case 3:
        	 String taille = "";
        	 taille = file.get(rowIndex).getFormatedSize(file.get(rowIndex).getSize());
        	 return taille;
         default:
             return null;
    	 }
    }
 
    public void addFile(Fichier fichier) {
        file.add(fichier);
 
        fireTableRowsInserted(file.size() -1, file.size() -1);
    }
}