package dto;

public class AccountBookdto {
	
	
	//column
	private String ioKind;  // 수입 or 지출 선택
	private int income;     // 수입
	private int expenses;   // 지출
	private String title;    // 수입 or 지출 내역 분류
	private String content;  // 상세 내역
	private String daily;    // 날짜
	
	
	public AccountBookdto() {
	}

	
	public AccountBookdto(String ioKind, int income, int expenses, String title, String content, String daily) {
		this.ioKind = ioKind;
		this.income = income;
		this.expenses = expenses;
		this.title = title;
		this.content = content;
		this.daily = daily;
	}

	
	public String getIoKind() {
		return ioKind;
	}

	public void setIoKind(String ioKind) {
		this.ioKind = ioKind;
	}

	public int getIncome() { 
		return income;
	}

	public void setIncome(int income) {
		this.income = income;
	}

	public int getExpenses() { 
		return expenses;
	}

	public void setExpenses(int expenses) {
		this.expenses = expenses;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDaily() {
		return daily;
	}

	public void setDaily(String daily) {
		this.daily = daily;
	}

	@Override
	public String toString() {
		if ("수입".equals(this.ioKind)) {
			return "[수입] 날짜: " + daily + " | 분류: " + title + " | 상세: " + content + " | + " + income + "원"; //수입일 경우 수입만
		} else {
			return "[지출] 날짜: " + daily + " | 분류: " + title + " | 상세: " + content + " | - " + expenses + "원"; //지출일 경우 지출만
		}
	}
}