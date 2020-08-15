/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend.pojos;

import javax.swing.table.DefaultTableModel;


public class TablaModelo extends DefaultTableModel{
    
    @Override
    public boolean isCellEditable (int row, int column){
       // Aquí devolvemos true o false según queramos que una celda
       // identificada por fila,columna (row,column), sea o no editable
       if (column == -5 )
          return true;
       return false;
   }
}