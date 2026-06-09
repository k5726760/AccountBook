package main;

import java.util.Scanner;

import dao.AccountBookdao;

public class Main {

	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		AccountBookdao dao=AccountBookdao.getInstance();
		
		//menu
		while(true) {
			System.out.println("============☞가계부☜==========");
			System.out.println("1. 가계부 목록에 추가");
			System.out.println("2. 가계부에서 특정항목 삭제");
			System.out.println("3. 가계부에서 특정 항목 조회(분류)");
			System.out.println("4. 가계부에서 특정 항목 조회(상세내역)");
			System.out.println("5. 가계부 정보 수정");  
			System.out.println("6. 가계부 모두 출력");   //확인용
			System.out.println("7. 월별 통계 및 총 결산 내역"); 
			System.out.println("8. 프로그램 종료"); 
			
			System.out.print("작업 >> ");
			int work=sc.nextInt();
			sc.nextLine();
			
			switch (work) {
				case 1:
					dao.insert(); //추가
					break;
				case 2:
					dao.delete(); //삭제
					break;
				case 3:
					dao.selectTitle(); //대분류로 항목 검색
					break;
				case 4:
					dao.selectContent(); //상세내역으로 항목 검색
					break;
				case 5:
					dao.update(); //수정
					break;
				case 6:
					dao.allprint(); //전체 조회
					break; 
				case 7:
					dao.report(); // 결산
					break;
				case 8:
					System.out.println("가계부 프로그램을 종료합니다");
					System.exit(0);
					break;
				default:
					System.out.println("잘못된 번호 입니다. 1번부터 8번 사이의 번호를 입력해 주세요");
					break;
					
			}
			
		}
	
	}

}
