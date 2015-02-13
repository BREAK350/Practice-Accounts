package break350.accounts.controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Iterator;
import java.util.ResourceBundle;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import break350.accounts.model.Account;
import break350.accounts.report.Report;

public class MainController implements Initializable {
	private Stage stage;
	@FXML
	private TextField rate;
	@FXML
	private Button print;
	@FXML
	private Button export;
	@FXML
	private Button months;
	@FXML
	private Button currRate;
	@FXML
	private Label working;
	@FXML
	private ComboBox<String> exportFormat;

	@FXML
	private TableView<Account> table;

	@FXML
	private TableColumn<Account, Integer> index;
	@FXML
	private TableColumn<Account, String> name;
	@FXML
	private TableColumn<Account, Integer> worked;
	@FXML
	private TableColumn<Object, Integer> own;
	@FXML
	private TableColumn<Object, Integer> hospital;
	@FXML
	private TableColumn<Account, Double> salary;
	@FXML
	private TableColumn<Account, Double> eur;
	@FXML
	private TableColumn<Account, Double> uah;

	private ObservableList<Account> data = FXCollections.observableArrayList();
	private int[] days = new int[12];

	public void initialize(URL location, ResourceBundle resources) {
		setCellsValueFactorys();
		setCellsFactorys();
		setOnActions();
		initRate();
		loadFromFile("Employees.txt", 20, getRate());
		loadDays("Months.txt");
		initDays();
		table.setItems(data);
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	private void initDays() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);
		int month = cal.get(Calendar.MONTH);
		setWorkingDay(days[month]);
	}

	private void initRate() {
		double r = getRateFromWeb();
		rate.setText(String.valueOf(round(r, 10000)));
	}

	private double getRateFromWeb() {
		String url = "http://bank-ua.com/export/currrate.xml";
		double r = 0;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		try {
			db = dbf.newDocumentBuilder();
			try {
				Document doc = db.parse(new URL(url).openStream());
				NodeList nodeList = doc.getChildNodes().item(0).getChildNodes();
				int i = 0;
				boolean eur = false;
				while (r == 0 && i < nodeList.getLength()) {
					Node node = nodeList.item(i);
					NodeList itemList = node.getChildNodes();
					int j = 0;
					while (r == 0 && j < itemList.getLength()) {
						Node itemNode = itemList.item(j);
						if (itemNode.getNodeName().equals("char3")
								&& itemNode.getTextContent() != null) {
							eur = itemNode.getTextContent().equals("EUR");
						}
						if (eur && itemNode.getNodeName().equals("rate")
								&& itemNode.getTextContent() != null) {
							r = Double.parseDouble(itemNode.getTextContent());
						}
						++j;
					}
					++i;
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (ParserConfigurationException e1) {
			e1.printStackTrace();
		}
		return r / 100;
	}

	public String getExportType() {
		return exportFormat.getSelectionModel().getSelectedItem();
	}

	private void setOnActions() {
		rate.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				calculateSalary();
			}
		});
		print.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				// setWorkingDay();
				Report r = new Report();
				r.print(data);
			}
		});
		export.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				String type = getExportType();
				if (type != null) {
					FileChooser fch = new FileChooser();
					FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
							type.toUpperCase() + " files", "*." + type);
					fch.getExtensionFilters().add(extFilter);
					File file = fch.showSaveDialog(stage);
					if (file != null) {
						Report r = new Report();
						if (type.equals("xls")) {
							r.exportToXLS(data, file);
						} else if (type.equals("odt")) {
							r.exportToODT(data, file);
						}
					}
				}
			}
		});
		months.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				FXMLLoader loader = new FXMLLoader();
				Stage stage = new Stage();
				File fileMonth = new File("design/fxml/MonthDialog.fxml");
				try {
					loader.setLocation(fileMonth.toURI().toURL());
					Parent parent;
					try {
						parent = loader.load();
						Scene scene = new Scene(parent);
						stage.setScene(scene);
						stage.initModality(Modality.APPLICATION_MODAL);
						MonthDialogController controller = loader
								.getController();
						controller.setMainController(MainController.this);
						controller.setStage(stage);
						stage.showAndWait();
					} catch (IOException e) {
						e.printStackTrace();
					}
				} catch (MalformedURLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		currRate.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				initRate();
				calculateSalary();
			}
		});
	}

	private double round(double value, int dec) {
		return (double) ((int) (value * dec)) / dec;
	}

	public double getRate() {
		return Double.parseDouble(this.rate.getText());
	}

	private void calculateSalary() {
		double rate = getRate();
		for (Iterator<Account> iterator = data.iterator(); iterator.hasNext();) {
			Account account = iterator.next();
			account.calculateSalary(rate);
		}
	}

	private void setWorkingDay(int working) {
		double rate = getRate();
		for (Iterator<Account> iterator = data.iterator(); iterator.hasNext();) {
			Account account = iterator.next();
			account.setWorkingDay(working, rate);
		}
		this.working.setText(String.valueOf(working));
	}

	public void setMonth(int month) {
		setWorkingDay(days[month]);
	}

	private void setCellsFactorys() {
		own.setCellFactory(TextFieldTableCell
				.forTableColumn(new StringConverter<Integer>() {

					@Override
					public String toString(Integer object) {
						return object.toString();
					}

					@Override
					public Integer fromString(String string) {
						return Integer.decode(string);
					}
				}));
		own.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Object, Integer>>() {

			public void handle(CellEditEvent<Object, Integer> event) {
				((Account) event.getTableView().getItems()
						.get(event.getTablePosition().getRow())).setOwn(
						event.getNewValue(), getRate());
			}
		});

		hospital.setCellFactory(TextFieldTableCell
				.forTableColumn(new StringConverter<Integer>() {

					@Override
					public String toString(Integer object) {
						return object.toString();
					}

					@Override
					public Integer fromString(String string) {
						return Integer.decode(string);
					}
				}));
		hospital.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Object, Integer>>() {

			public void handle(CellEditEvent<Object, Integer> event) {
				((Account) event.getTableView().getItems()
						.get(event.getTablePosition().getRow())).setHospital(
						event.getNewValue(), getRate());
			}
		});
	}

	private void loadDays(String fileName) {
		try {
			@SuppressWarnings("resource")
			BufferedReader inputStream = new BufferedReader(
					new java.io.FileReader(fileName));
			String row;
			int i = 0;
			while ((row = inputStream.readLine()) != null) {
				days[i++] = Integer.parseInt(row);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void loadFromFile(String fileName, int working, double rate) {
		try {
			@SuppressWarnings("resource")
			BufferedReader inputStream = new BufferedReader(
					new java.io.FileReader(fileName));
			String row;
			int index = 1;
			while ((row = inputStream.readLine()) != null) {
				String[] spl = row.split("\t");
				String name = spl[0];
				int own = Integer.parseInt(spl[1]);
				int hospital = Integer.parseInt(spl[2]);
				int worked = working - own - hospital;
				double salary = Double.parseDouble(spl[3]);
				double eur = Account.getEUR(working, worked, hospital, salary);
				double uah = eur * rate;
				Account ac = new Account(index++, name, worked, own, hospital,
						salary, eur, uah);
				data.add(ac);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void setCellsValueFactorys() {
		index.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Account, Integer>, ObservableValue<Integer>>() {

			public ObservableValue<Integer> call(
					CellDataFeatures<Account, Integer> param) {
				return param.getValue().indexProperty().asObject();
			}
		});

		name.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Account, String>, ObservableValue<String>>() {

			public ObservableValue<String> call(
					CellDataFeatures<Account, String> param) {
				return param.getValue().nameProperty();
			}
		});

		worked.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Account, Integer>, ObservableValue<Integer>>() {

			public ObservableValue<Integer> call(
					CellDataFeatures<Account, Integer> param) {
				return param.getValue().workedProperty().asObject();
			}
		});

		own.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Object, Integer>, ObservableValue<Integer>>() {

			public ObservableValue<Integer> call(
					CellDataFeatures<Object, Integer> param) {
				return ((Account) param.getValue()).ownProperty().asObject();
			}
		});

		hospital.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Object, Integer>, ObservableValue<Integer>>() {

			public ObservableValue<Integer> call(
					CellDataFeatures<Object, Integer> param) {
				return ((Account) param.getValue()).hospitalProperty()
						.asObject();
			}
		});

		salary.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Account, Double>, ObservableValue<Double>>() {

			public ObservableValue<Double> call(
					CellDataFeatures<Account, Double> param) {
				return param.getValue().salaryProperty().asObject();
			}
		});

		eur.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Account, Double>, ObservableValue<Double>>() {

			public ObservableValue<Double> call(
					CellDataFeatures<Account, Double> param) {
				return param.getValue().eurProperty().asObject();
			}
		});

		uah.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Account, Double>, ObservableValue<Double>>() {

			public ObservableValue<Double> call(
					CellDataFeatures<Account, Double> param) {
				return param.getValue().uahProperty().asObject();
			}
		});
	}

}