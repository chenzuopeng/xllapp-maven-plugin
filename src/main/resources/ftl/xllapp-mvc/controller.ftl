package ${controllerPackage};

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import org.xllapp.mvc.controller.SimpleController;
import org.xllapp.mvc.dao.CRUDDao;

import ${daoFullName};
import ${entityFullName};


@RequestMapping("${entityNameFirstLower}")
@Controller
public class ${controllerName} extends SimpleController<${entityName}>{

	private ${daoName} ${daoNameFirstLower};

	@Override
	protected CRUDDao<${entityName}> getDao() {
		return this.${daoNameFirstLower};
	}

	@Autowired
	public void set${daoName}(${daoName} ${daoNameFirstLower}) {
		this.${daoNameFirstLower} = ${daoNameFirstLower};
	}
	
}
