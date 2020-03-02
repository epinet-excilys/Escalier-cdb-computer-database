package fr.excilys.exception;

import java.sql.SQLException;



// TODO : COMPLETER L'EXCEPTION POUR POUVOIR l'UTILISER CORRECTEMENT
// impossible de l'utiliser dans l'état pour remplacer SQL pour le moment
public  class DatasourceException  extends SQLException {
   
    public DatasourceException (String message) {
       super() ;
   }
}