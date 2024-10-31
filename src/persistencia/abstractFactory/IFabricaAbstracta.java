package persistencia.abstractFactory;

import persistencia.daos.IDAOFolios;
import persistencia.daos.IDAORevisiones;

public interface IFabricaAbstracta {
	IDAOFolios crearFolioDAO();
    IDAORevisiones crearRevisionDAO();
}
