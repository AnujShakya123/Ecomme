package com.example.ecomm;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


import java.io.IOException;

public class Ecommerce extends Application {
//     Login login=new Login();

    private final int width=500,height=600,headerLine=50;
    ProductList productList=new ProductList();
    Pane bodyPane;

    ObservableList<Product> cartItemList= FXCollections.observableArrayList();
    Button signInButton=new Button("Sign In");

    Button placeOrderButton=new Button("Place Order");
    Label welcomeLabel=new Label("Welcome Customer");
    Customer loggedInCustomer=null;
    private void addItemsToCart(Product product){
        if(cartItemList.contains(product))
            return;
        cartItemList.add(product);
        System.out.println("Products in Cart "+cartItemList.stream().count());
    }
    private GridPane headerBar(){
         TextField searchBar=new TextField();
         Button searchButton=new Button("Search");
         Button cartButton=new Button("Cart");
         searchButton.setOnAction(new EventHandler<ActionEvent>() {
             @Override
             public void handle(ActionEvent actionEvent) {
                 bodyPane.getChildren().clear();
                 bodyPane.getChildren().add(productList.getAllProducts());
             }
         });

         signInButton.setOnAction(new EventHandler<ActionEvent>(){
             @Override
             public void handle(ActionEvent actionEvent){
             bodyPane.getChildren().clear();
             bodyPane.getChildren().add(LoginPage());
             }
                                  });
         cartButton.setOnAction(new EventHandler<ActionEvent>() {
             @Override
             public void handle(ActionEvent actionEvent) {
                 bodyPane.getChildren().clear();
                 bodyPane.getChildren().add(productList.productsInCart(cartItemList));
             }
         });
        GridPane header=new GridPane();
         header.setHgap(10);

         header.add(searchBar,0,0);
         header.add(searchButton,1,0);
         header.add(signInButton,2,0);
         header.add(welcomeLabel,3,0);
         header.add(cartButton,4,0);
         return header;

     }
     private GridPane LoginPage(){
         Label userLabel=new Label("User Name");
         Label passLabel=new Label("Password");
         TextField userName=new TextField();
         userName.setText("anujshakya808@gmail.com");
         userName.setPromptText("Enter User Name");
         PasswordField password=new PasswordField();
         password.setText("abc");
         password.setPromptText("Enter Password");
         Button loginButton=new Button("Login");
         Label messageLabel= new Label("Login-Message");

         loginButton.setOnAction(new EventHandler<ActionEvent>() {
             @Override
             public void handle(ActionEvent actionEvent) {
              String user = userName.getText();
              String pass = password.getText();
              loggedInCustomer = Login.customerLogin(user,pass);
              if(loggedInCustomer != null){
                  messageLabel.setText("Login Successful!!");
                  welcomeLabel.setText("Welcome " + loggedInCustomer.getName());
              }
              else{
                  messageLabel.setText("Login Failed!1");
              }
             }
         });
         GridPane loginPane=new GridPane();
         loginPane.setTranslateY(50);
         loginPane.setTranslateX(50);
         loginPane.setVgap(10);
         loginPane.setHgap(10);
         loginPane.add(userLabel,0,0);
         loginPane.add(userName,1,0);
         loginPane.add(passLabel,0,1);
         loginPane.add(password,1,1);
         loginPane.add(loginButton,0,2);
         loginPane.add(messageLabel,1,2);
         return loginPane;
    }
    private void showDialogue(String message){
        Dialog<String> dialog=new Dialog<String>();
        dialog.setTitle("Order Status");
        ButtonType type=new ButtonType("Ok",ButtonBar.ButtonData.OK_DONE);
        dialog.setContentText(message);
        dialog.getDialogPane().getButtonTypes().add(type);

        dialog.showAndWait();
    }

    private GridPane footerBar(){
        Button buyNowButton=new Button("Buy Now");
        Button addToCartButton=new Button("Add To Cart");
        buyNowButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Product product = productList.getSelectedProduct();
                boolean orderrrrrStatus = false;
                if (product != null && loggedInCustomer != null) {
                    orderrrrrStatus=order.placeOrder(loggedInCustomer, product);
                }
                if (orderrrrrStatus == true) {
                    showDialogue("Order Successful");

                } else {
                }
            }
        });
        addToCartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Product product=productList.getSelectedProduct();
                addItemsToCart(product);
            }
        });
        placeOrderButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                int orderrrrrCount = 0;
                if (!cartItemList.isEmpty() && loggedInCustomer != null) {
                    orderrrrrCount=order.placeOrderMultipleProducts(cartItemList, loggedInCustomer);
                }
                if (orderrrrrCount >0) {
                    showDialogue("Order for "+ orderrrrrCount + " products placed Successfully");

                } else {
                }
            }
        });
        GridPane footer=new GridPane();
        footer.setHgap(10);
        footer.setTranslateY(headerLine+height);
        footer.add(buyNowButton,0,0);
        footer.add(addToCartButton,1,0);
        footer.add(placeOrderButton,2,0);
        return footer;
    }
    private Pane createContent(){
        Pane root=new Pane();
        root.setPrefSize(width,height+2*headerLine);

        bodyPane=new Pane();
        bodyPane.setPrefSize(width,height);
        bodyPane.setTranslateY(headerLine);
        bodyPane.setTranslateX(10);

        bodyPane.getChildren().add(LoginPage());

       root.getChildren().addAll(headerBar()
//               ,LoginPage(),
//               productList.getAllProducts()
               ,bodyPane
               ,footerBar()
       );
        return root;
    }


    @Override
    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(createContent());
        stage.setTitle("Ecommerce");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}