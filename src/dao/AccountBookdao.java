package dao;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import Singleton.SingletonClass;
import dto.AccountBookdto;


public class AccountBookdao {
	
	private static AccountBookdao instance = null;
	private final String file_path="AccountBook.txt";
	Scanner sc=new Scanner(System.in);
	
	private AccountBookdao() {
		
	}
	
	public static AccountBookdao getInstance() {
		if (instance==null) {
			instance = new AccountBookdao();
		}
		return instance;
	}
	
	private List<AccountBookdto> getSingletonList(){
		return SingletonClass.getInstance().getList();	
	}
	
	//CRUD
	
	//TODO insert()
	public void insert() {
		System.out.println("==========가계부에  추가=======");
		System.out.print("날짜를 입력하세요 ☞");
		String inputdaily=sc.nextLine();
		String daily = formatDate(inputdaily);
		System.out.print("수입 또는 지출을 선택하세요 ☞");
		String ioKind=sc.next();
		sc.nextLine(); //버퍼 비우기
		
		int income=0;
		int expenses=0;
		if (ioKind.equals("수입")) {
			System.out.print("수입 금액을 입력하세요 ☞ ");
			income = sc.nextInt();
		} else {
			System.out.print("지출 금액을 입력하세요 ☞ ");
			expenses = sc.nextInt();
		}
		sc.nextLine(); //버퍼 비우기
		
		System.out.print("분류를 입력하세요 (식비/월급 등) ☞ ");
		String title = sc.nextLine();
		System.out.print("상세내역을 입력하세요 ☞ ");
		String content = sc.nextLine();
		
		AccountBookdto dto=new AccountBookdto(ioKind, income, expenses, title, content, daily);
		getSingletonList().add(dto);
		System.out.println(daily+"의 가계부가 정상적으로 등록되었습니다.");
		save_file();
		
	}
	
	//TODO delete
	public void delete() {
		int selectnum=1;
		
		System.out.print("어느 날짜의 가계부를 지우시겠습니까?☞ ");
		String inputdaily = sc.nextLine();
		String daily = formatDate(inputdaily);
	   
		String searchnum = inputdaily.replaceAll("[^0-9]", ""); //검색어에서 숫자만 검색하도록 숫자만 가져오기
		
		List<Integer> searchedIndices = new ArrayList<>(); //삭제할 인덱스 리스트 케이스
		List<AccountBookdto> list = getSingletonList();  //가져올 인덱스 리스트
		
		for (int i = 0; i < list.size(); i++) {
			String targetnum = list.get(i).getDaily().replaceAll("[^0-9]", "");  //숫자만 가져오기
			if (targetnum.contains(searchnum)) {
		        searchedIndices.add(i);   //날짜가 있을 경우삭제 후보리스트에 추가
		    }
	    } 
		
		
		//케이스 1. 해당날짜의 가계부가 없는 경우
		if (searchedIndices.isEmpty()) {
	        System.out.println(daily+" 가계부 정보가 없습니다.");
	        return;
	    }
		
		 int targetIndex = -1; // 최종적으로 삭제할 리스트의 실제 인덱스 번호
		
		 //케이스2. 해당날짜의 가계부가 1번인 경우
		 if (searchedIndices.size() == 1) {
		        targetIndex = searchedIndices.get(0); // 바구니의 0번째에 들어있는 인덱스가 바로 타겟
		    }
		// 케이스3. 해당 날짜의 가계부 목록이 2개 이상 발견된 경우
		    else {
		        System.out.println(searchedIndices.size() + "개의 목록이 있습니다 ");
		        System.out.println("지우시려는 대상을 정확히 선택해주세요.");
		        System.out.println("----------------------------------");
		        
		        for (int i = 0; i < searchedIndices.size(); i++) {
		            int realIndex = searchedIndices.get(i); // 실제 원본 리스트에서의 인덱스 가져오기
		            System.out.println("[" + (i + 1) + "번] " + list.get(realIndex).toString());
		        }
		        System.out.println("----------------------------------");
		        
		        System.out.print("삭제할 대상의 번호를 입력하세요 >> ");
		        selectnum = sc.nextInt();
		        sc.nextLine(); // 버퍼 비우기
		        
		        // 입력값 검증 (예: 2개 나왔는데 3번을 누르는 경우 방어)
		        if (selectnum > 0 && selectnum <= searchedIndices.size()) {
		            // 사용자가 고른 번호(selectnum - 1)로  실제 인덱스 가져오기
		            targetIndex = searchedIndices.get(selectnum - 1);
		        } else {
		            System.out.println("잘못된 번호를 선택하셨습니다. 삭제를 취소합니다.");
		            return; // 메소드 종료
		        }
		    }
		 
		 	String selectText=(searchedIndices.size()>1)?selectnum+"번째 " : "";
		    
		    if (targetIndex != -1) {
		        AccountBookdto deletedDto = list.remove(targetIndex);
		        System.out.println(selectText+ deletedDto.getDaily()+ "의 가계부 정보를 삭제했습니다.");
		        
		        save_file();
		    }
	
	}
	
	//TODO selctTitle()
	public void selectTitle() {
		System.out.print("조회하고 싶은 가계부의 분류명 입력하세요");
		String title=sc.nextLine();
		
		
		boolean isselect=false;
		
		List<AccountBookdto> list = getSingletonList();
		for(int i=0;i<list.size();i++) {
			AccountBookdto dto=list.get(i);
			if(dto.getTitle().contains(title)) {
				System.out.println(dto.toString());
				isselect=true;
			}	
		}
		if (!isselect) {
	        System.out.println("가계부 정보가 없습니다");
	    }
	}
	
	//TODO selectContent()
	public void selectContent() {
		System.out.print("조회하고 싶은 가계부의 소비내역 입력하세요");
		String content=sc.nextLine();
		
		
		boolean isselect=false;
		
		List<AccountBookdto> list = getSingletonList();
		for(int i=0;i<list.size();i++) {
			AccountBookdto dto=list.get(i);
			if(dto.getContent().contains(content)) {
				System.out.println(dto.toString());
				isselect=true;
			}	
		}
		if (!isselect) {
	        System.out.println("가계부 정보가 없습니다");
	    }
		
	}
	
	//TODO update()
	public void update() {
		int selectnum=1;
		System.out.print("가계부 정보를 수정하고 싶은 날짜을 입력하세요");
		String inputdaily=sc.nextLine();
		String searchnum = inputdaily.replaceAll("[^0-9]", "");  //날짜에서 숫자만 가져오기
		
		List<Integer> searchedIndices = new ArrayList<>(); //삭제할 인덱스 리스트 케이스
		List<AccountBookdto> list = getSingletonList();  //가져올 인덱스 리스트
		
		for (int i = 0; i < list.size(); i++) {
			
			String targetnum = list.get(i).getDaily().replaceAll("[^0-9]", ""); //날짜에서 숫자만 가져오기
			
			if (targetnum.contains(searchnum)) {
		        searchedIndices.add(i);   //날짜가 있을경우 삭제후보 리스트에 추가
		    }
	    } 
		
		//1단계 수정할 데이터 후보군 검색
		//케이스 1. 해당날짜의 가계부가 없는 경우
		if (searchedIndices.isEmpty()) {
	        System.out.println(inputdaily+" 가계부 정보가 없습니다.");
	        return;
	    }
		
		 int targetIndex = -1; // 최종적으로 수정할 리스트의 실제 인덱스 번호
		
		 //케이스2. 해당날짜의 가계부가 1번인 경우
		 if (searchedIndices.size() == 1) {
		        targetIndex = searchedIndices.get(0); // 바구니의 0번째에 들어있는 인덱스가 바로 타겟
		    }
		// 케이스3. 해당 날짜의 가계부 목록이 2개 이상 발견된 경우
		    else {
		        System.out.println(searchedIndices.size() + "개의 목록이 있습니다 ");
		        System.out.println("수정하고싶은 목록을 정확히 선택해주세요.");
		        System.out.println("----------------------------------");
		        
		        for (int i = 0; i < searchedIndices.size(); i++) {
		            int realIndex = searchedIndices.get(i); // 실제 원본 리스트에서의 인덱스
		            System.out.println("[" + (i + 1) + "번] " + list.get(realIndex).toString());
		        }
		        System.out.println("----------------------------------");
		        
		        System.out.print("수정할 대상의 번호를 입력하세요 >> ");
		        selectnum = sc.nextInt();
		        sc.nextLine(); // 버퍼 비우기
		        
		        // 입력값 검증 (예: 2개 나왔는데 3번을 누르는 경우 방어)
		        if (selectnum > 0 && selectnum <= searchedIndices.size()) {
		            // 사용자가 고른 번호(selectNum - 1)로 바구니에서 실제 인덱스 가져오기
		            targetIndex = searchedIndices.get(selectnum - 1);
		        } else {
		            System.out.println("잘못된 번호를 선택하셨습니다. 삭제를 취소합니다.");
		            return; // 메소드 종료
		        }
		    }
		 
		 //2단계 실제로 수정
		 if (targetIndex != -1) {
		        AccountBookdto dto = list.get(targetIndex); // 수정을 진행할 단 하나의 타겟 객체
		        
		        System.out.println("\n [" + dto.getDaily() + "]의 정보를 수정합니다.");
		        System.out.print("수정할 정보를 선택하세요 (1:수입/지출 분류내역, 2:수익 내역, 3:지출 내역, 4:분류내역, 5:상세 내역, 6: 날짜) >> ");
		        int upcase = sc.nextInt();
		        sc.nextLine(); // 엔터 버퍼 비우기
		        
		        String selectText=(searchedIndices.size()>1)?selectnum+"번째 " : "";
		        
		        switch(upcase) {
		            case 1:{
		                System.out.print("수정할 수입/지출 분류 = ");
		                String reioKind = sc.next();
		                sc.nextLine();  	          
		                String oldkind=dto.getIoKind(); 
		                dto.setIoKind(reioKind);
		                
		                System.out.println(selectText+dto.getDaily()+"의 수입/지출 분류를"+oldkind + "에서 " + reioKind + "으로 수정했습니다.");
		                break;
		            }
		            case 2:{
		                System.out.print("수정할 수익 = ");
		                int income = sc.nextInt();
		                sc.nextLine();
		                int oldincome=dto.getIncome();
		                dto.setIncome(income);
		                System.out.println(selectText+ dto.getDaily() + "의 수익을"+ oldincome+"에서 "+income+"으로 수정했습니다.");
		                break;
		            }
		            case 3:{
		                System.out.print("수정할 지출금액 = ");
		                int expenses = sc.nextInt();
		                sc.nextLine(); //버퍼 비우기
		                int oldexpenses=dto.getExpenses();
		                dto.setExpenses(expenses); 
		                System.out.println(selectText+ dto.getDaily() + "의 지출금액을 "+ oldexpenses+" 에서 "+expenses+"로 수정했습니다.");
		                break;
		            }
		            case 4:{
		                System.out.print("수정할 분류내역 = ");
		                String title = sc.nextLine();
		                String oldtitle=dto.getTitle();
		                dto.setTitle(title);
		                System.out.println(selectText+ dto.getDaily() + "의  가계부 분류내역를 "+ oldtitle+"에서 "+title+"로 수정했습니다.");
		                break;
		            }
		            case 5:{
		                System.out.print("수정할 상세 내역 = ");
		                String content = sc.nextLine();
		                String oldcontent=dto.getContent();
		                dto.setContent(content);                
		                System.out.println(selectText+ dto.getDaily() + "의 가계부 상세 내역를 "+ oldcontent+"에서 "+content+"로 수정했습니다.");
		                break;
		            }
		            case 6:{
		                System.out.print("수정할 날짜 내역 = ");
		                String inputdaily1 = sc.nextLine();
		                String redaily = formatDate(inputdaily1);
		                String olddaily=dto.getDaily();
		                dto.setDaily(redaily);                
		                System.out.println(selectText+ olddaily + "의 가계부 날짜를 "+ olddaily+"에서 "+redaily+"로 수정했습니다.");
		                break;
		            }
		        }
		        save_file();
		    }
		
		
	}
	
	//TODO all print()
	public void allprint() {
		List<AccountBookdto> list = getSingletonList();
		if(list.isEmpty()) {
			System.out.println("가계부 정보가 없습니다");
			return;
		}
		
		for (AccountBookdto dto : list) {
			System.out.println(dto.toString());	
		}
	}
	
	
	
	
	//TODO report
	public void report() {
	    List<AccountBookdto> list = getSingletonList();
	    
	    if (list.isEmpty()) { 
	        System.out.println("가계부 데이터가 없어 결산을 진행할 수 없습니다.");
	        return;
	    }
	    
	    java.util.Map<String, monthstat> monthlyMap = new java.util.TreeMap<>(); //맵 구조로 (연도+월),지출 구조로 저장
	    
	    System.out.print("조회하고 싶은 연도를 입력하세요 (예: 2026) ☞ ");
	    String searchYear = sc.nextLine();
	    
	    // 리스트를 순회하며 수입과 지출을 각각 누적
	    for (AccountBookdto dto : list) {
	    	String date = dto.getDaily();
	    	
	    	if (date == null || date.length() < 10) { //데이터가 없거나 날짜가 이상한 데이터의 경우
	            continue;
	        }
	    	
	    	if (!date.startsWith(searchYear)) {  //해당 연도데이터 아니라면 스킵
	            continue;
	        }
	    	
	    	//연도랑 월 만 가져오는 문자열(xxxx-xx 나 xxxx,xx 등등 표현 여부 상관없이 0~10번까지만)
	    	String yearMonth = date.substring(0, 10);
	        
	    	//연월이 바뀔때마다 새로 만들기
	    	if (!monthlyMap.containsKey(yearMonth)) { 
	            monthlyMap.put(yearMonth, new monthstat());
	        }
	    	
	    	monthstat stat = monthlyMap.get(yearMonth);  //연도 가져오기
	        stat.income += dto.getIncome();
	        stat.expenses += dto.getExpenses();
	    }
	    
	    //해당 년도 데이터가 없을때
	    if (monthlyMap.isEmpty()) {
	        System.out.println(searchYear + "년도의 가계부 데이터가 존재하지 않습니다.");
	        return;
	    }
    
	    // 결과 출력 
	    System.out.println("\n==================================================");
	    System.out.println("             월별 가계부 통계 보고서             ");
	    System.out.println("==================================================");
	    System.out.println("  연월   |    총 수입     |    총 지출     |   최종 손익");
	    System.out.println("--------------------------------------------------");
	    
	    int fintotalincome=0;
	    int fintotalexpen=0;
	    
	    
	    for (String ym : monthlyMap.keySet()) {
	        monthstat stat = monthlyMap.get(ym);
	        int profit = stat.income - stat.expenses;  //월별손익 계산
	        
	        fintotalincome += stat.income; //총 수입
	        fintotalexpen += stat.expenses;//총 지출
	        
	        String profitSign = (profit > 0) ? "+" : "";
	        
	        System.out.println(" " + ym + " |  +" + stat.income + "원 |  -" + stat.expenses + "원 |  " + profitSign + profit + "원");
	    }
	    int finprofit = fintotalincome - fintotalexpen;
	    String finprofitsign = (finprofit > 0) ? "+" : "";
	    System.out.println("==================================================");
	    System.out.println(" 총 결산 내역 ");
	    System.out.println("==================================================");
	    System.out.println("누적 총 수입액 :  +" + fintotalincome + "원");
	    System.out.println("누적 총 지출액 :  -" + fintotalexpen + "원");
	    System.out.println("--------------------------------------------------");
	    System.out.println(" 최종 누적 손익 :  " + finprofitsign + finprofit + "원");
	    System.out.println("==================================================\n");
	}
	
	//TODO formatDate ->연/월 표시 강제 변환
	private String formatDate(String input) {
	    if (input == null) return "";

	    // 1. 숫자가 아닌 모든 문자(년, 월, 일, 공백, 대시, 슬래시 등)를 제거하고 숫자만 남기기
	    String numbers = input.replaceAll("[^0-9]", "");

	    // 2. 만약 숫자가 8자리가 아니라면 변환 안되므로 원본을 그대로 반환
	    if (numbers.length() != 8) {
	        return input; 
	    }

	    // 3. 숫자 8자리를 "YYYY(연)", "MM(월)", "DD(일)"로 쪼개기.
	    String year  = numbers.substring(0, 4); // 0~3번 연도 (ex.2026)
	    String month = numbers.substring(4, 6); // 4~5번 월(ex.05)
	    String day   = numbers.substring(6, 8); // 6~7번 일(ex.05)

	    // 4. 재조합
	    return year + "년 " + month + "월 " + day + "일";
	}
	
	
	//TODO save_file()
		public void save_file() {
			List<AccountBookdto> list = getSingletonList();
			try (FileWriter fw=new FileWriter(file_path)) {
					for (AccountBookdto dto : list) {
						String line=dto.getIoKind()+"|"+dto.getIncome()+"|"+dto.getExpenses()+"|"+dto.getTitle()+"|"+dto.getContent()+"|"+dto.getDaily()+"\n";
						fw.write(line);
					}				
				}
			 catch (IOException e) {
				 System.out.println("파일 저장중 문제가 발생했습니다");
				
			}
		}
		
		//TODO :월별 결산을 위한 클래스(지출 내역)
		public class monthstat{
			int income=0;
			int expenses=0;
		}	
}
