package com.nurelm.genericabstracts.Controller;

import com.nurelm.assessormapping.annotation.FormDisplay;
import com.nurelm.assessormapping.struct.AccessorStruct;
import com.nurelm.assessormapping.struct.AccessorTypeEnum;
import com.nurelm.assessormapping.struct.FormVisibilityEnum;
import com.nurelm.assessormapping.tree.Node;
import com.nurelm.assessormapping.tree.Tree;
import com.nurelm.genericabstracts.DAO.AbstractDAO;
import com.nurelm.genericabstracts.Entity.AbstractEntity;
import com.nurelm.interceptordeps.NavigationStruct;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * The AbstractEntityController
 * @author Sam Shabaan (NuRelm)
 * @author Paul Scarrone (NuRelm)
 * @param <Entity> A JPA entity which extends the {@link AbstractEntity} Class;
 */
@SessionAttributes({"Current", "Parent", "Child", "CurrentId", "LastNode", "TreeView"})
@Controller
public abstract class AbstractEntityController<Entity extends AbstractEntity> implements ServletContextAware {

  private Class<? extends AbstractDAO<Entity>> daoClass;
  /**
   * Entity Class Object for extraction of DAO
   */
  protected Class entityClass;
  /**
   * The Request basePath of the Controller
   */
  protected String basePath;
  
  /**
   * Incomplete Set of tools used to ensure entity level security of requests
   * {@link EntitySecurity}
   */
  
  private ServletContext servletContext;
  
  /**
   * The base path of the controller request mapping
   * @return basePath
   */
  public String getBasePath() {
    return basePath;
  }

  /**
   * Defines the base path of the controller request mapping
   * @param basePath
   */
  public void setBasePath(String basePath) {
    this.basePath = basePath;
  }
  
  /**
   *
   * @return The Servlet Context
   */
  protected ServletRequestAttributes getServletRequest() {
    return (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
  }

  /**
   *
   * @param sc
   */
  @Override
  public void setServletContext(ServletContext sc) {
    this.servletContext = sc;
  }

  /**
   * Draws in the Navigation Context for internally controlling the history of the navigation
   * @return
   */
  public NavigationStruct getNavigationContext() {
    return (NavigationStruct) getServletRequest().getRequest().getSession().getAttribute("NavigationData");
  }
  
  /**
   * Specialized processing of the JumpBack field of the {@link NavigationStruct}
   * @param jumpBack
   */
  public void setNavigationJumpBack(String jumpBack){
    NavigationStruct navData = this.getNavigationContext();
    navData.setJumpBack(jumpBack);
    getServletRequest().getRequest().getSession().setAttribute("NavigationData", navData);
  }

  /**
   * Preferred Construction of the Super Controller
   * @param basePath
   * @param entityClass
   */
  public AbstractEntityController(String basePath, Class<Entity> entityClass) {
    this.entityClass = entityClass;
    try {
      this.daoClass = (Class<? extends AbstractDAO<Entity>>) entityClass.newInstance().getDAO().getClass();
    } catch (InstantiationException ex) {
      Logger.getLogger(AbstractEntityController.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IllegalAccessException ex) {
      Logger.getLogger(AbstractEntityController.class.getName()).log(Level.SEVERE, null, ex);
    }
    this.basePath = basePath;
  }

  /**
   * Abstract function to allow definition of the entity DAO within the extending controller
   * @deprecated 
   * @return
   */
  @Deprecated
  protected abstract AbstractDAO getDAO();

  /**
   * @deprecated 
   * Quick tool to allow the retrieval of the parent in the entity definition
   * @return
   */
  protected abstract AbstractDAO getParentDAO();

  private Entity getNewEntity() {
    Entity newEntity = null;
    try {
      Constructor constructor = this.entityClass.getConstructor();
      newEntity = (Entity) constructor.newInstance();
    } catch (Exception ex) {
      Logger.getLogger(AbstractEntityController.class.getName()).log(Level.SEVERE, null, ex);
    }
    return newEntity;
  }

  private Object getNewParentEntity() {
    Object newParentEntity = null;
    Constructor constructor;
    try {
      constructor = getNewEntity().getParentClass().getConstructor();
      newParentEntity = constructor.newInstance();
    } catch (Exception ex) {
      Logger.getLogger(AbstractEntityController.class.getName()).log(Level.SEVERE, null, ex);
    }

    return newParentEntity;
  }

  /**
   * All Hierarchy based views that need a tree model of their entity objects uses this method to simplify generation
   * This replaces the old method by allowing a tree to be generated from any entity not just that of the controller.
   * @param Entity
   * @param model
   */
  protected void getFrontView(AbstractEntity Entity, Model model) {
    Node<Object> nodeView = this.getTreeThreeLevels(Entity);
    model.addAttribute("TreeView", nodeView.getChildren());
  }
  
  private Collection<AccessorStruct> getEntityAccessors(AbstractEntity Entity) {
    ArrayList<AccessorStruct> Properties = new ArrayList<AccessorStruct>();
    try {
      AccessorStruct[] SortedProperties = null;
      ArrayList<Integer> SortOrder = new ArrayList<Integer>();
      //AbstractEntity item = (AbstractEntity) Entity.getClass().newInstance();
      Class<? extends AbstractEntity> itemClass = Entity.getClass();
      Method[] methods = Entity.getClass().getDeclaredMethods();
      for (Method m : methods) {
        Properties.add(new AccessorStruct(m));
      }
    } catch (Exception ex) {
      Logger.getLogger(AbstractEntityController.class.getName()).log(Level.SEVERE, null, ex);
    }
    return Properties;
  }

  /**
   * Nearly deprecated method to generate the scaffolding for an Entity. Similar to get front but can take any entity object as an argument
   * @param Entity
   * @return Collection<AccessorStruct> Unordered template of the entity based upon the Form Display annotation
   */
  public Collection<AccessorStruct> getFormFields(AbstractEntity Entity) {
    ArrayList<AccessorStruct> Properties = (ArrayList<AccessorStruct>) this.getEntityAccessors(Entity);
    ArrayList<AccessorStruct> FieldProperties = new ArrayList<AccessorStruct>();
    for (AccessorStruct item : Properties) {
      if (item.getType().equals(AccessorTypeEnum.GET)) {
        try {
          Field itemField = Entity.getClass().getDeclaredField(item.getBeanName());
          if (itemField.isAnnotationPresent(FormDisplay.class)) {
            FormDisplay annotationItems = (FormDisplay) itemField.getAnnotation(FormDisplay.class);
            item.setAltName(annotationItems.altName());
            item.setVisible(annotationItems.visible());
            item.setOrder(annotationItems.order());
            item.setType(annotationItems.type());
            if (item.getVisible() != FormVisibilityEnum.NOTDISPLAYED) {
              FieldProperties.add(item);
            }
          } else {
            FieldProperties.add(item);
          }
        } catch (Exception ex) {
          //Logger.getLogger(AbstractEntityController.class.getName()).log(Level.SEVERE, "Method Does not have Field", ex);
        }
      }
    }
    return FieldProperties;
  }

  /**
   * Appends the basepath to the session on reload
   * @deprecated 
   * @param model
   */
  protected void persistBasePath(Model model) {
    if (!model.containsAttribute("basepath")) {
      model.addAttribute("basepath", this.basePath);
    }
  }

  /**
   * Same as getFormFields except it handles the order of the items as well. Ignores visibility.
   * @param model 
   * @throws Exception If the entity method being processed doesn't have a field associated the process fails.
   */
  public void getEntityProperties(Model model) throws Exception {
    List<AccessorStruct> Properties = new ArrayList<AccessorStruct>();
    AccessorStruct[] SortedProperties = null;
    ArrayList<Integer> SortOrder = new ArrayList<Integer>();
    Entity item = (Entity) this.entityClass.newInstance();
    Class itemClass = item.getClass();
    Method[] methods = item.getClass().getDeclaredMethods();
    for (Method m : methods) {
      String rtype = m.getReturnType().getName();
      String accessorCheck = m.getName();
      if (!rtype.equals("void")) {
        AccessorStruct tempProp = new AccessorStruct(m);
        if (tempProp.getType().info().equals("get")) {
          try {
            Field itemField = itemClass.getDeclaredField(tempProp.getBeanName());
            if (itemField.isAnnotationPresent(FormDisplay.class)) {
              if (itemField.getAnnotation(FormDisplay.class).visible().equals(FormVisibilityEnum.DISPLAYED)) {
                final Field tempItemField = itemField;
                if (!tempItemField.getAnnotation(FormDisplay.class).altName().equals("")) {
                  Properties.add(new AccessorStruct(m) {

                    {
                      this.setShortName(tempItemField.getAnnotation(FormDisplay.class).altName());
                    }
                  });
                } else {
                  Properties.add(new AccessorStruct(m));
                }
                if (itemField.getAnnotation(FormDisplay.class).order() != -1) {
                  SortOrder.add(itemField.getAnnotation(FormDisplay.class).order());
                }
              }
            } else {
              Properties.add(new AccessorStruct(m));
            }
          } catch (Exception ex) {
            Logger.getLogger(AbstractEntityController.class.getName()).log(Level.SEVERE, "Method Does not have Field", ex);
          }
        }
      }
    }
    SortedProperties = new AccessorStruct[SortOrder.size()];
    ArrayList<AccessorStruct> FinalSortedProperties = new ArrayList<AccessorStruct>();
    int Index = 0;
    for (Integer PropertyItem : SortOrder) {
      SortedProperties[(int) PropertyItem] = Properties.get(Index++);
    }
    int offset = 0;
    for (AccessorStruct accessorItme : SortedProperties) {
      FinalSortedProperties.add(accessorItme);
    }
    for (AccessorStruct SortedProperty : FinalSortedProperties) {
      Properties.remove(SortedProperty);
    }

    FinalSortedProperties.addAll(Properties);
    model.addAttribute("FormFields", FinalSortedProperties);
  }

  private Tree<Object> getTreeThreeLevels(Integer id) {
    Tree<Object> treeView = new Tree<Object>();
    Node<Object> treeNodes = new Node<Object>();
    List<Object> tempList = new ArrayList<Object>();
    Entity topObject = (Entity) getDAO().find(id);
    treeView.setRootElement(new Node<Object>(topObject));
    treeNodes = treeView.getRootElement();
    tempList = (List<Object>) topObject.getChildren();
    for (Object item : tempList) {
      Node<Object> localNode = treeNodes.addChild(new Node<Object>(item));
      Class firstChildClass = item.getClass();
      Class[] parameterList = new Class[1];
      parameterList[0] = firstChildClass;
      Constructor firstChildCtor = null;
      AbstractEntity childClone = null;
      try {
        firstChildCtor = firstChildClass.getConstructor(parameterList);
        firstChildCtor.getGenericParameterTypes();
        childClone = (AbstractEntity) firstChildCtor.newInstance(item);
        childClone.getId();
      } catch (Exception ex) {
        Logger.getLogger(AbstractEntityController.class.getName()).log(Level.SEVERE, null, ex);
      }
      List<Object> innerTempList = (List<Object>) childClone.getChildren();
      for (Object innerItem : innerTempList) {
        localNode.addChild(new Node<Object>(innerItem));
      }
    }
    return treeView;
  }
  
  
    
  /**
   * Non recursive process to retrieve 3 child tiers of the relational hierarchy defined by the entities
   * @param Entity
   * @return A tree Node of type {@link Tree}
   */
  protected Node<Object> getTreeThreeLevels(final AbstractEntity Entity) {
    Node<Object> treeNodes = new Node<Object>();
    Node<Object> upperNode = new Node<Object>();
    Collection<Object> tempList = new ArrayList<Object>();
    List<? extends AbstractEntity> topObjects = new ArrayList<AbstractEntity>() {
      {
        add(Entity);
      }
    };
    for (AbstractEntity superItem : topObjects) {
      upperNode = treeNodes.addChild(new Node<Object>(superItem));
      tempList = superItem.getChildren();
      for (Object item : tempList) {
        Node<Object> localNode = upperNode.addChild(new Node<Object>(item));
        Class firstChildClass = item.getClass();
        Class[] parameterList = new Class[1];
        parameterList[0] = firstChildClass;
        Constructor firstChildCtor = null;
        AbstractEntity childClone = null;
        try {
          firstChildCtor = firstChildClass.getConstructor(parameterList);
          firstChildCtor.getGenericParameterTypes();
          childClone = (AbstractEntity) firstChildCtor.newInstance(item);
          childClone.getId();
        } catch (Exception ex) {
          Logger.getLogger(AbstractEntityController.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<Object> innerTempList = (List<Object>) childClone.getChildren();
        for (Object innerItem : innerTempList) {
          localNode.addChild(new Node<Object>(innerItem));
        }
      }
    }
    return treeNodes;
  }

  private Node<Object> getTreeThreeLevels() {
    Node<Object> treeNodes = new Node<Object>();
    Node<Object> upperNode = new Node<Object>();
    Collection<Object> tempList = new ArrayList<Object>();
    List<Entity> topObjects = (List<Entity>) getDAO().findAll();
    for (Entity superItem : topObjects) {
      upperNode = treeNodes.addChild(new Node<Object>(superItem));
      tempList = superItem.getChildren();
      for (Object item : tempList) {
        Node<Object> localNode = upperNode.addChild(new Node<Object>(item));
        Class firstChildClass = item.getClass();
        Class[] parameterList = new Class[1];
        parameterList[0] = firstChildClass;
        Constructor firstChildCtor = null;
        AbstractEntity childClone = null;
        try {
          firstChildCtor = firstChildClass.getConstructor(firstChildClass);
          firstChildCtor.getGenericParameterTypes();
          childClone = (AbstractEntity) firstChildCtor.newInstance(item);
          childClone.getId();
        } catch (Exception ex) {
          Logger.getLogger(AbstractEntityController.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<Object> innerTempList = (List<Object>) childClone.getChildren();
        for (Object innerItem : innerTempList) {
          localNode.addChild(new Node<Object>(innerItem));
        }
      }
    }
    return treeNodes;
  }
}
