/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nurelm.assessormapping.tree;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nurelm
 */
/**
 * Represents a Tree of Objects of generic type T. The Tree is represented as
 * a single rootElement which points to a List<Node<T>> of children. There is
 * no restriction on the number of children that a particular node may have.
 * This Tree provides a method to serialize the Tree into a List by doing a
 * pre-order traversal. It has several methods to allow easy updation of Nodes
 * in the Tree.
 * @param <T>
 */
public class Tree<T> {

    private Node<T> rootElement;
    private Integer greatestDepth = 0;

    /**
     * Default ctor.
     */
    public Tree() {
        super();
    }

    /**
     * Return the root Node of the tree.
     * @return the root element.
     */
    public Node<T> getRootElement() {
        return this.rootElement;
    }

    /**
     * Set the root Element for the tree.
     * @param rootElement the root element to set.
     */
    public void setRootElement(Node<T> rootElement) {
        this.rootElement = rootElement;
    }

    /**
     * Returns the Tree<T> as a List of Node<T> objects. The elements of the
     * List are generated from a pre-order traversal of the tree.
     * @return a List<Node<T>>.
     */
    public List<Node<T>> toList() {
        List<Node<T>> list = new ArrayList<Node<T>>();
        walk(rootElement, list);
        return list;
    }

    /**
     * Returns a String representation of the Tree. The elements are generated
     * from a pre-order traversal of the Tree.
     * @return the String representation of the Tree.
     */
    public String toString() {
        return toList().toString();
    }

    /**
     * Walks the Tree in pre-order style. This is a recursive method, and is
     * called from the toList() method with the root element as the first
     * argument. It appends to the second argument, which is passed by reference     * as it recurses down the tree.
     * @param element the starting element.
     * @param list the output of the walk.
     */
    private void walk(Node<T> element, List<Node<T>> list) {
        list.add(element);
        for (Node<T> data : element.getChildren()) {
            walk(data, list);
        }
    }

    /**
     *
     * @return
     */
    public int getDepth(){
      calcDepth(rootElement);
      return this.greatestDepth;
    }

    private int calcDepth(Node<T> element){
      for(Node<T> item : element.getChildren()){
        calcDepth(item);
        return this.greatestDepth += 1;
      }
      return 1;
    }

    //public Node<T>
}
