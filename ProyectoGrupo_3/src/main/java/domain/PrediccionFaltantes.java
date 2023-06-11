package domain;
import java.util.ArrayList;
import java.util.List;

public class PrediccionFaltantes {

    private static class Pedido {
        private Product producto;
        private int cantidad;

        public Pedido(Product producto, int cantidad) {
            this.producto = producto;
            this.cantidad = cantidad;
        }

        public Product getProducto() {
            return producto;
        }

        public int getCantidad() {
            return cantidad;
        }
    }

    private static class Faltante {
        private Product producto;
        private int faltantes;

        public Faltante(Product producto, int faltantes) {
            this.producto = producto;
            this.faltantes = faltantes;
        }

        public Product getProducto() {
            return producto;
        }

        public int getFaltantes() {
            return faltantes;
        }
    }

    public List<Faltante> prediccionFaltantes(List<Pedido> historialPedidos) {
        BSTTree bstTree = new BSTTree();

        // Insertar los pedidos en el BST
        for (Pedido pedido : historialPedidos) {
            bstTree.insert(pedido.getCantidad(), pedido.getProducto());
        }

        // Obtener los productos más demandados (en orden descendente)
        BSTTree.Node[] nodosMasDemandados = bstTree.getTopKNodes(3); // Cambiar el número '3' por la cantidad deseada

        // Calcular los posibles faltantes para cada producto más demandado
        List<Faltante> faltantes = new ArrayList<>();
        for (BSTTree.Node nodo : nodosMasDemandados) {
            Product producto = (Product) nodo.value;
            int cantidad = nodo.key;
            int faltantesCantidad = calcularFaltantes(cantidad); // Implementa tu algoritmo de cálculo de faltantes aquí
            Faltante faltante = new Faltante(producto, faltantesCantidad);
            faltantes.add(faltante);
        }

        return faltantes;
    }

    private int calcularFaltantes(int cantidad) {
        int stockActual = 50; // Cantidad actual en el inventario
        // (falta conectar con el archivo, actualmente esa variable esta quemada )
        int faltantes = cantidad - stockActual;
        if (faltantes < 0) {
            faltantes = 0;
        }
        return faltantes;
    }
}
