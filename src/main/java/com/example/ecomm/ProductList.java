package com.example.ecomm;


import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

public class ProductList {
    public static TableView<Product>productTable;

    public  Pane getAllProducts() {

        ObservableList<Product> productList = Product.getAllProducts();
        return createTableFromList(productList);
    }

        public Pane createTableFromList(ObservableList<Product> productList) {
            TableColumn id = new TableColumn("IdA");
            id.setCellValueFactory(new PropertyValueFactory<>("id"));

            TableColumn name = new TableColumn("Name");
            name.setCellValueFactory(new PropertyValueFactory<>("name"));

            TableColumn price = new TableColumn("Price");
            price.setCellValueFactory(new PropertyValueFactory<>("price"));

//            ObservableList<Product> data = FXCollections.observableArrayList();
//            data.addAll(new Product(123, "Laptop", (double) 234.5),
//                    new Product(1245, "Laptop", (double) 234.5)
//            );

//            productList = Product.getAllProducts();
            productTable = new TableView<>();
            productTable.setItems(productList);
            productTable.getColumns().addAll(id, name, price);

            Pane tablePane = new Pane();
            tablePane.getChildren().add(productTable);

            return tablePane;
        }
        public Pane productsInCart(ObservableList<Product> productList){
        return createTableFromList(productList);
        }
        public Product getSelectedProduct () {
            return productTable.getSelectionModel().getSelectedItem();
        }
    }