package com.smartfinance;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Calendar;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;

import utils.BigDecimalUtils;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class CountCash extends Application{
	private final static BigDecimal COIN_05_CENT_MULTIPLIER = new BigDecimal(0.05); 
	private final static BigDecimal COIN_10_CENT_MULTIPLIER = new BigDecimal(0.10);
	private final static BigDecimal COIN_25_CENT_MULTIPLIER = new BigDecimal(0.25);
	private final static BigDecimal COIN_1_DOLLAR_MULTIPLIER = new BigDecimal(1);
	private final static BigDecimal COIN_2_DOLLAR_MULTIPLIER = new BigDecimal(2);
	private final static BigDecimal BILL_5_DOLLAR_MULTIPLIER = new BigDecimal(5);
	private final static BigDecimal BILL_10_DOLLAR_MULTIPLIER = new BigDecimal(10);
	private final static BigDecimal BILL_20_DOLLAR_MULTIPLIER = new BigDecimal(20);
	private final static BigDecimal BILL_50_DOLLAR_MULTIPLIER = new BigDecimal(50);
	private final static BigDecimal BILL_100_DOLLAR_MULTIPLIER = new BigDecimal(100);
	
	private BigDecimal totalCents = new BigDecimal(0);
	private BigDecimal totalBills = new BigDecimal(0);
	private BigDecimal total = new BigDecimal(0);
	
	private GridPane gridPane;
	
	private final static int ROW_DATE = 0;
	private final static int ROW_TOTAL = 1;
	private final static int ROW_TOTAL_CENTS = 2;
	private final static int ROW_TOTAL_BILLS = 3;
	private final static int ROW_SEPARATOR = 4;
	private final static int ROW_CENTS_5 = 5;
	private final static int ROW_CENTS_10 = 6;
	private final static int ROW_DOLLARS_5 = 7;
	
	private Label date;
	private DatePicker datePicker;
	
	private Label totalTextL;
	private Label totalLabelV;
	private Label totalCentsTextL;
	private Label totalCentsLabelV;
	private Label totalBillsTextL;
	private Label totalBillsLabelV;
	private Separator sepH;
	
	private Label cents5L;
	private TextField cents5V;
//	private Image cent5ObverseImg = new Image(getClass().getResourceAsStream("/005Canadian_Nickel_-_obverse_20.png"));
//	private Image cent5reverseImg = new Image(getClass().getResourceAsStream("resources\005Canadian_Nickel_-_reverse_20.png"));
//	private Image cent5ObverseImg = new Image("file:resources/005Canadian_Nickel_-_obverse_10.png");
//	private Image cent5ReverseImg = new Image("file:resources/005Canadian_Nickel_-_reverse_10.png");
	private Image cent5ReverseImg = new Image(getClass().getResourceAsStream("/005Canadian_Nickel_-_reverse_10.png"));
	
	private Label cents10L;
	private TextField cents10V;
//	private Image cent10ObverseImg = new Image("file:resources/010Canadian_Dime_-_obverse_10.png");
//	private Image cent10ReverseImg = new Image("file:resources/010Canadian_Dime_-_reverse_1_10.png");
	private Image cent10ReverseImg = new Image(getClass().getResourceAsStream("/010Canadian_Dime_-_reverse_1_10.png"));
	
	private Label dollars5L;
	private TextField dollars5V;
//	private Image dollars5FaceImg = new Image("file:resources/500Canadian_$5_note_specimen_-_face_1_10.png");
//	private Image dollars5BackImg = new Image("file:resources/500Canadian_$5_note_specimen_-_back_1_10.png");
	private Image dollars5BackImg = new Image(getClass().getResourceAsStream("/500Canadian_$5_note_specimen_-_back_1_10.png"));
 
	
	public static void main(String [] args){
		
		
		
//		total.setScale(2, BigDecimal.ROUND_HALF_UP);
//		totalCents.setScale(2, BigDecimal.ROUND_HALF_UP);
//		totalBills.setScale(2, BigDecimal.ROUND_HALF_UP);
		Application.launch(args);
	}

	
	
	
	
	@Override
	public void init() throws Exception {
		// TODO Auto-generated method stub
		
		super.init();
		//https://docs.oracle.com/javase/8/docs/technotes/guides/intl/enhancements.8.html
		//https://docs.oracle.com/javase/tutorial/i18n/locale/extensions.html
		//CLDR: http://www.unicode.org/reports/tr35/#Calendars
        System.setProperty("java.locale.providers", "HOST");
		
	}
	
	private void setTotalAndTotalCentsLabels(String oldValue, String newValue, BigDecimal multiplier){
		BigDecimal oldCents = new BigDecimal(StringUtils.isEmpty(oldValue) ? "0" : oldValue).multiply(multiplier); 
		BigDecimal newCents = new BigDecimal(StringUtils.isEmpty(newValue) ? "0" : newValue).multiply(multiplier);
		
		totalCents = totalCents.subtract(oldCents).add(newCents);
		total = total.subtract(oldCents).add(newCents);
		
		totalCentsLabelV.setText(BigDecimalUtils.printDecimal(totalCents, 2));
		totalLabelV.setText(BigDecimalUtils.printDecimal(total, 2));
	}
	
	private void setTotalAndTotalDollarsLabels(String oldValue, String newValue, BigDecimal multiplier){
		BigDecimal oldDollars= new BigDecimal(StringUtils.isEmpty(oldValue) ? "0" : oldValue).multiply(multiplier); 
		BigDecimal newDollars = new BigDecimal(StringUtils.isEmpty(newValue) ? "0" : newValue).multiply(multiplier);
		
		totalBills = totalBills.subtract(oldDollars).add(newDollars);
		total = total.subtract(oldDollars).add(newDollars);
		
		totalBillsLabelV.setText(BigDecimalUtils.printDecimal(totalBills, 2));
		totalLabelV.setText(BigDecimalUtils.printDecimal(total, 2));
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		//https://docs.oracle.com/javafx/2/ui_controls/separator.htm
		primaryStage.setTitle("Cash count");

		//Creating a GridPane container
		gridPane = new GridPane();
		gridPane.setPadding(new Insets(10, 10, 10, 10));
		gridPane.setVgap(5);
		gridPane.setHgap(5);
		
		//Defining the Date
		date = new Label("Date:");
		GridPane.setConstraints(date, 0, ROW_DATE);
		gridPane.getChildren().add(date);
		
		LocalDate localDate = LocalDate.now();
		System.out.println(localDate);
		TemporalField dowField = WeekFields.ISO.dayOfWeek();
		localDate = localDate.with(dowField, dowField.range().getMinimum());
		System.out.println(localDate);
		
		LocalDate localDate1 = LocalDate.now();
		System.out.println("localDate1"+localDate1);
		localDate1 = localDate1.with(DayOfWeek.MONDAY);
		System.out.println(localDate1);
//		LocalDate.
		
		datePicker = new DatePicker();
//		http://myjavafx.blogspot.ca/2012/01/javafx-calendar-control.html
//		datePicker.setLocale(Locale.GERMAN);
//		datePicker.getCalendarView().setCalendar(new BuddhistCalendar());
//		Locale myLocale = new Locale.Builder().setLanguage("en").setRegion("FR").build();
//		Locale myLocale = new Locale("ru");//Monday first
//        Locale.setDefault(Locale.Category.FORMAT, myLocale); //russian lang, monday
//        Locale.setDefault(Locale.Category.DISPLAY, Locale.US); //russian lang, monday

//        Locale.setDefault(Locale.Category.FORMAT, Locale.US); //english lang, sunday
//        Locale.setDefault(Locale.Category.DISPLAY, myLocale);   //english lang, sunday

//        Locale myLocale = new Locale.Builder().setLanguageTag("me-ME-u-ca-gregory").build();
//        Locale myLocale = new Locale.Builder().setLanguageTag("en-US-u-fw-mon").build();
//        Locale myLocale = new Locale.Builder().setLanguageTag("me-ME-u-fw-mon").build();
//        ResourceBundle resourceBundle = ResourceBundle.getBundle("locale.CustomLocale", myLocale);
//        Locale.setDefault(myLocale);
        System.out.println(WeekFields.of(Locale.getDefault()).getFirstDayOfWeek());
		
		
		
//		Calendar cal = Calendar.getInstance(myLocale);
//		cal.setFirstDayOfWeek(Calendar.MONDAY);
//		System.out.println(cal.getFirstDayOfWeek());
		
		
		GridPane.setConstraints(datePicker, 1, ROW_DATE);
		GridPane.setColumnSpan(datePicker, 3);
		gridPane.getChildren().add(datePicker);
		
		//Defining the Total
		totalTextL = new Label("Total:");
		totalTextL.setStyle("-fx-font-weight: bold");
		GridPane.setConstraints(totalTextL, 0, ROW_TOTAL);
		gridPane.getChildren().add(totalTextL);
		
		totalLabelV = new Label();
		totalLabelV.setStyle("-fx-font-weight: bold");
		GridPane.setConstraints(totalLabelV, 1, ROW_TOTAL);
		gridPane.getChildren().add(totalLabelV);

		//Defining the Total cents		
		totalCentsTextL = new Label("Total cents:");
		GridPane.setConstraints(totalCentsTextL, 0, ROW_TOTAL_CENTS);
		gridPane.getChildren().add(totalCentsTextL);
		
		totalCentsLabelV = new Label();
		GridPane.setConstraints(totalCentsLabelV, 1, ROW_TOTAL_CENTS);
		gridPane.getChildren().add(totalCentsLabelV);
		
		//Defining the Total bills
		totalBillsTextL = new Label("Total bills:");
		GridPane.setConstraints(totalBillsTextL, 0, ROW_TOTAL_BILLS);
		gridPane.getChildren().add(totalBillsTextL);
		
		totalBillsLabelV = new Label();
		GridPane.setConstraints(totalBillsLabelV, 1, ROW_TOTAL_BILLS);
		gridPane.getChildren().add(totalBillsLabelV);
		
		//Horizontal Separator
		Separator sepH = new Separator();
		sepH.setValignment(VPos.CENTER);
		GridPane.setConstraints(sepH, 0, ROW_SEPARATOR);
		GridPane.setColumnSpan(sepH, 4);
		gridPane.getChildren().add(sepH);
		
		
		//Defining the 5 cents
		cents5L = new Label("5 cents");
		GridPane.setConstraints(cents5L, 0, ROW_CENTS_5);
		gridPane.getChildren().add(cents5L);
		
		cents5V = new TextField();
		cents5V.setPromptText("Enter quantity of 5 cents");
		GridPane.setConstraints(cents5V, 1, ROW_CENTS_5);
		gridPane.getChildren().add(cents5V);
		cents5V.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				setTotalAndTotalCentsLabels(oldValue, newValue, COIN_05_CENT_MULTIPLIER);
			}
			
		});
		
//		final ImageView cent5Obverse = new ImageView(cent5ObverseImg);
//		GridPane.setConstraints(cent5Obverse, 2, ROW_CENTS_5);
//		gridPane.getChildren().add(cent5Obverse);
		
		final ImageView cent5Reverse = new ImageView(cent5ReverseImg);
		GridPane.setConstraints(cent5Reverse, 3, ROW_CENTS_5);
		gridPane.getChildren().add(cent5Reverse);
		
		//Defining the 10 cents
		cents10L = new Label("10 cents");
		GridPane.setConstraints(cents10L, 0, ROW_CENTS_10);
		gridPane.getChildren().add(cents10L);
		
		cents10V = new TextField();
		cents10V.setPromptText("Enter quantity of 10 cents");
		GridPane.setConstraints(cents10V, 1, ROW_CENTS_10);
		gridPane.getChildren().add(cents10V);
		cents10V.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				setTotalAndTotalCentsLabels(oldValue, newValue, COIN_10_CENT_MULTIPLIER);
			}
			
		});
		
//		final ImageView cent10Obverse = new ImageView(cent10ObverseImg);
//		GridPane.setConstraints(cent10Obverse, 2, ROW_CENTS_10);
//		gridPane.getChildren().add(cent10Obverse);
		
		final ImageView cent10Reverse = new ImageView(cent10ReverseImg);
		GridPane.setConstraints(cent10Reverse, 3, ROW_CENTS_10);
		gridPane.getChildren().add(cent10Reverse);
		
		
		
		
		//Defining the 5 dollars
		dollars5L = new Label("5 dollars");
		GridPane.setConstraints(dollars5L, 0, ROW_DOLLARS_5);
		gridPane.getChildren().add(dollars5L);
		
		dollars5V = new TextField();
		dollars5V.setPromptText("Enter quantity of 5 dollars");
		GridPane.setConstraints(dollars5V, 1, ROW_DOLLARS_5);
		gridPane.getChildren().add(dollars5V);
		dollars5V.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				setTotalAndTotalDollarsLabels(oldValue, newValue, BILL_5_DOLLAR_MULTIPLIER);
			}
			
		});
		
//		final ImageView dollars5Face = new ImageView(dollars5FaceImg);
//		GridPane.setConstraints(dollars5Face, 2, ROW_DOLLARS_5);
//		gridPane.getChildren().add(dollars5Face);
		
		final ImageView dollars5Back = new ImageView(dollars5BackImg);
		GridPane.setConstraints(dollars5Back, 3, ROW_DOLLARS_5);
		gridPane.getChildren().add(dollars5Back);
		
		
		
		
		
		
		
		
		
////		setOnKeyReleased(new EventHandler<KeyEvent>() {
////
////			@Override
////			public void handle(KeyEvent event) {
////				nameL.setText(firstNameString);
////			}
////			
////		});
//		GridPane.setConstraints(name, 0, 0);
//		gridPane.getChildren().add(name);
//		
//		//Defining the Last Name text field
//		final TextField lastName = new TextField();
//		lastName.setPromptText("Enter your last name.");
//		lastName.setPrefColumnCount(10);
//		String lastNameString = lastName.getText();
//		GridPane.setConstraints(lastName, 0, 1);
//		gridPane.getChildren().add(lastName);
//		
//		//Defining the Comment text field
//		final TextField comment = new TextField();
//		comment.setPromptText("Enter your comment.");
//		comment.setPrefColumnCount(15);
//		String commentString = comment.getText();
//		GridPane.setConstraints(comment, 0, 2);
//		gridPane.getChildren().add(comment);
//		
//		//Defining the Submit button
//		final Button submit = new Button();
//		submit.setText("Submit");
//		GridPane.setConstraints(submit, 0, 3);
//		gridPane.getChildren().add(submit);
		
		Scene scene = new Scene(gridPane);
		primaryStage.setScene(scene);
		primaryStage.show();
//		primaryStage.setMaximized(true);
	}
	
	

}
