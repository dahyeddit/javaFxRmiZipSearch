package rmi.zip.client;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import rmi.zip.service.IZipSearchService;
import rmi.zip.vo.ZipVO;
import util.FxAlert;

public class ZipSearchMainController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> cmbSelect;

    @FXML
    private TextField tfData;

    @FXML
    private Button btnSearch;

    @FXML
    private TableView<ZipVO> zipTable;

    @FXML
    private TableColumn<?, ?> zipCol;

    @FXML
    private TableColumn<?, ?> sidoCol;

    @FXML
    private TableColumn<?, ?> gugunCol;

    @FXML
    private TableColumn<?, ?> dongCol;

    @FXML
    private TableColumn<?, ?> riCol;

    @FXML
    private TableColumn<?, ?> bldgCol;

    @FXML
    private TableColumn<?, ?> bunjiCol;
    
    // 검색버튼 클릭했을때 이벤트 처리
    @FXML
    void zipSearch(ActionEvent event) throws RemoteException {
    	String searchData = tfData.getText().trim();
    	if(searchData.isEmpty()) {
    		FxAlert.alert("우편번호검색", "검색할 단어를 입력하세요");
    		return;
    	}
    	
    	
    	List<ZipVO> resultList = null;
    	
    	// 콤보박스의 선택 항목을 구분해서 검색 처리
    	String searchField = cmbSelect.getValue();
    	if("동이름".equals(searchField)) {
    		resultList = service.getZipSearchDong(searchData);
    	}else if("우편번호".equals(searchField)) {
    		resultList = service.getZipSearchCode(searchData);
    	}
    	
    	// 검색 결과를 TableView에 사용하는 List에 넣어준다.
    	zipList.clear();
    	zipList.addAll(resultList);
    	
    }
    
    // service객체와 데이터가 저장될 변수 선언
    private IZipSearchService service;
    private ObservableList<ZipVO> zipList = 
    		FXCollections.observableArrayList();
    
    
    
    @FXML
    void initialize() {
        assert cmbSelect != null : "fx:id=\"cmbSelect\" was not injected: check your FXML file 'ZipSearchMain.fxml'.";
        assert tfData != null : "fx:id=\"tfData\" was not injected: check your FXML file 'ZipSearchMain.fxml'.";
        assert btnSearch != null : "fx:id=\"btnSearch\" was not injected: check your FXML file 'ZipSearchMain.fxml'.";
        assert zipTable != null : "fx:id=\"zipTable\" was not injected: check your FXML file 'ZipSearchMain.fxml'.";
        assert zipCol != null : "fx:id=\"zipCol\" was not injected: check your FXML file 'ZipSearchMain.fxml'.";
        assert sidoCol != null : "fx:id=\"sidoCol\" was not injected: check your FXML file 'ZipSearchMain.fxml'.";
        assert gugunCol != null : "fx:id=\"gugunCol\" was not injected: check your FXML file 'ZipSearchMain.fxml'.";
        assert dongCol != null : "fx:id=\"dongCol\" was not injected: check your FXML file 'ZipSearchMain.fxml'.";
        assert riCol != null : "fx:id=\"riCol\" was not injected: check your FXML file 'ZipSearchMain.fxml'.";
        assert bldgCol != null : "fx:id=\"bldgCol\" was not injected: check your FXML file 'ZipSearchMain.fxml'.";
        assert bunjiCol != null : "fx:id=\"bunjiCol\" was not injected: check your FXML file 'ZipSearchMain.fxml'.";

        //service객체 구하기
        try {
			Registry reg 
				= LocateRegistry.getRegistry("localhost",9988);
			service = (IZipSearchService) reg.lookup("zipSearch");
		} catch (RemoteException e) {
			// TODO: handle exception
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        //콤보박스 설정
        cmbSelect.getItems().addAll("동이름", "우편번호");
        cmbSelect.setValue("동이름");
        
        // TableView 각 컬럼 설정
        zipCol.setCellValueFactory(new PropertyValueFactory<>("zipcode"));
        sidoCol.setCellValueFactory(new PropertyValueFactory<>("sido"));
        gugunCol.setCellValueFactory(new PropertyValueFactory<>("gugun"));
        dongCol.setCellValueFactory(new PropertyValueFactory<>("dong"));
        riCol.setCellValueFactory(new PropertyValueFactory<>("ri"));
        bldgCol.setCellValueFactory(new PropertyValueFactory<>("bldg"));
        bunjiCol.setCellValueFactory(new PropertyValueFactory<>("bunji"));
        
        //TableView에 데이터 셋팅
        zipTable.setItems(zipList);
        
        
        
    }
}
