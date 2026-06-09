package Singleton;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import dto.AccountBookdto;

public class SingletonClass {

	private static SingletonClass sc=null;
	private List<AccountBookdto> list=new ArrayList<>();
	private final String file_path="AccountBook.txt";
	
	private SingletonClass() {
		loadFromFile();
	}
		
	public static SingletonClass getInstance() {
		if(sc==null) {
			sc= new SingletonClass();
		}
		return sc;
	}
	
	public List<AccountBookdto> getList(){
		return this.list;
	}
	
	//TODO load_file
	public void loadFromFile() {
		
		File file = new File(file_path);
		if (!file.exists()) {
			return; 
		}
		
		try (BufferedReader br=new BufferedReader(new FileReader(file))){
			String line;
			
			while ((line = br.readLine()) != null) {
				String[] data = line.split("\\|");
				
				if(data.length < 6) continue;
				
				// 배열 안의 조각들을 순서대로 매핑
				String ioKind = data[0];
				int income = Integer.parseInt(data[1]); // 문자를 숫자로 변환
				int expenses = Integer.parseInt(data[2]); // 문자를 숫자로 변환
				String title = data[3];
				String content = data[4];
				String daily = data[5];
				
				// 복원한 조각들로 다시 Dto 객체를 만들어서 메모리 리스트에 주입.
				AccountBookdto dto = new AccountBookdto(ioKind, income, expenses, title, content, daily);
				list.add(dto);
			}
			
			
		} catch (Exception e) {
			
		}	
		
	}
	
}
