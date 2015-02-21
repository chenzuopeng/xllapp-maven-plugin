package ${daoPackage};

import org.xllapp.mvc.dao.CRUDDao;
import org.xllapp.mybatis.MyBatisRepository;

import ${entityFullName};

@MyBatisRepository
public interface ${daoName} extends CRUDDao<${entityName}> {
	
}