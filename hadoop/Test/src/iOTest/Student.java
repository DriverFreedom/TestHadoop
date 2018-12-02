package iOTest;

public class Student {

	private String name;
	private int chinese;
	private int math;
	private int english;
	private int sum;
	
	
	public Student(String name,int chinese,int math,int english) {
		this.chinese = chinese;
		this.math = math;
		this.english = english;
		this.name = name;
		this.sum = chinese + math + english;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getChinese() {
		return chinese;
	}
	public void setChinese(int chinese) {
		this.chinese = chinese;
	}
	public int getMath() {
		return math;
	}
	public void setMath(int math) {
		this.math = math;
	}
	public int getEnglish() {
		return english;
	}
	public void setEnglish(int english) {
		this.english = english;
	}
	public int getSum() {
		return sum;
	}
	public void setSum(int sum) {
		this.sum = sum;
	}
	@Override
	public String toString() {
		return  name + "	" + chinese + "	" + math + "	" + english + "	"
				+ sum ;
	}
	
}
