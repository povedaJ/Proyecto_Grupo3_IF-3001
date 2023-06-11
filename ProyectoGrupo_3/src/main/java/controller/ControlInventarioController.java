package controller;

import domain.BTree;
import domain.BTreeNode;
import domain.Product;
import domain.TreeException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import ucr.proyecto.HelloApplication;
import util.Utility;

import java.io.IOException;

public class ControlInventarioController {

    @FXML
    private BorderPane bp;

    @FXML
    private TextField cantidadTextField;

    @FXML
    private ChoiceBox productosChoiceBox;
    private BTree registro;
    private int tamañoRegistro;
    ObservableList<String> nombreProductos;

    @FXML
    public void initialize() throws TreeException {
        registro= new BTree();
        //ejemplolistaProductos();
        llenarBTree();
        nombreProductos= FXCollections.observableArrayList();
        extraerNombres(registro.getRoot());
        productosChoiceBox.setItems(nombreProductos);
        tamañoRegistro = registro.size();
    }

    public void llenarBTree(){
        util.Utility.llenarProductosLista();

        Product[] list =Utility.getProductosList();
        int n = list.length;
        for (int i = 0; i < n; i++) {
            registro.add(list[i]);
        }
    }

    public void extraerNombres(BTreeNode node){
        if (node!=null){
            Product p = (Product) node.data;
            nombreProductos.add(p.getDescription());
            extraerNombres(node.left);
            extraerNombres(node.right);
        }
    }

    private void loadPage(String page) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(page));
        try {
            this.bp.setCenter(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnActualizaciónAutomatica(ActionEvent event) throws TreeException {
        int n = tamañoRegistro;
        if(n < registro.size()){
            eliminaNulos(registro.getRoot());
            if(n < registro.size()){
                eliminaNulos(registro.getRoot());
            }
        }
        cambiaAutomatico(registro.getRoot());

        if(n != registro.size()){
            eliminaNulos(registro.getRoot());
        }
        if(n != registro.size()){
            eliminaNulos(registro.getRoot());
        }
        if(n != registro.size()){
            eliminaNulos(registro.getRoot());
        }

        cambiarListaProductos(registro.getRoot(), 0);
    }

    public void cambiaAutomatico(BTreeNode node) throws TreeException {
        int cant= util.Utility.random(150); //Cantidad maxima de productos
        if (node!=null){
            Product p = (Product) node.data;
            if (p!=null){
                p.setCurrentStock(cant); //cambia la cantidad
                registro.remove(node.data);
                registro.add(p);
            }else{
                registro.remove(node.data);
            }
            cambiaAutomatico(node.left);
            cambiaAutomatico(node.right);
        }
    }
    public void eliminaNulos(BTreeNode node) throws TreeException {
        if (node!=null){
            if(node.data==null){
                registro.remove(node.data);
            }
            eliminaNulos(node.left);
            eliminaNulos(node.right);
        }
    }

    @FXML
    void btnActualizar(ActionEvent event) throws TreeException {
        String cant = cantidadTextField.getText();
        if (productosChoiceBox.getValue()!=null){
            if (!cant.isEmpty()){
                String producto = (String) productosChoiceBox.getValue();
                int cantidad = Integer.parseInt(cant);
                cambiarCantidad(registro.getRoot(), producto, cantidad);
                cambiarListaProductos(registro.getRoot(), 0); //debe actualizar toda la lista de productos
            }
        }
    }
    public void cambiarCantidad(BTreeNode node, String nombre, int cant) throws TreeException {
        if (node!=null){
            Product p = (Product) node.data;
            if (util.Utility.compare(p.getDescription(), nombre)==0){
                p.setCurrentStock(cant); //cambia la cantidad
                registro.remove(node.data);
                registro.add(p);
            }
            cambiarCantidad(node.left, nombre, cant);
            cambiarCantidad(node.right, nombre, cant);
        }
    }

    public void cambiarListaProductos(BTreeNode node, int k) throws TreeException {
        Product[] list = new Product[registro.size()];
        if (node!=null){
            list[k]= (Product) node.data;
            cambiarListaProductos(node.left, k+1);
            cambiarListaProductos(node.right, k+1);
        }
        util.Utility.setProductsList(list);
    }

    @FXML
    void btnRegresar(ActionEvent event) {
        loadPage("menuAdmin.fxml");
    }


}
